package de.vlad.supportcenter.dto;

import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.serializable.RooSerializable;

import de.vlad.supportcenter.model.SupportCategory;


/**
 * The Class NewTicketDTO.
 */
@RooJavaBean
@RooSerializable
public class NewTicketDTO {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -7889333707715618620L;

	/** The display name. */
	private String displayName = "";
	
	/** The email. */
	private String email = "";
	
	/** The password. */
	private String password = "";
	
	/** The confirm password. */
	private String confirmPassword = "";
	
	/** The support category. */
	private SupportCategory supportCategory;
	
	/** The subject. */
	private String subject = "";
	
	/** The comment. */
	private String comment = "";

}
