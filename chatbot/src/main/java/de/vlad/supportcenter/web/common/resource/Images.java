package de.vlad.supportcenter.web.common.resource;

import com.vaadin.terminal.Resource;
import com.vaadin.terminal.ThemeResource;


/**
 * The Class Images.
 */
public class Images {

	/**
	 * Gets the removes the icon.
	 *
	 * @return the removes the icon
	 */
	public static Resource getRemoveIcon() {
		return new ThemeResource("./../supportcenter/img/removeIcon.png");
	}
	
	/**
	 * Gets the save icon.
	 *
	 * @return the save icon
	 */
	public static Resource getSaveIcon() {
		return new ThemeResource("./../supportcenter/img/save-icon.png");
	}

	/**
	 * Gets the header.
	 *
	 * @return the header
	 */
	public static Resource getHeader() {
		return new ThemeResource("./../supportcenter/img/header.png");
	}

	/**
	 * Gets the start intro.
	 *
	 * @return the start intro
	 */
	public static Resource getStartIntro() {
		return new ThemeResource("./../supportcenter/img/StartIntro.png");
	}

	/**
	 * Gets the start login.
	 *
	 * @return the start login
	 */
	public static Resource getStartLogin() {
		return new ThemeResource("./../supportcenter/img/StartLogin.png");
	}

	/**
	 * Gets the start new ticket.
	 *
	 * @return the start new ticket
	 */
	public static Resource getStartNewTicket() {
		return new ThemeResource("./../supportcenter/img/StartNewTicket.png");
	}

}
