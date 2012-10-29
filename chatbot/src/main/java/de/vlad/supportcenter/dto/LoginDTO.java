package de.vlad.supportcenter.dto;

import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.serializable.RooSerializable;


/**
 * The Class LoginDTO.
 */
@RooJavaBean
@RooSerializable
public class LoginDTO {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 8902986597519128552L;
	
	/** The login email. */
	private String loginEmail = "";
	
	/** The login password. */
	private String loginPassword = "";
	
}