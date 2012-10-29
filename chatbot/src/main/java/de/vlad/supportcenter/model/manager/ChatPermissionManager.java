package de.vlad.supportcenter.model.manager;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.roo.addon.serializable.RooSerializable;
import org.springframework.transaction.annotation.Transactional;

import de.vlad.supportcenter.model.ChatSession;
import de.vlad.supportcenter.model.ChatUserPermission;
import de.vlad.supportcenter.model.SimpleUser;
import de.vlad.supportcenter.model.SupportAgent;
import de.vlad.supportcenter.model.enums.SupportRole;
import de.vlad.supportcenter.web.common.AppRegistry;


/**
 * The Class ChatPermissionManager.
 */
@Configurable
@RooSerializable
public class ChatPermissionManager {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 4845907661023087707L;

	/** The user. */
	private SimpleUser user = AppRegistry.getChatUser() == null ? AppRegistry.getLoggedInUser() : AppRegistry.getChatUser();
	
	/** The session. */
	private ChatSession session;
	
	/** The entity manager. */
	@PersistenceContext
    private transient EntityManager entityManager;
	
	/**
	 * Instantiates a new chat permission manager.
	 *
	 * @param session the session
	 */
	public ChatPermissionManager(ChatSession session) {
		this.session = session;
	}
	
	/**
	 * Checks for write permission.
	 *
	 * @return true, if successful
	 */
	public boolean hasWritePermission() {
		
		ChatUserPermission permission = getChatUserPermission();
		
		if(permission == null) {
			return false;
		} else {
			return true;
		}
		
	}
	
	/**
	 * Checks for read permission.
	 *
	 * @return true, if successful
	 */
	public boolean hasReadPermission() {
		
		if(hasWritePermission()) {
			return true;
		} else if(user instanceof SupportAgent) {
			SupportAgent employee = (SupportAgent)user;
			if(employee.getAdminCategories().contains(session.getSupportCategory())) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
		
	}
	
	/**
	 * Gets the ticket role.
	 *
	 * @return the ticket role
	 */
	public SupportRole getSupportRole() {
		ChatUserPermission permission = getChatUserPermission();
		
		if(permission == null) {
			return SupportRole.NONE;
		} else {
			return permission.getChatRole();
		}
	}
	
	/**
	 * Gets the chat user permission.
	 *
	 * @return the chat user permission
	 */
	@Transactional
	public ChatUserPermission getChatUserPermission() {
		Query query = entityManager.createQuery("select p from ChatUserPermission p WHERE p.session = ?1 AND p.active = true AND p.chatUser = ?2 ORDER BY p.timeAssigned DESC");
		query.setParameter(1, session);
		query.setParameter(2, user);
		
		query.setMaxResults(1);
		
		@SuppressWarnings("unchecked")
		List<ChatUserPermission> permissions = query.getResultList();
		
		if(permissions.isEmpty()) {
			return null;
		} else {
			return permissions.get(0);
		}
	}
	
	/**
	 * Sets the entity manager.
	 *
	 * @param entityManager the new entity manager
	 */
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
	
	/**
	 * Gets the entity manager.
	 *
	 * @return the entity manager
	 */
	public EntityManager getEntityManager() {
		return entityManager;
	}

	/**
	 * Gets the support agent.
	 *
	 * @return the support agent
	 */
	@Transactional
	public SupportAgent getSupportAgent() {
		ChatUserPermission permission = getSupportAgentPermission();
		
		if(permission == null) {
			return null;
		} else {
			return (SupportAgent)permission.getChatUser();
		}
	}
	
	@Transactional
	public SupportAgent getFirstSupportCoAgent() {
		ChatUserPermission permission = getFirstSupportCoAgentPermission();
		
		if(permission == null) {
			return null;
		} else {
			return (SupportAgent)permission.getChatUser();
		}
	}
	
	@Transactional
	public boolean isCoAgent(SupportAgent agent) {
		Query query = entityManager.createQuery("select p from ChatUserPermission p WHERE p.session = ?1 AND p.active = true AND p.chatUser = ?2 ORDER BY p.timeAssigned DESC");
		query.setParameter(1, session);
		query.setParameter(2, agent);
		
		query.setMaxResults(1);
		
		@SuppressWarnings("unchecked")
		List<ChatUserPermission> permissions = query.getResultList();
		
		if(permissions.isEmpty()) {
			return false;
		} else {
			return true;
		}
	}
	
	/**
	 * Gets the support agent permission.
	 *
	 * @return the support agent permission
	 */
	@Transactional
	public ChatUserPermission getSupportAgentPermission() {
		Query query = entityManager.createQuery("select p from ChatUserPermission p WHERE p.session = ?1 AND p.active = true AND p.chatRole = ?2 ORDER BY p.timeAssigned DESC");
		query.setParameter(1, session);
		query.setParameter(2, SupportRole.SUPPORT_AGENT);
		
		query.setMaxResults(1);
		
		@SuppressWarnings("unchecked")
		List<ChatUserPermission> permissions = query.getResultList();
		
		if(permissions.isEmpty()) {
			return null;
		} else {
			return permissions.get(0);
		}
	}
	
	@Transactional
	public ChatUserPermission getFirstSupportCoAgentPermission() {
		Query query = entityManager.createQuery("select p from ChatUserPermission p WHERE p.session = ?1 AND p.active = true AND p.chatRole = ?2 ORDER BY p.timeAssigned ASC");
		query.setParameter(1, session);
		query.setParameter(2, SupportRole.SUPPORT_CO_AGENT);
		
		query.setMaxResults(1);
		
		@SuppressWarnings("unchecked")
		List<ChatUserPermission> permissions = query.getResultList();
		
		if(permissions.isEmpty()) {
			return null;
		} else {
			return permissions.get(0);
		}
	}
	
	/**
	 * Gets the enquirer.
	 *
	 * @return the enquirer
	 */
	@Transactional
	public SimpleUser getEnquirer() {
		ChatUserPermission permission = getEnquirerPermission();
		
		if(permission == null) {
			return null;
		} else {
			return permission.getChatUser();
		}
	}
	
	/**
	 * Gets the enquirer permission.
	 *
	 * @return the enquirer permission
	 */
	@Transactional
	public ChatUserPermission getEnquirerPermission() {
		Query query = entityManager.createQuery("select p from ChatUserPermission p WHERE p.session = ?1 AND p.active = true AND p.chatRole = ?2 ORDER BY p.timeAssigned DESC");
		query.setParameter(1, session);
		query.setParameter(2, SupportRole.ENQUIRER);
		
		query.setMaxResults(1);
		
		@SuppressWarnings("unchecked")
		List<ChatUserPermission> permissions = query.getResultList();
		
		if(permissions.isEmpty()) {
			return null;
		} else {
			return permissions.get(0);
		}
	}

}
