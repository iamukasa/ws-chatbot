package de.vlad.supportcenter.web.livechat;

import com.vaadin.event.ShortcutAction;
import com.vaadin.event.ShortcutListener;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Form;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;

import de.vlad.superimmediatetextarea.SuperImmediateTextArea;
import de.vlad.superimmediatetextarea.SuperImmediateTextArea.KeyPressEvent;
import de.vlad.supportcenter.model.SimpleUser;
import de.vlad.supportcenter.web.common.resource.Css;
import de.vlad.supportcenter.web.common.widget.ConfirmResetFooter;
import de.vlad.supportcenter.web.common.widget.ConfirmResetFooter.ClickEventHandler;
import de.vlad.supportcenter.web.common.widget.UploadButton;
import de.vlad.supportcenter.web.common.widget.UploadButton.UploadFinishedListener;


/**
 * The Class ChatInputWidget.
 */
public class ChatInputWidget extends Form implements ClickEventHandler {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 6882548737668655583L;

	/** The chat widget. */
	private ChatWidget chatWidget;

	/** The info layout. */
	private HorizontalLayout infoLayout = new HorizontalLayout();

	/** The chatting with label. */
	private Label chattingWithLabel = new Label("");

	/** The is typing label. */
	private Label isTypingLabel = new Label("");
	
	/** The typing. */
	private boolean typing = false;
	
	/** The upload button. */
	private UploadButton uploadButton;

	/** The text area. */
	private SuperImmediateTextArea textArea;

	/** The enter listener. */
	private ShortcutListener enterListener;

	/** The points. */
	private String[] points = { "", ".", "..", "..." };

	/** The current point index. */
	private int currentPointIndex = 0;
	
	/**
	 * Instantiates a new chat input widget.
	 *
	 * @param chatWidget the chat widget
	 */
	public ChatInputWidget(ChatWidget chatWidget) {
		this.chatWidget = chatWidget;

		enterListener = new ShortcutListener(
				"Die Enter-Taste schickt die Nachricht ab",
				ShortcutAction.KeyCode.ENTER, null) {

			private static final long serialVersionUID = 5769375964545661L;

			@Override
			public void handleAction(Object sender, Object target) {
				ChatInputWidget.this.confirm();
			}

		};

		textArea = new SuperImmediateTextArea();
		textArea.setWidth("100%");
		textArea.setRows(3);
		textArea.setInputPrompt("Nachricht eingeben");
		textArea.addListener(new SuperImmediateTextArea.KeyPressListener() {

			private static final long serialVersionUID = 645621989544334L;

			public void keyPressed(final KeyPressEvent event) {
				typing = true;
			}
		});
		
		uploadButton = new UploadButton(new UploadFinishedListener() {
			
			@Override
			public void uploadFinished(String path) {
				ChatInputWidget.this.chatWidget.postFile(path);
			}
		});
		uploadButton.setWidth("150px");
		isTypingLabel.setWidth("200px");

		chattingWithLabel.setStyleName(Css.chatStatusLabel);

		infoLayout.setWidth("100%");
		infoLayout.addComponent(uploadButton);
		infoLayout.addComponent(chattingWithLabel);
		infoLayout.addComponent(isTypingLabel);
		infoLayout.setComponentAlignment(uploadButton,
				Alignment.MIDDLE_LEFT);
		infoLayout.setComponentAlignment(chattingWithLabel,
				Alignment.MIDDLE_LEFT);
		infoLayout.setComponentAlignment(isTypingLabel, Alignment.MIDDLE_RIGHT);
		infoLayout.setExpandRatio(chattingWithLabel, 0.9f);

		getLayout().addComponent(infoLayout);
		addField("editor", textArea);
		getLayout().setWidth("100%");
		getFooter().setWidth("100%");

		ConfirmResetFooter confirmResetWidget = new ConfirmResetFooter(this);
		confirmResetWidget.setMargin(true);
		
		getFooter().addComponent(confirmResetWidget);
		setStyleName(Css.addTicketEntryForm);
	}

	/* (non-Javadoc)
	 * @see de.vlad.supportcenter.web.common.widget.ConfirmResetFooter.ClickEventHandler#reset()
	 */
	@Override
	public void reset() {
		textArea.setValue("");
		getWindow().showNotification(
				"Die Eingabedaten wurden zurückgesetzt");
		typing = false;
	}

	/* (non-Javadoc)
	 * @see de.vlad.supportcenter.web.common.widget.ConfirmResetFooter.ClickEventHandler#confirm()
	 */
	@Override
	public void confirm() {
		Object value = textArea.getValue();
		if (value != null && !value.toString().trim().isEmpty()) {
			chatWidget.postEntry(value.toString().trim());
			textArea.setValue("");
			typing = false;
		} else {
			getWindow()
					.showNotification("",
							"Sie müssen zuerst eine Nachricht eingeben, bevor Sie diese abschicken können");
		}
	}

	/**
	 * Update.
	 */
	public void update() {
		SimpleUser chatPartner = chatWidget.getChatEntryManager().getPartner();
		chatWidget.getChatEntryManager().setChatTyping(typing);

		if (chatPartner != null
				&& chatWidget.getChatEntryManager().isChatPartnerTyping()) {
			isTypingLabel.setValue(chatPartner.getDisplayName() + " tippt ...");
		} else {
			isTypingLabel.setValue("");
		}
		
		if (chatPartner != null) {
			chattingWithLabel.setValue(chatPartner.getDisplayName()
					+ " chattet gerade mit Ihnen");
		} else if (!textArea.isEnabled()) {
			chattingWithLabel.setValue("Die Chat Sitzung wurde beendet");
		} else if(!chatWidget.getChatEntryManager().isChatCategoryAvailable()) {
			chattingWithLabel.setValue("Keine Mitarbeiter für diese Kategorie verfügbar");
		} else {
			chattingWithLabel.setValue("In der Support Warteschlange "
					+ points[getIncremetingPointIndex()]);
		}
		typing = false;
	}

	/**
	 * Gets the incremeting point index.
	 *
	 * @return the incremeting point index
	 */
	private int getIncremetingPointIndex() {
		int index = currentPointIndex;
		currentPointIndex = (currentPointIndex + 1) % points.length;
		return index;
	}

	/**
	 * Disable input.
	 */
	public void disableInput() {
		this.setEnabled(false);
	}
	
	public void enableInput() {
		this.setEnabled(true);
	}

	/**
	 * Enable enter shortcut.
	 */
	public void enableEnterShortcut() {
		textArea.addShortcutListener(enterListener);
	}

	/**
	 * Disable enter shortcut.
	 */
	public void disableEnterShortcut() {
		textArea.removeShortcutListener(enterListener);
	}

}
