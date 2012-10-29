package de.vlad.supportcenter.web.enquirer.tab;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

import com.github.wolfie.refresher.Refresher;
import com.github.wolfie.refresher.Refresher.RefreshListener;

import de.vlad.supportcenter.event.ChatStatusChangedEvent;
import de.vlad.supportcenter.event.listener.ChatStatusChangedEventListener;
import de.vlad.supportcenter.model.ChatSession;
import de.vlad.supportcenter.model.enums.SupportRole;
import de.vlad.supportcenter.model.manager.ChatManager;
import de.vlad.supportcenter.web.common.AbstractTab;
import de.vlad.supportcenter.web.common.AppRegistry;
import de.vlad.supportcenter.web.livechat.ChatEntranceWidget;
import de.vlad.supportcenter.web.livechat.ChatSessionEventType;
import de.vlad.supportcenter.web.livechat.ChatWidget;
import de.vlad.supportcenter.web.livechat.IUpdatableWidget;


/**
 * The Class RequestLiveChatTab.
 */
@Configurable
public class RequestLiveChatTab extends AbstractTab implements
		ChatStatusChangedEventListener {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -923662178215871620L;

	/** The current widget. */
	private IUpdatableWidget currentWidget;

	/** The refresher. */
	private Refresher refresher = new Refresher();

	/** The chat manager. */
	@Autowired
	private transient ChatManager chatManager;

	/**
	 * Instantiates a new request live chat tab.
	 */
	public RequestLiveChatTab() {
		super();

		refresher.addListener(new RefreshListener() {

			private static final long serialVersionUID = 6836667533678452211L;

			@Override
			public void refresh(Refresher source) {
				currentWidget.update();
			}

		});
	}

	// @SuppressWarnings("unused")
	// @PostConstruct
	// private void init() {
	// updateTab();
	// }

	/* (non-Javadoc)
	 * @see de.vlad.supportcenter.web.common.AbstractTab#updateTab()
	 */
	@Override
	public void updateTab() {

		ChatSession session = chatManager.getSessionByClientIp();
		if (session != null && !(currentWidget instanceof ChatWidget)) {
			this.removeAllComponents();
			currentWidget = new ChatWidget(session);
			((ChatWidget)currentWidget).addComponent(refresher);
			addComponent(currentWidget);
		} else if (session == null
				&& !(currentWidget instanceof ChatEntranceWidget)) {
			this.removeAllComponents();
			currentWidget = new ChatEntranceWidget(AppRegistry.isUserSupportAgent());
			addComponent(currentWidget);
		}
		currentWidget.update();
		if (currentWidget != null && currentWidget instanceof ChatWidget) {
			refresher.setRefreshInterval(1000);
			((ChatWidget)currentWidget).showWidgetEvent();
		}
	}

	/**
	 * Refresh chat.
	 */
	public void refreshChat() {
		currentWidget.update();
	}

	/* (non-Javadoc)
	 * @see de.vlad.supportcenter.event.listener.ChatStatusChangedEventListener#chatStatusChanged(de.vlad.supportcenter.event.ChatStatusChangedEvent)
	 */
	@Override
	public void chatStatusChanged(ChatStatusChangedEvent event) {
		if (event.getSupportRole() == SupportRole.ENQUIRER && (event.getSessionEventType() == ChatSessionEventType.CREATED
				|| event.getSessionEventType() == ChatSessionEventType.LEFT)) {
			updateTab();
		} else if(event.getSupportRole() == SupportRole.ENQUIRER) {
			currentWidget.update();
		}
	}

	/* (non-Javadoc)
	 * @see de.vlad.supportcenter.web.common.AbstractTab#hideTab()
	 */
	@Override
	public void hideTab() {
		refresher.setRefreshInterval(0);
		if (currentWidget != null && currentWidget instanceof ChatWidget) {
			((ChatWidget)currentWidget).hideWidgetEvent();
		}
	}

}
