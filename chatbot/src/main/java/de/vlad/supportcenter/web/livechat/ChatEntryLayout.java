package de.vlad.supportcenter.web.livechat;

import com.vaadin.ui.CustomLayout;
import com.vaadin.ui.Label;

import de.vlad.supportcenter.model.ChatEntry;
import de.vlad.supportcenter.web.common.Utils;


/**
 * The Class ChatEntryLayout.
 */
public class ChatEntryLayout extends CustomLayout {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -3251221139761596323L;
	
	/**
	 * Instantiates a new chat entry layout.
	 *
	 * @param entry the entry
	 */
	public ChatEntryLayout(ChatEntry entry) {
		super("../../supportcenter/layouts/ChatEntry");
		
		String time = Utils.getFormattedTime(entry.getTimeCreated());
		String message = entry.getComment();
		Label timeLabel = new Label(time);
		Label messageLabel = new Label(message);
		messageLabel.setContentMode(Label.CONTENT_PREFORMATTED);
		addComponent(timeLabel, "chatEntryTime");
		addComponent(messageLabel, "entryContent");
		
	}

}
