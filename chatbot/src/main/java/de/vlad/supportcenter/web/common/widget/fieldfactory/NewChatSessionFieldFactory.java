package de.vlad.supportcenter.web.common.widget.fieldfactory;

import com.vaadin.data.Item;
import com.vaadin.data.validator.StringLengthValidator;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.Field;
import com.vaadin.ui.TextArea;

import de.vlad.supportcenter.dto.FieldNames;


/**
 * A factory for creating NewChatSessionField objects.
 */
public class NewChatSessionFieldFactory extends NewTicketFieldFactory {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -3226323448104610954L;
	
	/** The ticket select. */
	private ComboBox ticketSelect;

	/**
	 * Instantiates a new new chat session field factory.
	 *
	 * @param isCategoryIntern the is category intern
	 */
	public NewChatSessionFieldFactory(boolean isCategoryIntern) {
		super(isCategoryIntern);
	}
	
	/* (non-Javadoc)
	 * @see de.vlad.supportcenter.web.common.widget.fieldfactory.NewTicketFieldFactory#createField(com.vaadin.data.Item, java.lang.Object, com.vaadin.ui.Component)
	 */
	@Override
	public Field createField(Item item, Object propertyId,
			Component uiContext) {
		Field field = null;

		
		if (FieldNames.REFERENCED_TICKET.equals(propertyId)) {
			field = getTicketSelect();
			field.setWidth("100%");
			initialValues.put(propertyId + "", null);
		} else if (FieldNames.CHAT_COMMENT.equals(propertyId)) {
			field = new TextArea(item.getItemProperty(propertyId));
			((TextArea)field).setRows(3);
			field.setCaption("Kontaktgrund");
			field.setRequiredError("Beschreiben Sie Ihr Anliegen");
			field.addValidator(new StringLengthValidator(
					"Ihr Kontaktgrund ist zu detailliert beschrieben. Es sind maximal 50 000 Zeichen erlaubt",
					1, 50000, false));
			
			field.setWidth("100%");
			initialValues.put(propertyId + "", "");
		} else if (FieldNames.EMAIL.equals(propertyId)) {
			field = super.createField(item, propertyId, uiContext);
			field.setRequired(false);
		} else {
			field = super.createField(item, propertyId, uiContext);
		}
		return field;
	}

	/**
	 * Gets the ticket select.
	 *
	 * @return the ticket select
	 */
	public ComboBox getTicketSelect() {
		return ticketSelect;
	}

	/**
	 * Sets the ticket select.
	 *
	 * @param ticketSelect the new ticket select
	 */
	public void setTicketSelect(ComboBox ticketSelect) {
		this.ticketSelect = ticketSelect;
	}

}
