package de.vlad.supportcenter.model.manager;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import de.vlad.supportcenter.dto.NewChatSessionDTO;
import de.vlad.supportcenter.dto.NewTicketDTO;
import de.vlad.supportcenter.dto.SupportAgentDTO;
import de.vlad.supportcenter.model.SimpleUser;
import de.vlad.supportcenter.model.SupportAgent;
import de.vlad.supportcenter.model.SupportUser;
import de.vlad.supportcenter.web.common.AppRegistry;


/**
 * The Class SupportUserManager.
 */
@Component
public class SupportUserManager {
	
	/** The Constant LOG. */
	protected static final Logger LOG = Logger
			.getLogger(SupportUserManager.class);

	/**
	 * The Enum OperationResult.
	 */
	public static enum OperationResult {
		
		/** The USE r_ created. */
		USER_CREATED, 
 /** The EMAI l_ alread y_ exists. */
 EMAIL_ALREADY_EXISTS, 
 /** The FAILED. */
 FAILED;
		
		/** The user. */
		private SimpleUser user;

		/**
		 * Gets the user.
		 *
		 * @return the user
		 */
		public SimpleUser getUser() {
			return user;
		}

		/**
		 * Sets the support user.
		 *
		 * @param user the new support user
		 */
		public void setSupportUser(SimpleUser user) {
			this.user = user;
		}

		/**
		 * Inits the.
		 *
		 * @param user the user
		 * @return the operation result
		 */
		public OperationResult init(SimpleUser user) {
			setSupportUser(user);
			return this;
		}

	}

	/** The entity manager. */
	@PersistenceContext
	private transient EntityManager entityManager;
	
	/**
	 * Creates the new support user.
	 *
	 * @param dto the dto
	 * @return the operation result
	 */
	@Transactional
	public OperationResult createNewSupportUser(NewTicketDTO dto) {
		try {
			SupportUser user = findEnquirerByEmail(dto.getEmail());
			if (user != null) {
				return OperationResult.EMAIL_ALREADY_EXISTS.init(user);
			}
			
			user = new SupportUser();
			user.setDisplayName(dto.getDisplayName());
			user.setEmail(dto.getEmail());
			user.setActive(true);
			user.setPassword(dto.getPassword());
			user.setPortal(AppRegistry.getPortal());
			user.persist();
			LOG.debug("User created: " + user.toString());

			return OperationResult.USER_CREATED.init(user);
		} catch (Exception e) {
			LOG.error(e.toString());
			return OperationResult.FAILED;
		}

	}

	/**
	 * Creates the new chat user.
	 *
	 * @param dto the dto
	 * @return the operation result
	 */
	@Transactional
	public OperationResult createNewChatUser(NewChatSessionDTO dto) {
		try {
			SimpleUser user = new SimpleUser();
			user.setDisplayName(dto.getDisplayName());
			user.setEmail(dto.getEmail());
			user.setPortal(AppRegistry.getPortal());
			user.persist();
			LOG.debug("Chat User created: " + user.toString());

			return OperationResult.USER_CREATED.init(user);
		} catch (Exception e) {
			LOG.error(e.toString());
			return OperationResult.FAILED;
		}

	}

	/**
	 * Gets the all employees.
	 *
	 * @return the all employees
	 */
	@Transactional
	public Set<SupportAgent> getAllEmployees() {
		TypedQuery<SupportAgent> query = entityManager.createQuery(
				"select s from SupportAgent s WHERE s.portal = ?1",
				SupportAgent.class);
		query.setParameter(1, AppRegistry.getPortal());
		return new HashSet<SupportAgent>(query.getResultList());

	}
	
	/**
	 * Gets the support agent for edit.
	 *
	 * @param agentId the agent id
	 * @return the support agent for edit
	 */
	@Transactional
	public SupportAgentDTO getSupportAgentForEdit(Long agentId) {
		SupportAgent agent = SupportAgent.findSupportAgent(agentId);
		SupportAgentDTO dto = new SupportAgentDTO();
		dto.setId(agent.getId());
		dto.setActive(agent.isActive());
		dto.setAdminCategories(agent.getAdminCategories());
		dto.setChatPermission(agent.isChatPermission());
		dto.setDisplayName(agent.getDisplayName());
		dto.setEmail(agent.getEmail());
		return dto;
	}
	

	/**
	 * Find enquirer by email and password.
	 *
	 * @param email the email
	 * @param password the password
	 * @return the support user
	 */
	@Transactional
	public SupportUser findEnquirerByEmailAndPassword(String email,
			String password) {
		Query query = entityManager
				.createQuery("select s from SupportUser s where s.email = ?1 AND s.password = ?2 AND s.portal = ?3");
		query.setParameter(1, email);
		query.setParameter(2, password);
		query.setParameter(3, AppRegistry.getPortal());
		query.setMaxResults(1);
		@SuppressWarnings("unchecked")
		List<SupportUser> result = (List<SupportUser>) query.getResultList();
		if (result != null && !result.isEmpty()) {
			return result.get(0);
		} else {
			return null;
		}
	}

	/**
	 * Find support agent by email and password.
	 *
	 * @param email the email
	 * @param password the password
	 * @return the support agent
	 */
	@Transactional
	public SupportAgent findSupportAgentByEmailAndPassword(String email,
			String password) {
		Query query = entityManager
				.createQuery("select s from SupportAgent s where s.email = ?1 AND s.password = ?2 AND s.portal = ?3");
		query.setParameter(1, email);
		query.setParameter(2, password);
		query.setParameter(3, AppRegistry.getPortal());
		query.setMaxResults(1);
		@SuppressWarnings("unchecked")
		List<SupportAgent> result = (List<SupportAgent>) query.getResultList();
		if (result != null && !result.isEmpty()) {
			return result.get(0);
		} else {
			return null;
		}
	}

	/**
	 * Find enquirer by email.
	 *
	 * @param email the email
	 * @return the support user
	 */
	@Transactional
	public SupportUser findEnquirerByEmail(String email) {
		Query query = entityManager
				.createQuery("select s from SupportUser s where s.email = ?1 AND s.portal = ?2");
		query.setParameter(1, email);
		query.setParameter(2, AppRegistry.getPortal());
		query.setMaxResults(1);
		@SuppressWarnings("unchecked")
		List<SupportUser> result = (List<SupportUser>) query.getResultList();
		if (result != null && !result.isEmpty()) {
			return result.get(0);
		} else {
			return null;
		}
	}

	/**
	 * Find support agent by email.
	 *
	 * @param email the email
	 * @return the support agent
	 */
	@Transactional
	public SupportAgent findSupportAgentByEmail(String email) {
		Query query = entityManager
				.createQuery("select s from SupportAgent s where s.email = ?1 AND s.portal = ?2");
		query.setParameter(1, email);
		query.setParameter(2, AppRegistry.getPortal());
		query.setMaxResults(1);
		@SuppressWarnings("unchecked")
		List<SupportAgent> result = (List<SupportAgent>) query.getResultList();
		if (result != null && !result.isEmpty()) {
			return result.get(0);
		} else {
			return null;
		}
	}

	/**
	 * Count support agents online.
	 *
	 * @return the long
	 */
	@Transactional
	public Long countSupportAgentsOnline() {
		TypedQuery<Long> query = entityManager
				.createQuery(
						"SELECT COUNT(o) FROM SupportAgent o WHERE o.chatEnabled = ?1 AND o.portal = ?2",
						Long.class);
		query.setParameter(1, true);
		query.setParameter(2, AppRegistry.getPortal());
		return query.getSingleResult();
	}

	/**
	 * Count support agents.
	 *
	 * @return the long
	 */
	@Transactional
	public Long countSupportAgents() {
		TypedQuery<Long> query = entityManager.createQuery(
				"SELECT COUNT(o) FROM SupportAgent o WHERE o.portal = ?1",
				Long.class);
		query.setParameter(1, AppRegistry.getPortal());
		return query.getSingleResult();
	}

}
