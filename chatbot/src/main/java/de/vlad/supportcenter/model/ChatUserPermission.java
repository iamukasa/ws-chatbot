package de.vlad.supportcenter.model;

import java.util.Date;

import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.roo.addon.entity.RooEntity;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.serializable.RooSerializable;
import org.springframework.roo.addon.tostring.RooToString;

import de.vlad.supportcenter.model.enums.SupportRole;


/**
 * The Class ChatUserPermission.
 */
@RooJavaBean
@RooToString
@RooEntity(versionField="")
@RooSerializable
public class ChatUserPermission {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1418778568025789113L;

	/** The session. */
	@NotNull
    @ManyToOne
    private ChatSession session;
    
    /** The chat user. */
    @ManyToOne
    private SimpleUser chatUser;
    
    /** The chat role. */
    @NotNull
    @Enumerated
    private SupportRole chatRole;
    
    /** The time assigned. */
    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "M-")
    private Date timeAssigned;
    
    /** The active. */
    private boolean active;
    
    /** The typing. */
    private boolean typing = false;
    
}