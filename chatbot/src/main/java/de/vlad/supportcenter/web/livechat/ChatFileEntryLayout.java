package de.vlad.supportcenter.web.livechat;

import com.vaadin.terminal.Resource;
import com.vaadin.ui.CustomLayout;
import com.vaadin.ui.Link;

import de.vlad.supportcenter.model.ChatEntry;
import de.vlad.supportcenter.web.common.Utils;


/**
 * The Class ChatFileEntryLayout.
 */
public class ChatFileEntryLayout extends CustomLayout {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -3251221139761596323L;
	
	/**
	 * Instantiates a new chat file entry layout.
	 *
	 * @param entry the entry
	 */
	public ChatFileEntryLayout(ChatEntry entry) {
		super("../../supportcenter/layouts/ChatFileEntry");
		
		String path = entry.getComment();
		String filename = Utils.getFileName(path);
		final Resource link = Utils.getUploadURL(path);
		Link filenameLink = new Link(filename, link);
		filenameLink.setTargetName("supportcenter_user_file");
		addComponent(filenameLink, "chatFileLink");
		
	}

}
