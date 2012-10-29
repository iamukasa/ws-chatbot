package de.vlad.supportcenter.event.listener;

import com.github.wolfie.blackboard.Listener;
import com.github.wolfie.blackboard.annotation.ListenerMethod;

import de.vlad.supportcenter.event.RequestTabChangeEvent;


/**
 * The listener interface for receiving requestTabChangeEvent events.
 * The class that is interested in processing a requestTabChangeEvent
 * event implements this interface, and the object created
 * with that class is registered with a component using the
 * component's <code>addRequestTabChangeEventListener<code> method. When
 * the requestTabChangeEvent event occurs, that object's appropriate
 * method is invoked.
 *
 * @see RequestTabChangeEventEvent
 */
public interface RequestTabChangeEventListener extends Listener {
	
	/**
	 * Change tab.
	 *
	 * @param event the event
	 */
	@ListenerMethod
	void changeTab(RequestTabChangeEvent event);

}
