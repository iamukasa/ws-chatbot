package de.vlad.supportcenter.model;

import java.util.Date;

import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.roo.addon.entity.RooEntity;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.serializable.RooSerializable;
import org.springframework.roo.addon.tostring.RooToString;

import de.vlad.supportcenter.model.enums.SystemStatus;
import de.vlad.supportcenter.model.enums.Theme;


/**
 * The Class Portal.
 */
@RooJavaBean
@RooEntity(versionField="")
@RooSerializable
@RooToString
public class Portal {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 5155349854690572496L;

	/** The email. */
	@NotNull
    @Size(min = 3, max = 200)
    private String email;

    /** The password. */
    @NotNull
    @Size(min = 6, max = 64)
    private String password;

    /** The locale. */
    @NotNull
    @ManyToOne
    private Locale locale;
    
    /** The theme. */
    @NotNull
    @Enumerated
    private Theme theme;
    
    private boolean chatMultipleAgentsAssistence = true;
    
    private boolean ticketMultipleAgentsAssistence = true;
    
    /** The chat enabled. */
    private boolean chatEnabled;
    
    /** The host filter enabled. */
    private boolean hostFilterEnabled;
    
	/** The system status. */
	@NotNull
    @Enumerated
    private SystemStatus systemStatus;
	
	/** The last change. */
	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	private Date lastChange;
    
    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (chatEnabled ? 1231 : 1237);
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + (hostFilterEnabled ? 1231 : 1237);
		result = prime * result + ((locale == null) ? 0 : locale.hashCode());
		result = prime * result
				+ ((password == null) ? 0 : password.hashCode());
		result = prime * result
				+ ((systemStatus == null) ? 0 : systemStatus.hashCode());
		result = prime * result + ((theme == null) ? 0 : theme.hashCode());
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
		Portal other = (Portal) obj;
		if (chatEnabled != other.chatEnabled)
			return false;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (hostFilterEnabled != other.hostFilterEnabled)
			return false;
		if (locale == null) {
			if (other.locale != null)
				return false;
		} else if (!locale.equals(other.locale))
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		if (systemStatus != other.systemStatus)
			return false;
		if (theme != other.theme)
			return false;
		return true;
	}

}
