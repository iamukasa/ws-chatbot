package de.vlad.supportcenter.web.livechat;

import java.util.Collections;
import java.util.List;

import com.vaadin.ui.Panel;
import com.vaadin.ui.Window;
import com.vaadin.ui.Window.ResizeEvent;
import com.vaadin.ui.Window.ResizeListener;

import de.vlad.supportcenter.model.ChatEntry;
import de.vlad.supportcenter.model.enums.EntryType;
import de.vlad.supportcenter.model.manager.ChatEntryManager;
import de.vlad.supportcenter.web.common.AppRegistry;


/**
 * The Class ChatEntryWidget.
 */
public class ChatEntryWidget extends Panel {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 4327427611638141262L;

	/** The latest entry id. */
	private long latestEntryId = 0L;

	/** The chat entry manager. */
	private ChatEntryManager chatEntryManager;

	/** The last user entry layout. */
	private ChatUserEntryLayout lastUserEntryLayout;
	
	/** The resize listener. */
	private ResizeListener resizeListener;
	
	/** The current height. */
	private float currentHeight;

	/**
	 * Instantiates a new chat entry widget.
	 *
	 * @param chatEntryManager the chat entry manager
	 */
	public ChatEntryWidget(ChatEntryManager chatEntryManager) {
		this.chatEntryManager = chatEntryManager;
		this.setScrollable(true);

		this.resizeListener = new ResizeListener() {

			private static final long serialVersionUID = 145345345345353L;

			@Override
			public void windowResized(ResizeEvent e) {
				updateHeight(e.getWindow());
			}
		};
		this.setImmediate(true);
		
//		update();
	}

	/**
	 * Update.
	 */
	public void update() {
		update(AppRegistry.getMainWindow());
	}
	
	/**
	 * Update.
	 *
	 * @param window the window
	 */
	public void update(Window window) {
		List<ChatEntry> latestChatEntries = chatEntryManager
				.getLatestChatEntries(latestEntryId);
		Collections.reverse(latestChatEntries);
		for (ChatEntry chatEntry : latestChatEntries) {
			addChatEntryToLayout(chatEntry);
			latestEntryId = chatEntry.getId();
			window.scrollIntoView(this.getContent());
		}
	}

	/**
	 * Adds the chat entry to layout.
	 *
	 * @param chatEntry the chat entry
	 */
	private void addChatEntryToLayout(ChatEntry chatEntry) {
		if(chatEntry.getType() == EntryType.SYSTEM_NOTIFICATION || chatEntry.getType() == EntryType.FILE) {
			lastUserEntryLayout = null;
			addComponent(new ChatUserEntryLayout(chatEntry));
		} else {
			if (lastUserEntryLayout == null
					|| !lastUserEntryLayout.isEntryFromUser(chatEntry
							.getUserPermission().getChatUser())) {
				lastUserEntryLayout = new ChatUserEntryLayout(chatEntry);
				this.addComponent(lastUserEntryLayout);
			} else {
				lastUserEntryLayout.addChatEntry(chatEntry);
			}
		}
	}
	
	/**
	 * Update height.
	 */
	public void updateHeight() {
		updateHeight(AppRegistry.getAbstractApplication().getMainWindow());
	}
	
	/**
	 * Update height.
	 *
	 * @param window the window
	 */
	public void updateHeight(Window window) {
		Window mainWindow = AppRegistry.getAbstractApplication().getMainWindow();
		float height = mainWindow.getHeight();
		if(height < 593f) {
			currentHeight = 155f;
		} else {
			currentHeight = height - 438f;
		}
		this.setHeight(currentHeight + "px");
	}
	
	
	/**
	 * Show widget event.
	 */
	public void showWidgetEvent() {
		showWidgetEvent(AppRegistry.getMainWindow());
	}
	
	/**
	 * Hide widget event.
	 */
	public void hideWidgetEvent() {
		hideWidgetEvent(AppRegistry.getMainWindow());
	}

	/**
	 * Show widget event.
	 *
	 * @param window the window
	 */
	public void showWidgetEvent(Window window) {
		window.addListener(resizeListener);
		updateHeight(window);
	}
	
	/**
	 * Hide widget event.
	 *
	 * @param window the window
	 */
	public void hideWidgetEvent(Window window) {
		if(window != null) {
			window.removeListener(resizeListener);
		}
	}
}
