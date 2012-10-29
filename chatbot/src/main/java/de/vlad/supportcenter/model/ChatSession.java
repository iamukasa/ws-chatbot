package de.vlad.supportcenter.model;

import java.util.Date;
import java.util.List;

import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.roo.addon.entity.RooEntity;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.serializable.RooSerializable;
import org.springframework.roo.addon.tostring.RooToString;

import de.vlad.supportcenter.model.enums.ChatStatus;


/**
 * The Class ChatSession.
 */
@RooJavaBean
@RooToString
@RooEntity(versionField="")
@RooSerializable
public class ChatSession {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 2841155476787421187L;
	
	/** The enquirer ip. */
	@NotNull
	@Size(min = 1, max = 44)
	private String enquirerIP;
	
	/** The support category. */
	@NotNull
    private SupportCategory supportCategory;

    /** The status. */
    @NotNull
    @Enumerated
    private ChatStatus status;

    /** The time created. */
    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "M-")
    private Date timeCreated;
    
    /** The time updated. */
    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "M-")
    private Date timeUpdated;
    
    /** The permissions. */
    @OneToMany(fetch=FetchType.EAGER, mappedBy="session")
    private List<ChatUserPermission> permissions;

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((enquirerIP == null) ? 0 : enquirerIP.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		result = prime * result
				+ ((supportCategory == null) ? 0 : supportCategory.hashCode());
		result = prime * result
				+ ((timeCreated == null) ? 0 : timeCreated.hashCode());
		result = prime * result
				+ ((timeUpdated == null) ? 0 : timeUpdated.hashCode());
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
		ChatSession other = (ChatSession) obj;
		if (enquirerIP == null) {
			if (other.enquirerIP != null)
				return false;
		} else if (!enquirerIP.equals(other.enquirerIP))
			return false;
		if (status != other.status)
			return false;
		if (supportCategory == null) {
			if (other.supportCategory != null)
				return false;
		} else if (!supportCategory.equals(other.supportCategory))
			return false;
		if (timeCreated == null) {
			if (other.timeCreated != null)
				return false;
		} else if (!timeCreated.equals(other.timeCreated))
			return false;
		if (timeUpdated == null) {
			if (other.timeUpdated != null)
				return false;
		} else if (!timeUpdated.equals(other.timeUpdated))
			return false;
		return true;
	}
    
	
}
