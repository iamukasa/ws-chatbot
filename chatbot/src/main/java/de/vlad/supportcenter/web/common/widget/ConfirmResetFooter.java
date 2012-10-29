package de.vlad.supportcenter.web.common.widget;

import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;


/**
 * The Class ConfirmResetFooter.
 */
public class ConfirmResetFooter extends HorizontalLayout {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1148523986291546437L;

	/** The reset. */
	private Button reset;
	
	/** The confirm. */
	private Button confirm;
	
	/**
	 * The Interface ClickEventHandler.
	 */
	public interface ClickEventHandler {
		
		/**
		 * Reset.
		 */
		void reset();
		
		/**
		 * Confirm.
		 */
		void confirm();
	}
	
	/**
	 * Instantiates a new confirm reset footer.
	 *
	 * @param eventHandler the event handler
	 */
	public ConfirmResetFooter(ConfirmResetFooter.ClickEventHandler eventHandler) {
		
		confirm = new Button("Bestätigen", eventHandler, "confirm");
		reset = new Button("Zurücksetzen", eventHandler, "reset");
		
		setWidth(100.0f, UNITS_PERCENTAGE);
		setSpacing(true);
		addComponent(reset);
		addComponent(confirm);
		setComponentAlignment(reset, Alignment.MIDDLE_RIGHT);
		setComponentAlignment(confirm, Alignment.MIDDLE_LEFT);
	}
	
	/**
	 * Gets the confirm button.
	 *
	 * @return the confirm button
	 */
	public Button getConfirmButton() {
		return confirm;
	}
	
	/**
	 * Gets the reset button.
	 *
	 * @return the reset button
	 */
	public Button getResetButton() {
		return reset;
	}

}
