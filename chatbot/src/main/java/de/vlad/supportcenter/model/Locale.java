package de.vlad.supportcenter.model;

import javax.validation.constraints.Size;

import org.springframework.roo.addon.entity.RooEntity;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.serializable.RooSerializable;
import org.springframework.roo.addon.tostring.RooToString;


/**
 * The Class Locale.
 */
@RooJavaBean
@RooToString
@RooEntity(versionField="")
@RooSerializable
public class Locale {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 8153089518991824801L;
	
	/** The locale key. */
	@Size(max = 6)
    private String localeKey;
	
	/** The locale name. */
	private String localeName;

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((localeKey == null) ? 0 : localeKey.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Locale other = (Locale) obj;
		if (localeKey == null) {
			if (other.localeKey != null)
				return false;
		} else if (!localeKey.equals(other.localeKey))
			return false;
		return true;
	}
}
