package de.vlad.supportcenter;

import de.vlad.supportcenter.web.common.Bootstrap;
import de.vlad.supportcenter.web.enquirer.EnquirerBootstrap;


/**
 * The Class EnquirerApplication.
 */
public class EnquirerApplication extends AbstractEmbeddedApplication {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -3415696471007594483L;

	/* (non-Javadoc)
	 * @see de.vlad.supportcenter.AbstractEmbeddedApplication#initEmbedded()
	 */
	@Override
	public void initEmbedded() {
		Bootstrap bootstrap = new EnquirerBootstrap();
		setMainLayout(bootstrap.getMainLayoutClass());
	}

}
