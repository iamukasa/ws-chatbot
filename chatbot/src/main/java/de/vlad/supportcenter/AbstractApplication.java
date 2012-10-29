package de.vlad.supportcenter;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.transaction.annotation.Transactional;

import com.github.wolfie.blackboard.Blackboard;
import com.vaadin.Application;
import com.vaadin.ui.Window;
import com.vaadin.ui.Window.ResizeEvent;
import com.vaadin.ui.Window.ResizeListener;

import de.vlad.supportcenter.model.Portal;
import de.vlad.supportcenter.model.manager.SupportUserManager;
import de.vlad.supportcenter.web.common.AppRegistry;
import de.vlad.supportcenter.web.common.DataPopulator;
import de.vlad.supportcenter.web.common.Utils;
import de.vlad.supportcenter.web.common.mainlayout.AbstractMainLayout;


/**
 * The Class AbstractApplication.
 */
@Configurable
public abstract class AbstractApplication extends Application {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -3415696471007594413L;

	/** The Constant LOG. */
	protected static final Logger LOG = Logger
			.getLogger(AbstractApplication.class);

	/** The event bus. */
	private transient Blackboard eventBus;
	
	/** The main window. */
	protected Window mainWindow = new Window();

	/** The support user manager. */
	@Autowired
	protected transient SupportUserManager supportUserManager;

	/* (non-Javadoc)
	 * @see com.vaadin.Application#init()
	 */
	@Override
	@Transactional
	public void init() {
		LOG.debug("start initializing the vaadin application");
		eventBus = new Blackboard();
		AppRegistry sessionData = new AppRegistry(this);

		getContext().addTransactionListener(sessionData);

		initMainWindow();
		
		if(Portal.countPortals() == 0) {
			DataPopulator populator = new DataPopulator();
			populator.populateData();
		}

	}
	
	/**
	 * Inits the main window.
	 */
	public void initMainWindow() {
		mainWindow.addListener(new ResizeListener() {

			private static final long serialVersionUID = 145345345345353L;

			@Override
			public void windowResized(ResizeEvent e) {
				float relWidth = mainWindow.getWidth();
				if (relWidth < 764.0f) {
					mainWindow.getContent().setWidth("764px");
				} else {
					mainWindow.getContent().setWidth("100%");
				}
			}
		});
		mainWindow.setImmediate(true);
		setMainWindow(mainWindow);
	}

	/**
	 * Sets the main layout.
	 *
	 * @param windowClass the new main layout
	 */
	public void setMainLayout(
			Class<? extends AbstractMainLayout> windowClass) {
		setTheme(Utils.getCurrentThemeName());
		AbstractMainLayout newMainLayout = null;
		try {
			newMainLayout = windowClass.newInstance();
		} catch (Exception e) {
			LOG.error("could not instantiate a main layout");
			e.printStackTrace();
		}

		mainWindow.setContent(newMainLayout);
		mainWindow.setCaption(newMainLayout.getTitleForWindow());
	}

	/**
	 * Gets the event bus.
	 *
	 * @return the event bus
	 */
	public Blackboard getEventBus() {
		return eventBus;
	}
	
	/**
	 * Gets the system messages.
	 *
	 * @return the system messages
	 */
	public static SystemMessages  getSystemMessages() {
		CustomizedSystemMessages systemMessages = new CustomizedSystemMessages();
        systemMessages.setCommunicationErrorCaption("Problem mit der Verbindung");
        systemMessages.setCommunicationErrorMessage("Die Verbindung zum Server ist fehlgeschlangen, klicken Sie <u>hier</u> um fortzufahren.");
        systemMessages.setSessionExpiredCaption("Ihre Sitzung ist abgelaufen");
        systemMessages.setSessionExpiredMessage("Vergewissern Sie sich, dass alle Daten bereits gespeichert wurden. Klicken Sie <u>hier</u> um fortzufahren.");
        systemMessages.setAuthenticationErrorCaption("Authentifizierung fehlgeschlagen");
        systemMessages.setAuthenticationErrorMessage("Vergewissern Sie sich, dass alle Daten bereits gespeichert wurden. Klicken Sie <u>hier</u> um fortzufahren.");
        systemMessages.setInternalErrorCaption("Interner Fehler");
        systemMessages.setInternalErrorMessage("Kontaktieren Sie den Administrator.<br />Vergewissern Sie sich, dass alle Daten bereits gespeichert wurden. Klicken Sie <u>hier</u> um fortzufahren.");
        systemMessages.setOutOfSyncCaption("Synchronisationsproblem");
        systemMessages.setOutOfSyncMessage("Etwas hat ein Synchronisationsproblem von Ihrem aktuellen Datenbestand mit dem Server verursacht.<br />Vergewissern Sie sich, dass alle Daten bereits gespeichert wurden. Klicken Sie <u>hier</u> um fortzufahren.");
        systemMessages.setCookiesDisabledCaption("Cookies sind deaktiviert");
        systemMessages.setCookiesDisabledMessage("Um die Webanwendung bedienen zu können, müssen die Cookies in Ihrem Browser aktiviert sein<br />Klicken Sie <u>hier</u> um die Cookie-Einstellung nochmal zu überprüfen");
        return systemMessages;
	}

}
