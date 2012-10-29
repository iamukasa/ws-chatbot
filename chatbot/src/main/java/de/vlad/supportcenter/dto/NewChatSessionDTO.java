package de.vlad.supportcenter.dto;

import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.serializable.RooSerializable;

import de.vlad.supportcenter.model.SupportCategory;


/**
 * The Class NewChatSessionDTO.
 */
@RooSerializable
@RooJavaBean
public class NewChatSessionDTO {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 530392953793741379L;
	
	/** The display name. */
	private String displayName = "";
	
	/** The email. */
	private String email = "";
	
	/** The support category. */
	private SupportCategory supportCategory;
	
	/** The chat comment. */
	private String chatComment = "";
	
	/** The referenced ticket. */
	private Long referencedTicket;
}
