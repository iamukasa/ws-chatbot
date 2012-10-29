package de.vlad.supportcenter.web.livechat;

import java.util.Arrays;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

import com.vaadin.data.Validator.EmptyValueException;
import com.vaadin.data.Validator.InvalidValueException;
import com.vaadin.data.util.BeanItem;
import com.vaadin.ui.Window.Notification;

import de.vlad.supportcenter.dto.FieldNames;
import de.vlad.supportcenter.dto.NewChatSessionDTO;
import de.vlad.supportcenter.event.ChatStatusChangedEvent;
import de.vlad.supportcenter.model.ChatSession;
import de.vlad.supportcenter.model.SupportUser;
import de.vlad.supportcenter.model.manager.ChatManager;
import de.vlad.supportcenter.web.common.AppRegistry;
import de.vlad.supportcenter.web.common.widget.ConfirmResetFooter;
import de.vlad.supportcenter.web.common.widget.fieldfactory.AbstractFieldFactory;
import de.vlad.supportcenter.web.common.widget.fieldfactory.NewChatSessionFieldFactory;
import de.vlad.supportcenter.web.common.widget.form.AbstractForm;


/**
 * The Class ChatEntranceWidget.
 */
@Configurable
public class ChatEntranceWidget extends AbstractForm implements IUpdatableWidget {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 2679086353924380769L;

	/** The chat session dto. */
	private NewChatSessionDTO chatSessionDTO = new NewChatSessionDTO();
	
	/** The field factory. */
	private NewChatSessionFieldFactory fieldFactory;

	/** The is category intern. */
	private boolean isCategoryIntern;

	/** The chat manager. */
	@Autowired
	private transient ChatManager chatManager;

	/**
	 * Instantiates a new chat entrance widget.
	 *
	 * @param isCategoryIntern the is category intern
	 */
	public ChatEntranceWidget(boolean isCategoryIntern) {
		this.isCategoryIntern = isCategoryIntern;
		this.setWidth("400px");
	}

	/**
	 * Inits the.
	 */
	@SuppressWarnings("unused")
	@PostConstruct
	private void init() {
		SupportUser user = AppRegistry.getLoggedInUser();
		fieldFactory = new NewChatSessionFieldFactory(isCategoryIntern);
		chatSessionDTO.setSupportCategory(fieldFactory.getDefaultSupportCategory());
		setCaption("Live-Chat Support Anfrage");
		setWriteThrough(false);
		setInvalidCommitted(false);

		ConfirmResetFooter confirmReset = new ConfirmResetFooter(this);

		getFooter().setWidth("100%");
		getFooter().addComponent(confirmReset);

		setItemDataSource(new BeanItem<NewChatSessionDTO>(chatSessionDTO));
		setFormFieldFactory(fieldFactory);

		setVisibleItemProperties(Arrays.asList(new String[] {
					FieldNames.DISPLAY_NAME, FieldNames.SUPPORT_CATEGORY }));

	}

	/* (non-Javadoc)
	 * @see de.vlad.supportcenter.web.common.widget.form.AbstractForm#resetForm(boolean)
	 */
	public void resetForm(boolean showNotification) {
		super.resetForm(showNotification);
	}

	/* (non-Javadoc)
	 * @see de.vlad.supportcenter.web.common.widget.ConfirmResetFooter.ClickEventHandler#confirm()
	 */
	@Override
	public void confirm() {
		boolean ipExists = false;
		try {
			this.commit();
			ChatSession chatSession = chatManager.createChatSession(chatSessionDTO);
			ipExists = chatSession == null;
			if (ipExists) {
				throw new Exception();
			}
			AppRegistry.getMainWindow()
			.showNotification(
					"LiveChat wurde beantragt",
					Notification.TYPE_HUMANIZED_MESSAGE);
			AppRegistry.getEventBus().fire(
					new ChatStatusChangedEvent(ChatSessionEventType.CREATED));
		} catch (EmptyValueException e) {
			AppRegistry.getMainWindow()
					.showNotification(
							"Eingaben unvollständig",
							"<br />Sie müssen das Formular komplett ausfüllen, damit wir Ihnen bei der Anfrager assistieren können.",
							Notification.TYPE_ERROR_MESSAGE);

		} catch (InvalidValueException e) {
			AppRegistry.getMainWindow()
					.showNotification(
							"Eingaben unvollständig",
							"<br />Sie müssen das Formular komplett ausfüllen, damit wir Ihnen bei der Anfrager assistieren können.",
							Notification.TYPE_ERROR_MESSAGE);
		} catch (Exception e) {
			if (ipExists) {
				AppRegistry.getMainWindow()
						.showNotification(
								"Sie haben bereits eine aktive Chatsitzung",
								"Um eine neue Chatsitzung anzufragen, müssen sie zunächst die alte Sitzung beenden",
								Notification.TYPE_ERROR_MESSAGE);
			} else {
				e.printStackTrace();
				AppRegistry.getMainWindow().showNotification(
						"Es ist etwas schief gegangen",
						"Versuchen Sie es später erneut",
						Notification.TYPE_ERROR_MESSAGE);
			}
		}
	}

	/* (non-Javadoc)
	 * @see de.vlad.supportcenter.web.common.widget.form.AbstractForm#getAbstractFieldFactory()
	 */
	@Override
	public AbstractFieldFactory getAbstractFieldFactory() {
		return fieldFactory;
	}

	/* (non-Javadoc)
	 * @see de.vlad.supportcenter.web.livechat.IUpdatableWidget#update()
	 */
	@Override
	public void update() {
	}

}
