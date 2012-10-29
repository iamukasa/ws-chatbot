package de.vlad.supportcenter.web.common;

import org.springframework.roo.addon.serializable.RooSerializable;

import com.github.wolfie.blackboard.Blackboard;

import de.vlad.supportcenter.event.ChatStatusChangedEvent;
import de.vlad.supportcenter.event.RequestTabChangeEvent;
import de.vlad.supportcenter.event.listener.ChatStatusChangedEventListener;
import de.vlad.supportcenter.event.listener.RequestTabChangeEventListener;
import de.vlad.supportcenter.model.Portal;
import de.vlad.supportcenter.model.enums.SystemStatus;
import de.vlad.supportcenter.web.common.mainlayout.AbstractMainLayout;


/**
 * The Class Bootstrap.
 */
@RooSerializable
public abstract class Bootstrap {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 6637020903880867421L;

	/**
	 * Instantiates a new bootstrap.
	 */
	public Bootstrap() {
		Blackboard eventBus = AppRegistry.getEventBus();
//		eventBus.enableLogging();
		eventBus.register(RequestTabChangeEventListener.class, RequestTabChangeEvent.class);
		eventBus.register(ChatStatusChangedEventListener.class, ChatStatusChangedEvent.class);
		init();
	}
	
	/**
	 * Inits the.
	 */
	public abstract void init();
	
	/**
	 * Gets the main layout class.
	 *
	 * @return the main layout class
	 */
	public abstract Class<? extends AbstractMainLayout> getMainLayoutClass();
	
	/**
	 * Inits the portal.
	 *
	 * @param portalId the portal id
	 * @return the system status
	 */
	public static SystemStatus initPortal(String portalId) {
		try {
			Long id = Long.parseLong(portalId);
			Portal portal = Portal.findPortal(id);
			if(portal != null) {
				if(portal.getSystemStatus() == SystemStatus.ONLINE) {
					AppRegistry.setPortal(portal);
				}
				return portal.getSystemStatus();
			}
		} catch(Exception e) {}
		return SystemStatus.NOT_EXISTS;
	}

}
