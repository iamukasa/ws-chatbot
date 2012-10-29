package de.vlad.supportcenter.web.common.mainlayout;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.github.wolfie.sessionguard.SessionGuard;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.TabSheet.SelectedTabChangeEvent;
import com.vaadin.ui.TabSheet.SelectedTabChangeListener;
import com.vaadin.ui.UriFragmentUtility;
import com.vaadin.ui.UriFragmentUtility.FragmentChangedEvent;
import com.vaadin.ui.UriFragmentUtility.FragmentChangedListener;
import com.vaadin.ui.VerticalLayout;

import de.vlad.supportcenter.event.RequestTabChangeEvent;
import de.vlad.supportcenter.event.listener.RequestTabChangeEventListener;
import de.vlad.supportcenter.web.common.AbstractTab;
import de.vlad.supportcenter.web.common.AppRegistry;


/**
 * The Class AbstractMainLayout.
 */
public abstract class AbstractMainLayout extends VerticalLayout implements
		RequestTabChangeEventListener {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 620937931049188561L;
	
	/** The title for window. */
	private String titleForWindow = "";

	/** The tabsheet. */
	protected TabSheet tabsheet = new TabSheet();
	
	/** The head wrapper. */
	protected HorizontalLayout headWrapper = new HorizontalLayout();

	/** The prev tab. */
	protected AbstractTab prevTab;

	/** The history. */
	protected final UriFragmentUtility history;
	
	/** The tabs. */
	protected Map<String, AbstractTab> tabs = new HashMap<String, AbstractTab>();

	/**
	 * Instantiates a new abstract main layout.
	 *
	 * @param title the title
	 */
	public AbstractMainLayout(String title) {
		this.titleForWindow = title;
		setWidth("100%");
		setMargin(true);
		
		headWrapper.setWidth("100%");
		headWrapper.setMargin(false, false, true, false);
		addComponent(headWrapper);

		tabsheet.setSizeFull();

		addComponent(tabsheet);
		
		SessionGuard sessionGuard = new SessionGuard();
		sessionGuard.setKeepalive(true);
		addComponent(sessionGuard);

		history = new UriFragmentUtility();
		addComponent(history);

		tabsheet.addListener(new SelectedTabChangeListener() {

			private static final long serialVersionUID = 15345345345345L;

			@Override
			public void selectedTabChange(SelectedTabChangeEvent event) {
				AbstractTab tab = (AbstractTab) event.getTabSheet()
						.getSelectedTab();
				if (prevTab != null) {
					prevTab.hideTab();
				}
				history.setFragment(tab.getTabUri(), false);
				prevTab = tab;
				tab.updateTab();
			}
		});
		
		history.addListener(new FragmentChangedListener() {
			private static final long serialVersionUID = 5435345345531L;

			public void fragmentChanged(FragmentChangedEvent source) {
				String fragment = source.getUriFragmentUtility().getFragment();
				if (fragment != null) {
					String[] fragmentParts = fragment.split("/");
					AbstractTab tab = (AbstractTab) tabs.get(fragmentParts[0]);
					if (tab != null) {
						tabsheet.setSelectedTab(tab);
						if (fragmentParts.length > 1) {
							tab.updateTab(fragmentParts[1]);
						}
						tab.updateTab();
					}
				}
			}
		});

	}

	/**
	 * Adds the tab.
	 *
	 * @param tab the tab
	 * @param uri the uri
	 * @param name the name
	 */
	protected void addTab(AbstractTab tab, String uri, String name) {
		tab.setHistoryUtility(history);
		tab.setTabUri(uri);
		tabsheet.addTab(tab, name);
		tabs.put(uri, tab);
	}

	/* (non-Javadoc)
	 * @see de.vlad.supportcenter.event.listener.RequestTabChangeEventListener#changeTab(de.vlad.supportcenter.event.RequestTabChangeEvent)
	 */
	@Override
	public void changeTab(RequestTabChangeEvent event) {
		AbstractTab tab = tabs.get(event.getTabname());
		if (tab != null) {
			tabsheet.setSelectedTab(tab);
		}
	}
	
	/**
	 * Destroy layout.
	 */
	public void destroyLayout() {
		for(AbstractTab tab : tabs.values()) {
			tab.hideTab();
		}
	}
	
	/**
	 * Gets the current uri.
	 *
	 * @return the current uri
	 */
	public String getCurrentURI() {
		String finalURL = AppRegistry.getMainWindow().getURL().toString();
		finalURL = finalURL.replaceFirst("\\/([0-9])+\\/", "/" + new Date().getTime() + "/");
		String fragment = history.getFragment();
		if(fragment != null && !fragment.trim().isEmpty()) {
			finalURL += "#" + fragment;
		}
		return finalURL;
		
	}
	
	/**
	 * Gets the title for window.
	 *
	 * @return the title for window
	 */
	public String getTitleForWindow() {
		return titleForWindow;
	}
	
	/* (non-Javadoc)
	 * @see com.vaadin.ui.AbstractComponentContainer#attach()
	 */
	@Override
	public void attach() {
		AppRegistry.getEventBus().addListener(this);
		super.attach();
	}
	
	/* (non-Javadoc)
	 * @see com.vaadin.ui.AbstractComponentContainer#detach()
	 */
	@Override
	public void detach() {
		AppRegistry.getEventBus().removeListener(this);
		super.detach();
	}

}
