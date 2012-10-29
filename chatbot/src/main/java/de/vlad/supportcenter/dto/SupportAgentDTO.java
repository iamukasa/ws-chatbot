package de.vlad.supportcenter.dto;

import java.util.HashSet;
import java.util.Set;

import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.serializable.RooSerializable;

import de.vlad.supportcenter.model.SupportCategory;


/**
 * The Class SupportAgentDTO.
 */
@RooJavaBean
@RooSerializable
public class SupportAgentDTO {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -618620L;

	/** The id. */
	private Long id;
	
	/** The display name. */
	private String displayName = "";
	
	/** The email. */
	private String email = "";
	
	/** The password. */
	private String password = "";
	
	/** The confirm password. */
	private String confirmPassword = "";
	
	/** The active. */
	private boolean active = true;
	
	/** The admin categories. */
	private Set<SupportCategory> adminCategories = new HashSet<SupportCategory>();
	
	/** The chat permission. */
	private boolean chatPermission = true;

}
