package de.vlad.supportcenter.web.common;

import java.io.File;
import java.io.Serializable;
import java.util.ResourceBundle;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import com.github.wolfie.blackboard.Blackboard;
import com.vaadin.Application;
import com.vaadin.service.ApplicationContext;
import com.vaadin.service.ApplicationContext.TransactionListener;
import com.vaadin.terminal.gwt.server.WebApplicationContext;
import com.vaadin.ui.Window;

import de.vlad.supportcenter.AbstractApplication;
import de.vlad.supportcenter.EnquirerApplication;
import de.vlad.supportcenter.model.Locale;
import de.vlad.supportcenter.model.Portal;
import de.vlad.supportcenter.model.SimpleUser;
import de.vlad.supportcenter.model.SupportAgent;
import de.vlad.supportcenter.model.SupportUser;


/** Holds data for one user session. */
public class AppRegistry implements TransactionListener, Serializable {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 14563456212355L;
	
	/** The bundle. */
	private ResourceBundle bundle;
	
	/** The locale. */
	private Locale locale; // Current locale
//	private String userData; // Trivial data model for the user

	/** The app. */
private Application app; // For distinguishing between apps

	/** The portal. */
	private Portal portal;
	
	/** The user. */
	private SupportUser user;
	
	/** The chat user. */
	private SimpleUser chatUser;

	/** The client ip. */
	private String clientIp = "";
	
	/** The host ip. */
	private String hostIp = "";

	/** The Constant instance. */
	public final static ThreadLocal<AppRegistry> instance = new ThreadLocal<AppRegistry>();

	/**
	 * Instantiates a new app registry.
	 *
	 * @param app the app
	 */
	public AppRegistry(Application app) {
		this.app = app;

		// It's usable from now on in the current request
		instance.set(this);
	}

	/* (non-Javadoc)
	 * @see com.vaadin.service.ApplicationContext.TransactionListener#transactionStart(com.vaadin.Application, java.lang.Object)
	 */
	@Override
	public void transactionStart(Application application, Object transactionData) {
		// Set this data instance of this application
		// as the one active in the current thread.
		if (this.app == application)
			instance.set(this);

		if (transactionData instanceof HttpServletRequest) {
			HttpServletRequest httpServletRequest = (HttpServletRequest) transactionData;
			clientIp = httpServletRequest.getRemoteAddr();
			hostIp = httpServletRequest.getRemoteHost();
		}
	}

	/* (non-Javadoc)
	 * @see com.vaadin.service.ApplicationContext.TransactionListener#transactionEnd(com.vaadin.Application, java.lang.Object)
	 */
	@Override
	public void transactionEnd(Application application, Object transactionData) {
		// Clear the reference to avoid potential problems
		if (this.app == application)
			instance.set(null);
	}

	/**
	 * Inits the locale.
	 *
	 * @param locale the locale
	 * @param bundleName the bundle name
	 */
	public static void initLocale(Locale locale, String bundleName) {
		instance.get().locale = locale;
		// instance.get().bundle =
		// ResourceBundle.getBundle(bundleName, new
		// java.util.Locale(locale.getLocaleKey()));
	}

	/**
	 * Sets the locale.
	 *
	 * @param locale the new locale
	 */
	public static void setLocale(Locale locale) {
		instance.get().locale = locale;
	}

	/**
	 * Gets the locale.
	 *
	 * @return the locale
	 */
	public static Locale getLocale() {
		return instance.get().locale;
	}

	/**
	 * Gets the message.
	 *
	 * @param msgId the msg id
	 * @return the message
	 */
	public static String getMessage(String msgId) {
		return instance.get().bundle.getString(msgId);
	}

	/**
	 * Gets the event bus.
	 *
	 * @return the event bus
	 */
	public static Blackboard getEventBus() {
		return getAbstractApplication().getEventBus();
	}

	/**
	 * Gets the logged in user.
	 *
	 * @return the logged in user
	 */
	public static SupportUser getLoggedInUser() {
		if(instance != null && instance.get() != null) {
			return instance.get().user;
		} else {
			return null;
		}
	}

	/**
	 * Sets the logged in user.
	 *
	 * @param user the new logged in user
	 */
	public static void setLoggedInUser(SupportUser user) {
		instance.get().user = user;
	}

	/**
	 * Gets the portal.
	 *
	 * @return the portal
	 */
	public static Portal getPortal() {
		return instance.get().portal;
	}

	/**
	 * Sets the portal.
	 *
	 * @param portal the new portal
	 */
	public static void setPortal(Portal portal) {
		instance.get().portal = portal;
	}

	/**
	 * Gets the enquirer application.
	 *
	 * @return the enquirer application
	 */
	public static EnquirerApplication getEnquirerApplication() {
		return (EnquirerApplication) instance.get().app;
	}
	
	/**
	 * Gets the abstract application.
	 *
	 * @return the abstract application
	 */
	public static AbstractApplication getAbstractApplication() {
		return (AbstractApplication) instance.get().app;
	}
	
	/**
	 * Gets the main window.
	 *
	 * @return the main window
	 */
	public static Window getMainWindow() {
		return instance.get().app.getMainWindow();
	}

	/**
	 * Gets the client ip.
	 *
	 * @return the client ip
	 */
	public static String getClientIp() {
		return instance.get().clientIp;
	}

	/**
	 * Gets the host ip.
	 *
	 * @return the host ip
	 */
	public static String getHostIp() {
		return instance.get().hostIp;
	}

	/**
	 * Gets the chat user.
	 *
	 * @return the chat user
	 */
	public static SimpleUser getChatUser() {
		if(instance.get().chatUser != null) {
			return instance.get().chatUser;
		} else {
			return instance.get().user;
		}
	}

	/**
	 * Sets the chat user.
	 *
	 * @param chatUser the new chat user
	 */
	public static void setChatUser(SimpleUser chatUser) {
		instance.get().chatUser = chatUser;
	}
	
	/**
	 * Checks if is user support agent.
	 *
	 * @return true, if is user support agent
	 */
	public static boolean isUserSupportAgent() {
		return instance.get().user != null && instance.get().user instanceof SupportAgent;
	}
	
	/**
	 * Gets the upload dir.
	 *
	 * @return the upload dir
	 */
	public static String getUploadDir() {
	    ApplicationContext ctx = instance.get().app.getContext();
	    if (ctx == null) return null;
	    final ServletContext sCtx = ((WebApplicationContext)ctx).getHttpSession().getServletContext();
	    return sCtx.getRealPath("VAADIN" + File.separator + "uploads" + File.separator);
	}
	
}