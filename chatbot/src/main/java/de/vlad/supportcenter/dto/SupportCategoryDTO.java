package de.vlad.supportcenter.dto;

import java.util.HashSet;
import java.util.Set;

import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.serializable.RooSerializable;

import de.vlad.supportcenter.model.SupportAgent;


/**
 * The Class SupportCategoryDTO.
 */
@RooJavaBean
@RooSerializable
public class SupportCategoryDTO {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -7889707715618620L;

	/** The id. */
	private Long id;
	
	/** The name. */
	private String name = "";
	
	/** The active. */
	private boolean active = true;
	
	/** The intern. */
	private boolean intern = false;
	
	/** The support agents. */
	private Set<SupportAgent> supportAgents = new HashSet<SupportAgent>();

}
