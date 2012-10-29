package de.vlad.supportcenter.web.common;

import com.vaadin.ui.UriFragmentUtility;
import com.vaadin.ui.VerticalLayout;


/**
 * The Class AbstractTab.
 */
public abstract class AbstractTab extends VerticalLayout {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 201410598269763907L;
	
	/** The history. */
	private UriFragmentUtility history;
	
	/** The tab uri. */
	private String tabUri;
	
	/**
	 * Instantiates a new abstract tab.
	 */
	public AbstractTab() {
		super();
		setMargin(true);
		setSizeFull();
	}
	
	/**
	 * Sets the history utility.
	 *
	 * @param history the new history utility
	 */
	public void setHistoryUtility(UriFragmentUtility history) {
		this.history = history;
	}
	
	/**
	 * Sets the tab uri.
	 *
	 * @param uri the new tab uri
	 */
	public void setTabUri(String uri) {
		tabUri = uri;
	}
	
	/**
	 * Sets the current sub uri.
	 *
	 * @param subUri the sub uri
	 * @param fireUriEvent the fire uri event
	 */
	public void setCurrentSubUri(String subUri, boolean fireUriEvent) {
		if(subUri != null && !subUri.isEmpty()) {
			history.setFragment(tabUri + "/" + subUri, fireUriEvent);
		} else {
			history.setFragment(tabUri, fireUriEvent);
		}
	}
	
	/**
	 * Sets the current sub uri.
	 *
	 * @param subUri the new current sub uri
	 */
	public void setCurrentSubUri(String subUri) {
		setCurrentSubUri(subUri, false);
	}
	
	/**
	 * Gets the tab uri.
	 *
	 * @return the tab uri
	 */
	public String getTabUri() {
		return tabUri;
	}
	
	/**
	 * Update tab.
	 */
	public void updateTab() {
		
	}
	
	/**
	 * Update tab.
	 *
	 * @param uriToken the uri token
	 */
	public void updateTab(String uriToken) {
		
	}
	
	/**
	 * Hide tab.
	 */
	public void hideTab() {
		
	}
	
}
