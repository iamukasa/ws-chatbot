package de.vlad.supportcenter.web.common.widget.form;

import com.vaadin.ui.Form;
import com.vaadin.ui.Window.Notification;

import de.vlad.supportcenter.web.common.AppRegistry;
import de.vlad.supportcenter.web.common.widget.ConfirmResetFooter;
import de.vlad.supportcenter.web.common.widget.fieldfactory.AbstractFieldFactory;


/**
 * The Class AbstractForm.
 */
public abstract class AbstractForm extends Form implements
		ConfirmResetFooter.ClickEventHandler {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 5902777396227250384L;

	/* (non-Javadoc)
	 * @see de.vlad.supportcenter.web.common.widget.ConfirmResetFooter.ClickEventHandler#reset()
	 */
	@Override
	public void reset() {
		resetForm(true);
	}

	/**
	 * Reset form.
	 *
	 * @param showNotification the show notification
	 */
	public void resetForm(boolean showNotification) {
		this.setComponentError(null);
		this.setValidationVisible(false);
		for (Object propertyId : this.getVisibleItemProperties()) {
			if (getAbstractFieldFactory().getInitialValues().containsKey(
					propertyId)) {
				Object value = getAbstractFieldFactory().getInitialValues()
						.get(propertyId);
				this.getField(propertyId).setValue(value);
			}
		}
		if (showNotification) {
			AppRegistry.getMainWindow().showNotification(
					"Die Eingabedaten wurden zurückgesetzt");
		}
	}

	/**
	 * Gets the abstract field factory.
	 *
	 * @return the abstract field factory
	 */
	public abstract AbstractFieldFactory getAbstractFieldFactory();

	/**
	 * Display something went wrong error.
	 */
	public void displaySomethingWentWrongError() {
		AppRegistry.getMainWindow().showNotification("Es ist etwas schief gegangen",
				"Versuchen Sie es später erneut",
				Notification.TYPE_ERROR_MESSAGE);
	}

}
