package de.vlad.supportcenter.web.livechat;

import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Window;

import de.vlad.supportcenter.model.ChatSession;
import de.vlad.supportcenter.model.enums.ChatStatus;
import de.vlad.supportcenter.model.manager.ChatEntryManager;
import de.vlad.supportcenter.web.common.AppRegistry;


/**
 * The Class ChatWidget.
 */
public class ChatWidget extends GridLayout implements IUpdatableWidget {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 9223145634126768992L;
	
	/** The chat info widget. */
	private ChatInfoWidget chatInfoWidget;
	
	/** The chat entry widget. */
	private ChatEntryWidget chatEntryWidget;
	
	/** The input widget. */
	private ChatInputWidget inputWidget;
	
	/** The chat entry manager. */
	private ChatEntryManager chatEntryManager;
	
	/**
	 * Instantiates a new chat widget.
	 *
	 * @param session the session
	 */
	public ChatWidget(ChatSession session) {
		super(1, 3);
		this.chatEntryManager = new ChatEntryManager(session);
		this.setSpacing(true);
		this.setWidth("100%");
		chatInfoWidget = new ChatInfoWidget(chatEntryManager);
		chatEntryWidget = new ChatEntryWidget(chatEntryManager);
		inputWidget = new ChatInputWidget(this);
		
		addComponent(chatInfoWidget, 0, 0);
		addComponent(chatEntryWidget, 0, 1);
		addComponent(inputWidget, 0, 2);
		this.setRowExpandRatio(1, 1.0f);
		
	}
	
	/**
	 * Post entry.
	 *
	 * @param entry the entry
	 */
	public void postEntry(String entry) {
		chatEntryManager.addChatEntry(entry);
		chatEntryWidget.update(getWindow());
	}
	
	/**
	 * Post file.
	 *
	 * @param path the path
	 */
	public void postFile(String path) {
		chatEntryManager.addFileEntry(path);
		chatEntryWidget.update(getWindow());
	}
	
	/* (non-Javadoc)
	 * @see de.vlad.supportcenter.web.livechat.IUpdatableWidget#update()
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
		chatEntryWidget.update(window);
		chatInfoWidget.update();
		if(chatEntryManager.getChatSession().getStatus() == ChatStatus.CLOSED) {
			inputWidget.disableInput();
		} else if(!inputWidget.isEnabled()){
			inputWidget.enableInput();
		}
		inputWidget.update();
	}
	
	/**
	 * Hide widget event.
	 */
	public void hideWidgetEvent() {
		hideWidgetEvent(AppRegistry.getMainWindow());
	}
	
	/**
	 * Hide widget event.
	 *
	 * @param window the window
	 */
	public void hideWidgetEvent(Window window) {
		inputWidget.disableEnterShortcut();
		chatEntryWidget.hideWidgetEvent(window);
	}
	
	/**
	 * Show widget event.
	 */
	public void showWidgetEvent() {
		showWidgetEvent(AppRegistry.getMainWindow());
	}
	
	/**
	 * Gets the chat entry manager.
	 *
	 * @return the chat entry manager
	 */
	public ChatEntryManager getChatEntryManager() {
		return chatEntryManager;
	}

	/**
	 * Show widget event.
	 *
	 * @param window the window
	 */
	public void showWidgetEvent(Window window) {
		inputWidget.enableEnterShortcut();
		chatEntryWidget.showWidgetEvent(window);
	}
	
}
