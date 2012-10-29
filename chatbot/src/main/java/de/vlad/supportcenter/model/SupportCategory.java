package de.vlad.supportcenter.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.roo.addon.entity.RooEntity;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.serializable.RooSerializable;


/**
 * The Class SupportCategory.
 */
@RooJavaBean
@RooEntity(versionField="")
@RooSerializable
public class SupportCategory {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -6845740495492406395L;

	/** The name. */
	@NotNull
    @Size(min=1,max=30)
    private String name = "";
    
    /** The intern. */
    private boolean intern = false;
    
    /** The active. */
    private boolean active = true;
    
    /** The portal. */
    @NotNull
    @ManyToOne
    private Portal portal;
    
    /** The support agents. */
    @ManyToMany(mappedBy="adminCategories")
	private Set<SupportAgent> supportAgents = new HashSet<SupportAgent>();
    
    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
    	return name;
    }
    
    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
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
		SupportCategory other = (SupportCategory) obj;
		if (getId() == null) {
			if (other.getId() != null)
				return false;
		} else if (!getId().equals(other.getId()))
			return false;
		return true;
	}
    
}
