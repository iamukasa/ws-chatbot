package de.vlad.supportcenter.web.enquirer.window;

import com.github.wolfie.blackboard.Blackboard;

import de.vlad.supportcenter.event.listener.RequestTabChangeEventListener;
import de.vlad.supportcenter.web.common.AppRegistry;
import de.vlad.supportcenter.web.common.mainlayout.AbstractSupportMainLayout;
import de.vlad.supportcenter.web.enquirer.tab.RequestLiveChatTab;


/**
 * The Class UnauthenticatedEnquirerAreaMainLayout.
 */
public class UnauthenticatedEnquirerAreaMainLayout extends AbstractSupportMainLayout implements RequestTabChangeEventListener{
	
	/** The request live chat tab. */
	private RequestLiveChatTab requestLiveChatTab = new RequestLiveChatTab();
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 2429245490735L;
	
	/**
	 * Instantiates a new unauthenticated enquirer area main layout.
	 */
	public UnauthenticatedEnquirerAreaMainLayout() {
		super("Ticket Support System");
		
		if(AppRegistry.getPortal().isChatEnabled()) {
			addTab(requestLiveChatTab, "request-live-chat", "Live-Chat");
		}
		
	}
	
	/* (non-Javadoc)
	 * @see de.vlad.supportcenter.web.common.mainlayout.AbstractMainLayout#detach()
	 */
	@Override
	public void detach() {
		Blackboard eventBus = AppRegistry.getEventBus();
		eventBus.removeListener(requestLiveChatTab);
		super.detach();
	}
	
	/* (non-Javadoc)
	 * @see de.vlad.supportcenter.web.common.mainlayout.AbstractMainLayout#attach()
	 */
	@Override
	public void attach() {
		Blackboard eventBus = AppRegistry.getEventBus();
		eventBus.addListener(requestLiveChatTab);
		super.attach();
	}
	

}
