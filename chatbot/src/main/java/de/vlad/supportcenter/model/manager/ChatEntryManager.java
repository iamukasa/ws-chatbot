package de.vlad.supportcenter.model.manager;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.roo.addon.serializable.RooSerializable;
import org.springframework.transaction.annotation.Transactional;

import de.vlad.supportcenter.chatbot.ChatBotEntryPoint;
import de.vlad.supportcenter.model.ChatEntry;
import de.vlad.supportcenter.model.ChatSession;
import de.vlad.supportcenter.model.ChatUserPermission;
import de.vlad.supportcenter.model.SimpleUser;
import de.vlad.supportcenter.model.SupportAgent;
import de.vlad.supportcenter.model.enums.EntryType;
import de.vlad.supportcenter.model.enums.SupportRole;


/**
 * The Class ChatEntryManager.
 */
@Configurable
@RooSerializable
public class ChatEntryManager {
	
	/** The Constant LOG. */
	protected static final Logger LOG = Logger.getLogger(ChatEntryManager.class);

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 8219814696937547974L;

	/** The Constant MAX_ENTRY_RESULT. */
	public static final int MAX_ENTRY_RESULT = 50;

	/** The session. */
	private ChatSession session;

	/** The permission manager. */
	private ChatPermissionManager permissionManager;
	
	/** The chat manager. */
	@Autowired
	private transient ChatManager chatManager;
	
	private ChatBotEntryPoint chatBot;

	/** The entity manager. */
	@PersistenceContext
	private transient EntityManager entityManager;

	/**
	 * Instantiates a new chat entry manager.
	 *
	 * @param session the session
	 */
	public ChatEntryManager(ChatSession session) {
		this.session = session;
		this.permissionManager = new ChatPermissionManager(session);
		this.chatBot = new ChatBotEntryPoint(this);
	}

	/**
	 * Adds the chat entry.
	 *
	 * @param message the message
	 */
	@Transactional
	public void addChatEntry(String message) {

		if (permissionManager.hasWritePermission()) {
			ChatEntry entry = new ChatEntry();
			Date curTime = new Date();
			entry.setComment(message);
			entry.setUserPermission(permissionManager.getChatUserPermission());
			chatManager.updateSessionTime(session.getId());
			entry.setTimeCreated(curTime);
			entry.setType(EntryType.COMMENT);
			entry.persist();
			
			chatBot.userPostedMessageEventListener(message);
		}
	}
	
	/**
	 * Adds the file entry.
	 *
	 * @param path the path
	 */
	@Transactional
	public void addFileEntry(String path) {

		if (permissionManager.hasWritePermission()) {
			ChatEntry entry = new ChatEntry();
			Date curTime = new Date();
			entry.setComment(path);
			entry.setUserPermission(permissionManager.getChatUserPermission());
			chatManager.updateSessionTime(session.getId());
			entry.setTimeCreated(curTime);
			entry.setType(EntryType.FILE);
			entry.persist();
		}
	}

	/**
	 * Gets the chat entries.
	 *
	 * @return the chat entries
	 */
	@SuppressWarnings("unchecked")
	@Transactional
	public List<ChatEntry> getChatEntries() {

		if (permissionManager.hasReadPermission()) {

			Query query = entityManager
					.createQuery("select c from ChatEntry c WHERE c.userPermission.session = ?1 ORDER BY c.timeCreated DESC, c.id DESC");
			query.setParameter(1, session);
			query.setMaxResults(MAX_ENTRY_RESULT);
			return query.getResultList();
		} else {
			return new ArrayList<ChatEntry>();
		}

	}

	/**
	 * Gets the latest chat entries.
	 *
	 * @param latestId the latest id
	 * @return the latest chat entries
	 */
	@SuppressWarnings("unchecked")
	@Transactional
	public List<ChatEntry> getLatestChatEntries(long latestId) {

		if (permissionManager.hasReadPermission()) {

			Query query = entityManager
					.createQuery("select c from ChatEntry c WHERE c.id > ?1 AND c.userPermission.session = ?2 ORDER BY c.timeCreated DESC, c.id DESC");
			query.setParameter(1, latestId);
			query.setParameter(2, session);
			query.setMaxResults(MAX_ENTRY_RESULT);
			
			return query.getResultList();

		} else {
			LOG.error("no read permission");
			return new ArrayList<ChatEntry>();
		}

	}
	
	/**
	 * Gets the latest chat entry from enquirer.
	 *
	 * @return the latest chat entry from enquirer
	 */
	@Transactional
	public ChatEntry getLatestChatEntryFromEnquirer() {

		if (permissionManager.hasReadPermission()) {

			Query query = entityManager
					.createQuery("select c from ChatEntry c WHERE c.userPermission.session = ?1 AND c.type = ?2 AND c.userPermission.chatRole = ?3 ORDER BY c.timeCreated DESC, c.id DESC");
			query.setParameter(1, session);
			query.setParameter(2, EntryType.COMMENT);
			query.setParameter(3, SupportRole.ENQUIRER);
			query.setMaxResults(1);
			
			@SuppressWarnings("unchecked")
			List<ChatEntry> entries = query.getResultList();

			if (entries.isEmpty()) {
				return null;
			} else {
				return entries.get(0);
			}

		} else {
			LOG.error("no read permission");
			return null;
		}

	}
	
	/**
	 * Gets the contact reason.
	 *
	 * @return the contact reason
	 */
	@Transactional
	public ChatEntry getContactReason() {

		if (permissionManager.hasReadPermission()) {

			Query query = entityManager
					.createQuery("select c from ChatEntry c WHERE c.userPermission.session = ?1 ORDER BY c.timeCreated ASC, c.id ASC");
			query.setParameter(1, session);
			query.setMaxResults(2);
			@SuppressWarnings("unchecked")
			List<ChatEntry> resultList = query.getResultList();
			if(resultList.size() == 2) {
				
				ChatEntry firstEntry = resultList.get(1);
				if(firstEntry.getType() != EntryType.COMMENT) {
					return null;
				} else {
					return firstEntry;
				}
			} else {
				return null;
			}

		} else {
			LOG.error("no read permission");
			return null;
		}

	}
	
	/**
	 * Sets the chat typing.
	 *
	 * @param typing the new chat typing
	 */
	@Transactional
	public void setChatTyping(boolean typing) {
		if (permissionManager.hasWritePermission()) {
			ChatUserPermission permission = permissionManager.getChatUserPermission();
			if(permission.isTyping() != typing) {
				permission.setTyping(typing);
				permission.merge();
			}
		}
	}
	
	/**
	 * Checks if is chat partner typing.
	 *
	 * @return true, if is chat partner typing
	 */
	public boolean isChatPartnerTyping() {
		ChatUserPermission permission = permissionManager.getChatUserPermission();
		ChatUserPermission partnerPermission = null;
		if(permission.getChatRole() == SupportRole.ENQUIRER) {
			partnerPermission = permissionManager.getSupportAgentPermission();
		} else {
			partnerPermission = permissionManager.getEnquirerPermission();
		}
		if(partnerPermission == null) {
			return false;
		} else {
			return partnerPermission.isTyping();
		}
	}
	
	/**
	 * Gets the partner.
	 *
	 * @return the partner
	 */
	public SimpleUser getPartner() {
		ChatUserPermission permission = permissionManager.getChatUserPermission();
		if(permission.getChatRole() == SupportRole.ENQUIRER) {
			return getSupportAgent();
		} else {
			return getEnquirer();
		}
	}
	
	/**
	 * Gets the support agent.
	 *
	 * @return the support agent
	 */
	@Transactional
	public SupportAgent getSupportAgent() {
		return permissionManager.getSupportAgent();
	}
	
	/**
	 * Gets the enquirer.
	 *
	 * @return the enquirer
	 */
	@Transactional
	public SimpleUser getEnquirer() {
		return permissionManager.getEnquirer();
	}
	
	@Transactional
	public SupportAgent getFirstSupportCoAgent() {
		return permissionManager.getFirstSupportCoAgent();
	}
	
	public boolean isCoAgent(SupportAgent agent) {
		return permissionManager.isCoAgent(agent);
	}
	
	/**
	 * Gets the chat session.
	 *
	 * @return the chat session
	 */
	public ChatSession getChatSession() {
		session = ChatSession.findChatSession(session.getId());
		return session;
	}
	
	/**
	 * Checks if is chat category available.
	 *
	 * @return true, if is chat category available
	 */
	public boolean isChatCategoryAvailable() {
		Query query = entityManager
				.createQuery("select s from SupportAgent s INNER JOIN s.adminCategories a WHERE s.chatEnabled =?1 AND a = ?2");
		query.setParameter(1, true);
		query.setParameter(2, session.getSupportCategory());

		query.setMaxResults(1);

		@SuppressWarnings("unchecked")
		List<ChatSession> session = query.getResultList();

		if (session.isEmpty()) {
			return false;
		} else {
			return true;
		}
	}
	
	public ChatPermissionManager getPermissionManager() {
		return permissionManager;
	}

}
