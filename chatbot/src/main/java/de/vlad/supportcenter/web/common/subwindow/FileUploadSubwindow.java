package de.vlad.supportcenter.web.common.subwindow;

import java.io.File;
import java.util.UUID;

import org.vaadin.easyuploads.MultiFileUpload;

import com.vaadin.event.dd.DragAndDropEvent;
import com.vaadin.ui.DragAndDropWrapper;
import com.vaadin.ui.DragAndDropWrapper.WrapperTransferable;
import com.vaadin.ui.Form;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Html5File;
import com.vaadin.ui.Window;

import de.vlad.supportcenter.web.common.AppRegistry;


/**
 * The Class FileUploadSubwindow.
 */
public abstract class FileUploadSubwindow extends Window {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 3823635735351057290L;
	
	/** The upload file. */
	private MultiFileUpload uploadFile;
	
	/** The uploaded file link. */
	private String uploadedFileLink = "";

	/**
	 * Instantiates a new file upload subwindow.
	 */
	public FileUploadSubwindow() {
		super("Datei Upload");
		FormLayout content = new FormLayout();
		this.setContent(content);
		this.setWidth("430px");
		uploadFile = new MultiFileUpload() {

			private static final long serialVersionUID = 156832042342456L;

			@Override
			protected void handleFile(File file, String fileName,
					String mimeType, long length) {
				fileName = fileName.replaceAll(" ", "-");
				fileName = fileName.replaceAll("ü", "ue");
				fileName = fileName.replaceAll("Ü", "Ue");
				fileName = fileName.replaceAll("ö", "oe");
				fileName = fileName.replaceAll("ä", "ae");
				fileName = fileName.replaceAll("Ä", "Ae");
				String uuid = UUID.randomUUID().toString();
				File upload = new File(AppRegistry.getUploadDir()
						+ File.separator + uuid);
				try {
					upload.mkdirs();
					upload = new File(upload.getAbsolutePath() + File.separator
							+ fileName);
					uploadedFileLink = uuid + "/" + fileName;
				} catch (Exception e) {
					e.printStackTrace();
				}
				if (uploadedFileLink != null
						&& !uploadedFileLink.trim().isEmpty()) {
					file.renameTo(upload);
					uploadFinished(uploadedFileLink);
					uploadedFileLink = "";
					FileUploadSubwindow.this.getParent().removeWindow(
							FileUploadSubwindow.this);

				} else {
					FileUploadSubwindow.this.showNotification("",
							"Upload ist fehlgeschlagen");
				}

			}
			
			@Override
			public void drop(DragAndDropEvent event) {
				DragAndDropWrapper.WrapperTransferable transferable = (WrapperTransferable) event
						.getTransferable();
				Html5File[] files = transferable.getFiles();
				if (files != null) {
					super.drop(event);
				}
			}

			@Override
			protected String getAreaText() {
				return "<small>DATEIEN HIERHER ZIEHEN<br />(DRAG AND DROP)</small>";
			}
		};
		uploadFile.setWidth("400px");
		uploadFile.setUploadButtonCaption("Oder die Datei suchen ...");
		Form caption = new Form();
		caption.setCaption("Laden Sie Ihre Datei hoch");
		addComponent(caption);
		addComponent(uploadFile);
		center();
	}

	/**
	 * Upload finished.
	 *
	 * @param path the path
	 */
	public abstract void uploadFinished(String path);

}
