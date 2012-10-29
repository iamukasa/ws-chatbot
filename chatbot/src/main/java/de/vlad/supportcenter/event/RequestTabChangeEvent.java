package de.vlad.supportcenter.event;

import com.github.wolfie.blackboard.Event;


/**
 * The Class RequestTabChangeEvent.
 */
public class RequestTabChangeEvent implements Event {
	
	/** The tabname. */
	private String tabname;

	/**
	 * Instantiates a new request tab change event.
	 *
	 * @param tabname the tabname
	 */
	public RequestTabChangeEvent(String tabname) {
		this.tabname = tabname;
	}

	/**
	 * Gets the tabname.
	 *
	 * @return the tabname
	 */
	public String getTabname() {
		return tabname;
	}

}
