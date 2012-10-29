package de.vlad.supportcenter.dto;

import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.serializable.RooSerializable;

import de.vlad.supportcenter.model.Locale;


/**
 * The Class NewPortalDTO.
 */
@RooJavaBean
@RooSerializable
public class NewPortalDTO {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -7889707715618620L;

	/** The email. */
	private String email = "";
	
	/** The password. */
	private String password = "";
	
	/** The confirm password. */
	private String confirmPassword = "";
	
	/** The language. */
	private Locale language;

}
