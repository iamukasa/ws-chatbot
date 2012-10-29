package de.vlad.supportcenter.event;

import com.github.wolfie.blackboard.Event;

import de.vlad.supportcenter.model.enums.SupportRole;
import de.vlad.supportcenter.web.livechat.ChatSessionEventType;


/**
 * The Class ChatStatusChangedEvent.
 */
public class ChatStatusChangedEvent implements Event {
	
	/** The session event type. */
	private ChatSessionEventType sessionEventType;
	
	/** The support role. */
	private SupportRole supportRole;
	
	/**
	 * Instantiates a new chat status changed event.
	 *
	 * @param chatStatus the chat status
	 */
	public ChatStatusChangedEvent(ChatSessionEventType chatStatus) {
		this(chatStatus, SupportRole.ENQUIRER);
	}
	
	/**
	 * Instantiates a new chat status changed event.
	 *
	 * @param sessionEventType the session event type
	 * @param supportRole the support role
	 */
	public ChatStatusChangedEvent(ChatSessionEventType sessionEventType, SupportRole supportRole) {
		this.sessionEventType = sessionEventType;
		this.supportRole = supportRole;
	}
	
	/**
	 * Gets the session event type.
	 *
	 * @return the session event type
	 */
	public ChatSessionEventType getSessionEventType() {
		return sessionEventType;
	}
	
	/**
	 * Gets the support role.
	 *
	 * @return the support role
	 */
	public SupportRole getSupportRole() {
		return supportRole;
	}

}
