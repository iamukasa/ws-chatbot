package de.vlad.supportcenter.web.common.widget;

import com.vaadin.event.FieldEvents.BlurEvent;
import com.vaadin.event.FieldEvents.BlurListener;
import com.vaadin.event.FieldEvents.FocusEvent;
import com.vaadin.event.FieldEvents.FocusListener;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;


/**
 * The Class AddTextualItem.
 */
public class AddTextualItem extends HorizontalLayout {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -3677369304341875661L;
	
	/** The text field. */
	private TextField textField = new TextField();
	
	/** The add button. */
	private Button addButton = new Button();
	
	/**
	 * Instantiates a new adds the textual item.
	 *
	 * @param inputPrompt the input prompt
	 * @param listener the listener
	 */
	public AddTextualItem(String inputPrompt, ClickListener listener) {
		super();
		setWidth("100%");
		setSpacing(true);
		setMargin(false, false, true, false);
		textField.setWidth("100%");
		textField.setInputPrompt(inputPrompt);
		textField.addListener(new FocusListener() {
			
			private static final long serialVersionUID = -369304341875661L;
			
			@Override
			public void focus(FocusEvent event) {
				enableEnterShortcut();
			}
		});
		textField.addListener(new BlurListener() {
			
			private static final long serialVersionUID = -369306454341875661L;
			
			@Override
			public void blur(BlurEvent event) {
				disableEnterShortcut();
			}
		});
		addComponent(textField);
		addButton.setCaption("Hinzufügen");
		addButton.addListener(listener);
		addComponent(addButton);
		setExpandRatio(textField, 1.0f);
	}
	
	/**
	 * Gets the value.
	 *
	 * @return the value
	 */
	public String getValue() {
		return textField.getValue() != null ? textField.toString().trim() : "";
	}
	
	/**
	 * Reset input.
	 */
	public void resetInput() {
		textField.setValue("");
	}
	
	/**
	 * Enable enter shortcut.
	 */
	public void enableEnterShortcut() {
		addButton.setClickShortcut(KeyCode.ENTER);
	}
	
	/**
	 * Disable enter shortcut.
	 */
	public void disableEnterShortcut() {
		addButton.removeClickShortcut();
	}
	

}
