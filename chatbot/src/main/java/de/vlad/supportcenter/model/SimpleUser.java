package de.vlad.supportcenter.model;

import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.roo.addon.entity.RooEntity;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.serializable.RooSerializable;
import org.springframework.roo.addon.tostring.RooToString;


/**
 * The Class SimpleUser.
 */
@RooJavaBean
@RooToString
@RooEntity(versionField="")
@RooSerializable
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
public class SimpleUser {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -1500933414735754346L;


	/** The display name. */
	@NotNull
    @Size(min = 2, max = 200)
    private String displayName;
	
	
    /** The email. */
    @Size(max = 200)
    private String email;
	
	/** The portal. */
	@NotNull
    @ManyToOne(fetch=FetchType.EAGER)
    private Portal portal;

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((getId() == null) ? 0 : getId().hashCode());
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
		SimpleUser other = (SimpleUser) obj;
		if (getId() == null) {
			if (other.getId() != null)
				return false;
		} else if (!getId().equals(other.getId()))
			return false;
		return true;
	}

}
