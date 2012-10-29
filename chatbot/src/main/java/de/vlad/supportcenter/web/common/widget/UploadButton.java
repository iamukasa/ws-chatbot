package de.vlad.supportcenter.web.common.widget;

import com.vaadin.ui.Button;
import com.vaadin.ui.Window.CloseEvent;
import com.vaadin.ui.Window.CloseListener;
import com.vaadin.ui.themes.Reindeer;

import de.vlad.supportcenter.web.common.AppRegistry;
import de.vlad.supportcenter.web.common.subwindow.FileUploadSubwindow;


/**
 * The Class UploadButton.
 */
public class UploadButton extends Button {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -771917359042752179L;
	
	/** The listener. */
	private UploadFinishedListener listener;
	
	/** The subwindow. */
	private FileUploadSubwindow subwindow;
	
	/**
	 * The listener interface for receiving uploadFinished events.
	 * The class that is interested in processing a uploadFinished
	 * event implements this interface, and the object created
	 * with that class is registered with a component using the
	 * component's <code>addUploadFinishedListener<code> method. When
	 * the uploadFinished event occurs, that object's appropriate
	 * method is invoked.
	 *
	 * @see UploadFinishedEvent
	 */
	public interface UploadFinishedListener {
		
		/**
		 * Upload finished.
		 *
		 * @param path the path
		 */
		void uploadFinished(String path);
	}

	/**
	 * Instantiates a new upload button.
	 *
	 * @param listener the listener
	 */
	public UploadButton(UploadFinishedListener listener) {
		super("Datei hochladen");
		this.listener = listener;
		this.addListener(new ClickListener() {
			
			private static final long serialVersionUID = -7719173590752179L;
			
			@Override
			public void buttonClick(ClickEvent event) {
				if(subwindow == null) {
					UploadButton.this.initNewSubwindow();
					AppRegistry.getMainWindow().addWindow(subwindow);
				} else {
					subwindow.focus();
				}
			}
		});
		setStyleName(Reindeer.BUTTON_LINK);
	}
	
	/**
	 * Inits the new subwindow.
	 */
	private void initNewSubwindow() {
		subwindow = new FileUploadSubwindow() {
			private static final long serialVersionUID = 71917359042752179L;
			
			@Override
			public void uploadFinished(String path) {
				UploadButton.this.listener.uploadFinished(path);
			}
		};
		
		subwindow.addListener(new CloseListener() {
			
			private static final long serialVersionUID = 53454204532L;

			@Override
			public void windowClose(CloseEvent e) {
				AppRegistry.getMainWindow().removeWindow(UploadButton.this.subwindow);
				UploadButton.this.subwindow = null;
			}
		});
	}

}
