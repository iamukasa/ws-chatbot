package de.vlad.supportcenter.model;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.roo.addon.entity.RooEntity;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.serializable.RooSerializable;
import org.springframework.roo.addon.tostring.RooToString;


/**
 * The Class SupportUser.
 */
@RooJavaBean
@RooToString
@RooEntity(versionField="")
@RooSerializable
public class SupportUser extends SimpleUser {


	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 3266735277768614847L;

    /** The password. */
    @NotNull
    @Size(min = 4, max = 64)
    private String password;
    
    /** The active. */
    private boolean active;
    
}
