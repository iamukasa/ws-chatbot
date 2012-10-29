package de.vlad.supportcenter.model.manager;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import de.vlad.supportcenter.dto.NewChatSessionDTO;
import de.vlad.supportcenter.model.ChatEntry;
import de.vlad.supportcenter.model.ChatSession;
import de.vlad.supportcenter.model.ChatUserPermission;
import de.vlad.supportcenter.model.Portal;
import de.vlad.supportcenter.model.SimpleUser;
import de.vlad.supportcenter.model.SupportAgent;
import de.vlad.supportcenter.model.SupportCategory;
import de.vlad.supportcenter.model.enums.ChatStatus;
import de.vlad.supportcenter.model.enums.EntryType;
import de.vlad.supportcenter.model.enums.SupportRole;
import de.vlad.supportcenter.model.manager.enums.SystemNotification;
import de.vlad.supportcenter.web.common.AppRegistry;
import de.vlad.supportcenter.web.common.Utils;


/**
 * The Class ChatManager.
 */
@Component
public class ChatManager {

	/** The entity manager. */
	@PersistenceContext
	private transient EntityManager entityManager;
	
	/** The support user manager. */
	@Autowired
	private transient SupportUserManager supportUserManager;
	
	/**
	 * Update session time.
	 *
	 * @param sessionId the session id
	 */
	@Transactional
	public void updateSessionTime(Long sessionId) {
		ChatSession session = ChatSession.findChatSession(sessionId);
		session.setTimeUpdated(new Date());
		session.merge();
	}
	
	/**
	 * Update session status.
	 *
	 * @param sessionId the session id
	 * @param status the status
	 */
	@Transactional
	public void updateSessionStatus(Long sessionId, ChatStatus status) {
		ChatSession session = ChatSession.findChatSession(sessionId);
		session.setStatus(status);
		session.setTimeUpdated(new Date());
		session.merge();
	}

	/**
	 * Creates the chat session.
	 *
	 * @param sessionDTO the session dto
	 * @return the chat session
	 */
	@Transactional
	public ChatSession createChatSession(NewChatSessionDTO sessionDTO) {
		if (getSessionByClientIp() != null) {
			return null;
		}
		Date curtime = new Date();
		ChatSession session = new ChatSession();
		SimpleUser user = AppRegistry.getLoggedInUser();
		if(user == null) {
			user = supportUserManager.createNewChatUser(sessionDTO).getUser();
			if(user == null) {
				return null;
			}
		}
		AppRegistry.setChatUser(user);
		session.setSupportCategory(sessionDTO.getSupportCategory());
		
		session.setEnquirerIP(Utils.getClientIp());
		session.setStatus(ChatStatus.WAITING);
		session.setTimeCreated(curtime);
		session.setTimeUpdated(curtime);
		session.persist();
		
		ChatUserPermission userPermission = new ChatUserPermission();
		userPermission.setChatUser(user);
		userPermission.setSession(session);
		userPermission.setChatRole(SupportRole.ENQUIRER);
		userPermission.setTimeAssigned(curtime);
		userPermission.setActive(true);
		userPermission.persist();

		ChatEntry systemEntry = new ChatEntry();
		systemEntry.setUserPermission(userPermission);
		systemEntry.setTimeCreated(curtime);
		systemEntry.setType(EntryType.SYSTEM_NOTIFICATION);
		systemEntry.setComment(SystemNotification.CREATED.toString());
		systemEntry.persist();
		
		if(sessionDTO.getChatComment() != null && !sessionDTO.getChatComment().trim().isEmpty()) {
			ChatEntry chatEntry = new ChatEntry();
			chatEntry.setUserPermission(userPermission);
			chatEntry.setTimeCreated(curtime);
			chatEntry.setType(EntryType.COMMENT);
			chatEntry.setComment(sessionDTO.getChatComment());
			chatEntry.persist();
		}
		
		return session;
	}

	/**
	 * Close session.
	 *
	 * @param session the session
	 * @return true, if successful
	 */
	@Transactional
	public boolean closeSession(ChatSession session) {
		ChatPermissionManager permissionManager = new ChatPermissionManager(session);
		if(permissionManager.hasWritePermission()) {
			Date curTime = new Date();
			ChatEntry systemEntry = new ChatEntry();
			systemEntry.setUserPermission(permissionManager.getChatUserPermission());
			systemEntry.setTimeCreated(curTime);
			systemEntry.setType(EntryType.SYSTEM_NOTIFICATION);
			systemEntry.setComment(SystemNotification.CLOSED.toString());
			systemEntry.persist();
			updateSessionStatus(session.getId(), ChatStatus.CLOSED);
			return true;
		}
		return false;
	}
	
	@Transactional
	public boolean leaveSession(ChatSession session) {
		ChatPermissionManager permissionManager = new ChatPermissionManager(session);
		ChatUserPermission permission = permissionManager.getChatUserPermission();
		if(permission != null && permission.isActive()) {
			return closeSession(session);
		}
		return false;
	}

	/**
	 * Reopen session.
	 *
	 * @param session the session
	 * @return true, if successful
	 */
	@Transactional
	public boolean reopenSession(ChatSession session) {
		ChatPermissionManager permissionManager = new ChatPermissionManager(session);
		if(permissionManager.hasWritePermission()) {
			Date curTime = new Date();
			ChatEntry systemEntry = new ChatEntry();
			systemEntry.setUserPermission(permissionManager.getChatUserPermission());
			systemEntry.setTimeCreated(curTime);
			systemEntry.setType(EntryType.SYSTEM_NOTIFICATION);
			systemEntry.setComment(SystemNotification.REOPENED.toString());
			systemEntry.persist();
			updateSessionStatus(session.getId(), ChatStatus.WAITING);
			return true;
		}
		return false;
	}

	/**
	 * Gets the session by client ip.
	 *
	 * @return the session by client ip
	 */
	@Transactional
	public ChatSession getSessionByClientIp() {
		String ip = Utils.getClientIp();
		Portal portal = AppRegistry.getPortal();
		Query query = entityManager
				.createQuery("select s from ChatSession s WHERE s.enquirerIP = ?1 AND s.supportCategory.portal = ?2 AND s.status <> ?3");
		query.setParameter(1, ip);
		query.setParameter(2, portal);
		query.setParameter(3, ChatStatus.CLOSED);

		query.setMaxResults(1);

		@SuppressWarnings("unchecked")
		List<ChatSession> session = query.getResultList();

		if (session.isEmpty()) {
			return null;
		} else {
			ChatSession sess = session.get(0);
			if(AppRegistry.getChatUser() == null) {
				AppRegistry.setChatUser(new ChatPermissionManager(sess).getEnquirer());
			}
			return session.get(0);
		}

	}
	
	/**
	 * Gets the chat entry manager.
	 *
	 * @param session the session
	 * @return the chat entry manager
	 */
	public ChatEntryManager getChatEntryManager(ChatSession session) {
		return new ChatEntryManager(session);
	}
	
	/**
	 * Checks if is chat available.
	 *
	 * @return true, if is chat available
	 */
	public boolean isChatAvailable() {
		Query query = entityManager
				.createQuery("select s from SupportAgent s  WHERE s.portal = ?1 AND s.chatEnabled = ?2");
		query.setParameter(1, AppRegistry.getPortal());
		query.setParameter(2, true);

		query.setMaxResults(1);

		@SuppressWarnings("unchecked")
		List<ChatSession> session = query.getResultList();

		if (session.isEmpty()) {
			return false;
		} else {
			return true;
		}
	}
	
	/**
	 * Checks if is chat for category available.
	 *
	 * @param category the category
	 * @return true, if is chat for category available
	 */
	public boolean isChatForCategoryAvailable(SupportCategory category) {
		Query query = entityManager
				.createQuery("select s from SupportAgent s INNER JOIN s.adminCategories a WHERE s.chatEnabled =?1 AND a = ?2");
		query.setParameter(1, true);
		query.setParameter(2, category);

		query.setMaxResults(1);

		@SuppressWarnings("unchecked")
		List<ChatSession> session = query.getResultList();

		if (session.isEmpty()) {
			return false;
		} else {
			return true;
		}
	}
	
	/**
	 * Assign session to current agent.
	 *
	 * @param session the session
	 * @return true, if successful
	 */
	@Transactional
	public boolean assignSessionToCurrentAgent(ChatSession session) {
		ChatEntryManager chatEntryManager = getChatEntryManager(session);
		SupportAgent existingAssignee = chatEntryManager.getSupportAgent();
		if(existingAssignee == null) {
			Date curTime = new Date();
			ChatUserPermission permission = new ChatUserPermission();
			permission.setActive(true);
			permission.setChatUser(AppRegistry.getLoggedInUser());
			permission.setSession(session);
			permission.setChatRole(SupportRole.SUPPORT_AGENT);
			permission.setTimeAssigned(curTime);
			permission.persist();
			
			ChatEntry systemEntry = new ChatEntry();
			systemEntry.setComment(SystemNotification.AGENT_ASSIGN.toString());
			systemEntry.setTimeCreated(curTime);
			systemEntry.setType(EntryType.SYSTEM_NOTIFICATION);
			systemEntry.setUserPermission(permission);
			systemEntry.persist();
			
			
			updateSessionStatus(session.getId(), ChatStatus.IN_PROGRESS);
			
			return true;
		} else if(chatEntryManager.isCoAgent((SupportAgent)AppRegistry.getLoggedInUser())) {
			return true;
		} else if(AppRegistry.getPortal().isChatMultipleAgentsAssistence()) {
			Date curTime = new Date();
			ChatUserPermission permission = new ChatUserPermission();
			permission.setActive(true);
			permission.setChatUser(AppRegistry.getLoggedInUser());
			permission.setSession(session);
			permission.setChatRole(SupportRole.SUPPORT_CO_AGENT);
			permission.setTimeAssigned(curTime);
			permission.persist();
			
			ChatEntry systemEntry = new ChatEntry();
			systemEntry.setComment(SystemNotification.CO_AGENT_ASSIGN.toString());
			systemEntry.setTimeCreated(curTime);
			systemEntry.setType(EntryType.SYSTEM_NOTIFICATION);
			systemEntry.setUserPermission(permission);
			systemEntry.persist();
			return true;
		}
		
		return false;
	}
	
	/**
	 * Pass session to other agents.
	 *
	 * @param session the session
	 */
	@Transactional
	public void passSessionToOtherAgents(ChatSession session) {
		ChatPermissionManager permissionManager = new ChatPermissionManager(session);
		ChatUserPermission permission = permissionManager.getChatUserPermission();
		if(permission != null && permission.isActive() && permission.getChatRole() == SupportRole.SUPPORT_AGENT) {
			Date curTime = new Date();
			permission.setActive(false);
			permission.merge();
			
			ChatEntry systemEntry = new ChatEntry();
			systemEntry.setComment(SystemNotification.AGENT_PASS.toString());
			systemEntry.setTimeCreated(curTime);
			systemEntry.setType(EntryType.SYSTEM_NOTIFICATION);
			systemEntry.setUserPermission(permission);
			systemEntry.persist();
			boolean newAgentFound = false;
			if(AppRegistry.getPortal().isChatMultipleAgentsAssistence()) {
				ChatUserPermission coAgentPermission = permissionManager.getFirstSupportCoAgentPermission();
				if(coAgentPermission != null) {
					coAgentPermission.setChatRole(SupportRole.SUPPORT_AGENT);
					coAgentPermission.merge();
					
					ChatEntry coAgentSystemEntry = new ChatEntry();
					coAgentSystemEntry.setComment(SystemNotification.AGENT_ASSIGN.toString());
					coAgentSystemEntry.setTimeCreated(curTime);
					coAgentSystemEntry.setType(EntryType.SYSTEM_NOTIFICATION);
					coAgentSystemEntry.setUserPermission(coAgentPermission);
					coAgentSystemEntry.persist();
					newAgentFound = true;
				}
			}
			if(!newAgentFound) {
				updateSessionStatus(session.getId(), ChatStatus.WAITING);
			}
			
		}
	}
	
	/**
	 * Gets the next chat request from query.
	 *
	 * @return the next chat request from query
	 */
	@Transactional
	public ChatSession getNextChatRequestFromQuery() {
		Query query = entityManager.createQuery("select t from ChatSession t WHERE EXISTS (select s from SupportAgent s INNER JOIN s.adminCategories a WHERE a = t.supportCategory) AND NOT EXISTS (SELECT p FROM ChatUserPermission p WHERE p.session = t AND ((p.chatRole = ?1 AND p.active = ?5 AND p.chatUser <> ?3) OR (p.chatRole = ?2 AND p.chatUser = ?3))) AND t.status = ?4 ORDER BY t.timeUpdated ASC");
		query.setParameter(1, SupportRole.SUPPORT_AGENT);
		query.setParameter(2, SupportRole.ENQUIRER);
		query.setParameter(3, AppRegistry.getLoggedInUser());
		query.setParameter(4, ChatStatus.WAITING);
		query.setParameter(5, true);
		
		query.setMaxResults(1);
		
		@SuppressWarnings("unchecked")
		List<ChatSession> permissions = query.getResultList();
		
		if(permissions.isEmpty()) {
			return null;
		} else {
			ChatSession session = permissions.get(0);
			assignSessionToCurrentAgent(session);
			return session;
		}
	}
	
	/**
	 * Find chat session for current user.
	 *
	 * @param id the id
	 * @return the chat session
	 */
	@Transactional
	public ChatSession findChatSessionForCurrentUser(Long id) {
		if(id != null) {
			ChatSession session = ChatSession.findChatSession(id);
			ChatPermissionManager permissionManager = new ChatPermissionManager(session);
			if(permissionManager.hasReadPermission()) {
				return session;
			}
		}
		return null;
	}
	
	/**
	 * Count sessions.
	 *
	 * @return the long
	 */
	@Transactional
	public Long countSessions() {
		TypedQuery<Long> query = entityManager.createQuery("SELECT COUNT(o) FROM ChatSession o WHERE o.supportCategory.portal = ?1", Long.class);
		query.setParameter(1, AppRegistry.getPortal());
		return query.getSingleResult();
	}
	
	/**
	 * Count sessions with status.
	 *
	 * @param status the status
	 * @return the long
	 */
	@Transactional
	public Long countSessionsWithStatus(ChatStatus status) {
		TypedQuery<Long> query = entityManager.createQuery("SELECT COUNT(o) FROM ChatSession o WHERE o.status = ?1 AND o.supportCategory.portal = ?2", Long.class);
		query.setParameter(1, status);
		query.setParameter(2, AppRegistry.getPortal());
		return query.getSingleResult();
	}


}
