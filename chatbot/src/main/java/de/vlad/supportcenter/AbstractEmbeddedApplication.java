package de.vlad.supportcenter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.vaadin.terminal.gwt.server.HttpServletRequestListener;
import com.vaadin.ui.Window.CloseEvent;
import com.vaadin.ui.Window.CloseListener;
import com.vaadin.ui.Window.Notification;

import de.vlad.supportcenter.model.Portal;
import de.vlad.supportcenter.model.enums.SystemStatus;
import de.vlad.supportcenter.web.common.AppRegistry;
import de.vlad.supportcenter.web.common.Bootstrap;


/**
 * The Class AbstractEmbeddedApplication.
 */
public abstract class AbstractEmbeddedApplication extends AbstractApplication implements HttpServletRequestListener {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -536380094960533918L;
	
	/** The system status. */
	protected SystemStatus systemStatus = null;
	
	/** The portal param value. */
	protected String portalParamValue = null;

	/* (non-Javadoc)
	 * @see de.vlad.supportcenter.AbstractApplication#init()
	 */
	@Override
	public void init() {
		super.init();
		portalParamValue = "2";
		if(systemStatus == null && portalParamValue != null) {
			systemStatus = Bootstrap.initPortal(portalParamValue);
		}
		if(systemStatus == null || systemStatus == SystemStatus.NOT_EXISTS) {
			mainWindow.showNotification("Das Support System existiert nicht", "Unter der URL ist kein Support System registriert", Notification.TYPE_ERROR_MESSAGE);
		} else if(systemStatus == SystemStatus.OFFLINE) {
			mainWindow.showNotification("Das Support System ist offline", "Versuchen Sie es später erneut", Notification.TYPE_ERROR_MESSAGE);
		} else {
			initEmbedded();
		}
		mainWindow.addListener(new CloseListener() {
			
			private static final long serialVersionUID = 75675353578765676L;

			@Override
			public void windowClose(CloseEvent e) {
				if(AppRegistry.getPortal() == null) {
					AbstractEmbeddedApplication.this.close();
				} else {
					Portal refreshedPortal = Portal.findPortal(AppRegistry.getPortal().getId());
					if(refreshedPortal == null || !refreshedPortal.getLastChange().equals(AppRegistry.getPortal().getLastChange())) {
						AbstractEmbeddedApplication.this.close();
					}
				}
			}
		});
	}
	
	/**
	 * Inits the embedded.
	 */
	public abstract void initEmbedded();
	
	/* (non-Javadoc)
	 * @see com.vaadin.terminal.gwt.server.HttpServletRequestListener#onRequestStart(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	public void onRequestStart(HttpServletRequest request,
			HttpServletResponse response) {
		String paramValue = request.getParameter("portal");
		if(paramValue != null) {
			if(portalParamValue != null && !portalParamValue.equals(paramValue)) {
				systemStatus = null;
				this.close();
			}
			portalParamValue = paramValue;
		}
	}



	/* (non-Javadoc)
	 * @see com.vaadin.terminal.gwt.server.HttpServletRequestListener#onRequestEnd(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	public void onRequestEnd(HttpServletRequest request,
			HttpServletResponse response) {
	}

}
