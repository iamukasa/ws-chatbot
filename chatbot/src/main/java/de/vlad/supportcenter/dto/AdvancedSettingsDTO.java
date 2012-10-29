package de.vlad.supportcenter.dto;

import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.serializable.RooSerializable;

import de.vlad.supportcenter.model.Locale;
import de.vlad.supportcenter.model.enums.SystemStatus;
import de.vlad.supportcenter.model.enums.Theme;


/**
 * The Class AdvancedSettingsDTO.
 */
@RooJavaBean
@RooSerializable
public class AdvancedSettingsDTO {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 89029865971252L;
	
	/** The language. */
	private Locale language;
	
	/** The theme. */
	private Theme theme;
	
	/** The system status. */
	private SystemStatus systemStatus;
	
	/** The livechat active. */
	private boolean livechatActive;
	
	/** The host filter active. */
	private boolean hostFilterActive;
	
}