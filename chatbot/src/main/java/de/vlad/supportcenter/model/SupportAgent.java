package de.vlad.supportcenter.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.FetchType;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotNull;

import org.springframework.roo.addon.entity.RooEntity;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;


/**
 * The Class SupportAgent.
 */
@RooJavaBean
@RooToString
@RooEntity(versionField="")
public class SupportAgent extends SupportUser{
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -2789762761132399143L;

	/** The admin categories. */
	@ManyToMany(fetch=FetchType.EAGER)
	@JoinTable(name="employee_category")
	private Set<SupportCategory> adminCategories = new HashSet<SupportCategory>();
	
	/** The chat enabled. */
	@NotNull
	private boolean chatEnabled = false;
	
	/** The chat permission. */
	@NotNull
	private boolean chatPermission = true;

}
