package de.vlad.supportcenter.web.livechat;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.Reindeer;

import de.vlad.supportcenter.event.ChatStatusChangedEvent;
import de.vlad.supportcenter.model.enums.SupportRole;
import de.vlad.supportcenter.model.manager.ChatEntryManager;
import de.vlad.supportcenter.model.manager.ChatManager;
import de.vlad.supportcenter.model.manager.ChatPermissionManager;
import de.vlad.supportcenter.web.common.AppRegistry;
import de.vlad.supportcenter.web.common.widget.LabeledData;


/**
 * The Class ChatInfoWidget.
 */
@Configurable
public class ChatInfoWidget extends VerticalLayout {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 8104243239938499574L;
	
	/** The first level info wrapper. */
	private HorizontalLayout firstLevelInfoWrapper = new HorizontalLayout();
	
	/** The second level info wrapper. */
	private HorizontalLayout secondLevelInfoWrapper = new HorizontalLayout();
	
	/** The category label. */
	private LabeledData categoryLabel;
	
	/** The leave button. */
	private Button leaveButton = new Button("LiveChat verlassen");
	
	/** The show contact reason button. */
	private Button showContactReasonButton = new Button("Kontaktgrund zeigen");
	
	/** The pass button. */
	private Button passButton = new Button("Passen");
	
	/** The chat entry manager. */
	private ChatEntryManager chatEntryManager;
	
	/** The chat manager. */
	@Autowired
	private transient ChatManager chatManager;
	
	/**
	 * Instantiates a new chat info widget.
	 *
	 * @param chatEntryManager the chat entry manager
	 */
	public ChatInfoWidget(ChatEntryManager chatEntryManager) {
		this.chatEntryManager = chatEntryManager;
		setWidth("100%");

		Label caption = new Label("LiveChat Online");
		caption.setStyleName(Reindeer.LABEL_H2);
		
		addComponent(caption);
		firstLevelInfoWrapper.setMargin(true, false, false, false);
		addComponent(firstLevelInfoWrapper);
		addComponent(secondLevelInfoWrapper);
		
	}

	/**
	 * Inits the infos.
	 */
	@SuppressWarnings("unused")
	@PostConstruct
	private void initInfos() {
		categoryLabel = new LabeledData();
		categoryLabel.setCaptionContent("Support Kategorie");
		categoryLabel.setDataContent(chatEntryManager.getChatSession().getSupportCategory().getName());
		categoryLabel.setMargin(false, true, false, true);
		
		final SupportRole role = new ChatPermissionManager(chatEntryManager.getChatSession()).getSupportRole();
		
		leaveButton.addListener(new ClickListener() {

			private static final long serialVersionUID = 434165464532432L;

			@Override
			public void buttonClick(ClickEvent event) {
				chatManager.leaveSession(chatEntryManager.getChatSession());
				AppRegistry.setChatUser(null);
				AppRegistry.getEventBus().fire(new ChatStatusChangedEvent(ChatSessionEventType.LEFT, role));
			}
			
		});
		leaveButton.setStyleName(Reindeer.BUTTON_SMALL);
		
		passButton.addListener(new ClickListener() {

			private static final long serialVersionUID = 43464532432L;

			@Override
			public void buttonClick(ClickEvent event) {
				chatManager.passSessionToOtherAgents(chatEntryManager.getChatSession());
				AppRegistry.setChatUser(null);
				AppRegistry.getEventBus().fire(new ChatStatusChangedEvent(ChatSessionEventType.LEFT, role));
			}
			
		});
		passButton.setStyleName(Reindeer.BUTTON_SMALL);
		
		showContactReasonButton.addListener(new ClickListener() {
			
			private static final long serialVersionUID = 4341654645324432L;

			@Override
			public void buttonClick(ClickEvent event) {
				showContactReason();
			}
			
		});
		showContactReasonButton.setStyleName(Reindeer.BUTTON_SMALL);
		
		firstLevelInfoWrapper.setWidth("100%");
		secondLevelInfoWrapper.setWidth("100%");
		
		HorizontalLayout leftSideWrapper = new HorizontalLayout();
		HorizontalLayout rightSideWrapper = new HorizontalLayout();
		leftSideWrapper.setSpacing(true);
		leftSideWrapper.addComponent(categoryLabel);
		rightSideWrapper.setSpacing(true);
		
		if(chatEntryManager.getContactReason() != null) {
			leftSideWrapper.addComponent(showContactReasonButton);
		}
		if(role == SupportRole.SUPPORT_AGENT) {
			rightSideWrapper.addComponent(passButton);
		}
		rightSideWrapper.addComponent(leaveButton);
		
		firstLevelInfoWrapper.addComponent(leftSideWrapper);
		firstLevelInfoWrapper.setComponentAlignment(leftSideWrapper, Alignment.MIDDLE_LEFT);
		firstLevelInfoWrapper.addComponent(rightSideWrapper);
		firstLevelInfoWrapper.setComponentAlignment(rightSideWrapper, Alignment.MIDDLE_RIGHT);
		
//		update();
	}

	/**
	 * Update.
	 */
	public void update() {
		categoryLabel.setDataContent(chatEntryManager.getChatSession().getSupportCategory().getName());
	}
	
	/**
	 * Show contact reason.
	 */
	public void showContactReason() {
		Window subwindow = new Window("Grund für die LiveChat Anfrage");
		subwindow.setModal(true);
		Label contactReason = new Label(chatEntryManager.getContactReason().getComment());
		subwindow.addComponent(contactReason);
		subwindow.setWidth("80%");
		AppRegistry.getAbstractApplication().getMainWindow().addWindow(subwindow);
		subwindow.center();
	}
	
	

}
