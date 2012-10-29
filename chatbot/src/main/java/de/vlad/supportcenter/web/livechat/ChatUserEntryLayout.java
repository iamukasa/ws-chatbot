package de.vlad.supportcenter.web.livechat;

import com.vaadin.ui.CustomLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

import de.vlad.supportcenter.model.ChatEntry;
import de.vlad.supportcenter.model.SimpleUser;
import de.vlad.supportcenter.model.enums.EntryType;
import de.vlad.supportcenter.model.manager.enums.SystemNotification;
import de.vlad.supportcenter.web.common.AppRegistry;
import de.vlad.supportcenter.web.common.Utils;
import de.vlad.supportcenter.web.common.resource.Css;


/**
 * The Class ChatUserEntryLayout.
 */
public class ChatUserEntryLayout extends CustomLayout {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -3251221139761596323L;

	/** The user id. */
	private Long userId;

	/** The chat entries. */
	private VerticalLayout chatEntries = new VerticalLayout();

	/**
	 * Instantiates a new chat user entry layout.
	 *
	 * @param entry the entry
	 */
	public ChatUserEntryLayout(ChatEntry entry) {
		super("../../supportcenter/layouts/ChatUserEntry");
		userId = entry.getUserPermission().getChatUser().getId();
		boolean incoming = AppRegistry.getChatUser() != null ? !userId.equals(AppRegistry.getChatUser().getId()) : !userId.equals(AppRegistry.getLoggedInUser().getId());
		if (incoming) {
			addStyleName(Css.chatEntryIncoming);
		}

		Label userAndDateLabel = getUserAndDateLabel(entry);

		chatEntries.addStyleName(Css.clearLeft);
		addComponent(userAndDateLabel, "userAndDateLabel");
		addComponent(chatEntries, "chatEntries");
		
		if (entry.getType() == EntryType.COMMENT) {
			addChatEntry(entry);
		} else if(entry.getType() == EntryType.FILE) {
			addFileEntry(entry);
		}

	}

	/**
	 * Checks if is entry from user.
	 *
	 * @param user the user
	 * @return true, if is entry from user
	 */
	public boolean isEntryFromUser(SimpleUser user) {
		return userId.equals(user.getId());
	}

	/**
	 * Adds the chat entry.
	 *
	 * @param entry the entry
	 */
	public void addChatEntry(ChatEntry entry) {
		ChatEntryLayout entryLayout = new ChatEntryLayout(entry);
		chatEntries.addComponent(entryLayout);
	}

	/**
	 * Adds the file entry.
	 *
	 * @param entry the entry
	 */
	public void addFileEntry(ChatEntry entry) {
		ChatFileEntryLayout entryLayout = new ChatFileEntryLayout(entry);
		chatEntries.addComponent(entryLayout);
	}
	
	/**
	 * Gets the user and date label.
	 *
	 * @param entry the entry
	 * @return the user and date label
	 */
	private Label getUserAndDateLabel(ChatEntry entry) {
		Label userAndDateLabel = null;
		String displayName = entry.getUserPermission().getChatUser()
				.getDisplayName();
		if (entry.getType() == EntryType.COMMENT) {
			String date = Utils.getFormattedDate(entry.getTimeCreated());
			userAndDateLabel = new Label(displayName + " schreibt am " + date + " ...");
		} else if(entry.getType() == EntryType.SYSTEM_NOTIFICATION) {
			String systemComment = entry.getComment();
			String displayComment = "";
			String datetime = Utils.getFormattedDateTime(entry.getTimeCreated());
			if(systemComment.equals(SystemNotification.CREATED.toString())) {
				displayComment = displayName + " beantragt um "+datetime+" LiveChat Support";
			} else if(systemComment.equals(SystemNotification.CLOSED.toString())) {
				displayComment = displayName + " beendet um "+datetime+" seine LiveChat Sitzung";
			} else if(systemComment.equals(SystemNotification.AGENT_ASSIGN.toString())) {
				displayComment = displayName + " nimmt um "+datetime+" die LiveChat Anfrage entgegen";
			} else if(systemComment.equals(SystemNotification.CO_AGENT_ASSIGN.toString())) {
				displayComment = displayName + " kommt zur Hilfe um "+datetime;
			} else if(systemComment.equals(SystemNotification.AGENT_PASS.toString())) {
				displayComment = displayName + " leitet um "+datetime+" die Anfrage an andere Mitarbeiter weiter";
			} else if(systemComment.equals(SystemNotification.CO_AGENT_LEAVE.toString())) {
				displayComment = displayName + " verlässt die LiveChat Sitzung um "+datetime;
			}
			userAndDateLabel = new Label(displayComment);
		} else {
			String datetime = Utils.getFormattedDateTime(entry.getTimeCreated());
			userAndDateLabel = new Label(displayName + " lädt am " + datetime + " eine Datei hoch:");
		}
		userAndDateLabel.setWidth("100%");
		
		return userAndDateLabel;
	}

}
