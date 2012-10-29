package de.vlad.supportcenter.dto;

import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.serializable.RooSerializable;


/**
 * The Class AuthConfigDTO.
 */
@RooJavaBean
@RooSerializable
public class AuthConfigDTO {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 890298659759128552L;
	
	/** The current password. */
	private String currentPassword = "";
	
	/** The email. */
	private String email = "";
	
	/** The password. */
	private String password = "";
	
	/** The confirm password. */
	private String confirmPassword = "";
	
}