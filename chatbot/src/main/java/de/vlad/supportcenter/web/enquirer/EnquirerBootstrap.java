package de.vlad.supportcenter.web.enquirer;

import de.vlad.supportcenter.web.common.AppRegistry;
import de.vlad.supportcenter.web.common.Bootstrap;
import de.vlad.supportcenter.web.common.mainlayout.AbstractMainLayout;
import de.vlad.supportcenter.web.enquirer.window.UnauthenticatedEnquirerAreaMainLayout;

/**
 * The Class EnquirerBootstrap.
 */
public class EnquirerBootstrap extends Bootstrap {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 4116637729014078935L;

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.vlad.supportcenter.web.common.Bootstrap#init()
	 */
	@Override
	public void init() {

		AppRegistry.setLocale(AppRegistry.getPortal().getLocale());

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.vlad.supportcenter.web.common.Bootstrap#getMainLayoutClass()
	 */
	@Override
	public Class<? extends AbstractMainLayout> getMainLayoutClass() {
		return UnauthenticatedEnquirerAreaMainLayout.class;
	}

}
