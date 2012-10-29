package de.vlad.supportcenter.event.listener;

import com.github.wolfie.blackboard.Listener;
import com.github.wolfie.blackboard.annotation.ListenerMethod;

import de.vlad.supportcenter.event.ChatStatusChangedEvent;


/**
 * The listener interface for receiving chatStatusChangedEvent events.
 * The class that is interested in processing a chatStatusChangedEvent
 * event implements this interface, and the object created
 * with that class is registered with a component using the
 * component's <code>addChatStatusChangedEventListener<code> method. When
 * the chatStatusChangedEvent event occurs, that object's appropriate
 * method is invoked.
 *
 * @see ChatStatusChangedEventEvent
 */
public interface ChatStatusChangedEventListener extends Listener {
	
	/**
	 * Chat status changed.
	 *
	 * @param event the event
	 */
	@ListenerMethod
	void chatStatusChanged(ChatStatusChangedEvent event);

}
