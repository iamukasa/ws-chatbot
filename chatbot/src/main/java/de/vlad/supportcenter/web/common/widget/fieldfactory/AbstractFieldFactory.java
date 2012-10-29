package de.vlad.supportcenter.web.common.widget.fieldfactory;

import java.util.LinkedHashMap;
import java.util.Map;

import com.vaadin.ui.DefaultFieldFactory;


/**
 * A factory for creating AbstractField objects.
 */
public abstract class AbstractFieldFactory extends DefaultFieldFactory {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 2157827077020329428L;
	
	/** The initial values. */
	protected Map<String, Object> initialValues = new LinkedHashMap<String, Object>();
	
	/**
	 * Gets the initial values.
	 *
	 * @return the initial values
	 */
	public Map<String, Object> getInitialValues() {
		return initialValues;
	}

}
