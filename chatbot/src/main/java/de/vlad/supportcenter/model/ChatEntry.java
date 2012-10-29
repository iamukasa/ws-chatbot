package de.vlad.supportcenter.model;

import java.util.Date;

import javax.persistence.Enumerated;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.roo.addon.entity.RooEntity;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.serializable.RooSerializable;
import org.springframework.roo.addon.tostring.RooToString;

import de.vlad.supportcenter.model.enums.EntryType;


/**
 * The Class ChatEntry.
 */
@RooJavaBean
@RooToString
@RooEntity(versionField="")
@RooSerializable
public class ChatEntry {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -4298348070497701458L;

	/** The user permission. */
	@NotNull
    @ManyToOne
    private ChatUserPermission userPermission;
    
    /** The type. */
    @NotNull
    @Enumerated
    private EntryType type;
    
    /** The comment. */
    @Lob
    private String comment;
    
    /** The time created. */
    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "M-")
    private Date timeCreated;

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((comment == null) ? 0 : comment.hashCode());
		result = prime * result
				+ ((timeCreated == null) ? 0 : timeCreated.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		result = prime * result
				+ ((userPermission == null) ? 0 : userPermission.hashCode());
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
		ChatEntry other = (ChatEntry) obj;
		if (comment == null) {
			if (other.comment != null)
				return false;
		} else if (!comment.equals(other.comment))
			return false;
		if (timeCreated == null) {
			if (other.timeCreated != null)
				return false;
		} else if (!timeCreated.equals(other.timeCreated))
			return false;
		if (type != other.type)
			return false;
		if (userPermission == null) {
			if (other.userPermission != null)
				return false;
		} else if (!userPermission.equals(other.userPermission))
			return false;
		return true;
	}

}
