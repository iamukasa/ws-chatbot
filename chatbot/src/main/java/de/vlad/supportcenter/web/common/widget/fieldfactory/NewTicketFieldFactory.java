package de.vlad.supportcenter.web.common.widget.fieldfactory;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

import com.vaadin.data.Item;
import com.vaadin.data.validator.StringLengthValidator;
import com.vaadin.ui.Component;
import com.vaadin.ui.Field;
import com.vaadin.ui.NativeSelect;
import com.vaadin.ui.RichTextArea;

import de.vlad.supportcenter.dto.FieldNames;
import de.vlad.supportcenter.model.SupportCategory;
import de.vlad.supportcenter.model.manager.SupportCategoryManager;


/**
 * A factory for creating NewTicketField objects.
 */
@Configurable
public class NewTicketFieldFactory extends AccountRegistrationFieldFactory {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 6253770818698927001L;
	
	/** The is category intern. */
	protected boolean isCategoryIntern;
	
	/** The categories. */
	protected List<SupportCategory> categories;
	
	/** The password field. */
	protected Field passwordField;
	
	/** The support category manager. */
	@Autowired
	protected transient SupportCategoryManager supportCategoryManager;
	
	/**
	 * Instantiates a new new ticket field factory.
	 *
	 * @param isCategoryIntern the is category intern
	 */
	public NewTicketFieldFactory(boolean isCategoryIntern) {
		super();
		this.isCategoryIntern = isCategoryIntern;
		setPasswordMinLength(4);
	}
	
	/**
	 * Inits the.
	 */
	@SuppressWarnings("unused")
	@PostConstruct
	private void init() {
		categories = supportCategoryManager
				.findCategoriesByPortal(isCategoryIntern);
	}

	/* (non-Javadoc)
	 * @see de.vlad.supportcenter.web.common.widget.fieldfactory.AccountRegistrationFieldFactory#createField(com.vaadin.data.Item, java.lang.Object, com.vaadin.ui.Component)
	 */
	@Override
	public Field createField(Item item, Object propertyId,
			Component uiContext) {
		Field field = null;

		if (FieldNames.DISPLAY_NAME.equals(propertyId)) {
			field = super.createField(item, propertyId, uiContext);
			field.setCaption("Ihr Name");
			field.setRequiredError("Teilen Sie uns Ihren Namen mit, damit wir Sie ansprechen können");
			field.setWidth("200px");
			field.addValidator(new StringLengthValidator(
					"Ihr Name ist zu lang. Es werden maximal 200 Zeichen akzeptiert", 1, 200,
					false));
			initialValues.put(propertyId + "", "");
		} else if (FieldNames.SUPPORT_CATEGORY.equals(propertyId)) {
			initialValues.put(propertyId + "", getDefaultSupportCategory());
			field = createCategoryField(isCategoryIntern);
			field.setWidth("200px");
		} else if (FieldNames.COMMENT.equals(propertyId)) {
			field = new RichTextArea("Kommentar");
			field.setRequiredError("Teilen Sie uns Ihr Anliegen mit");
			field.setWidth("100%");
			field.setHeight("350px");
			field.addValidator(new StringLengthValidator(
					"Teilen Sie uns Ihr Anliegen mit", 1, 50000,
					false));
			initialValues.put(propertyId + "", "");
		} else if (FieldNames.SUBJECT.equals(propertyId)) {
			field = super.createField(item, propertyId, uiContext);
			field.setCaption("Betreff");
			field.setRequiredError("Geben Sie Ihrem Anliegen einen aussagekräftigen Titel");
			field.setWidth("100%");
			field.addValidator(new StringLengthValidator(
					"Ihr Betreff ist zu lang. Es werden maximal 200 Zeichen akzeptiert", 1,
					200, false));
			initialValues.put(propertyId + "", "");
		} else {
			field = super.createField(item, propertyId, uiContext);
		}
		if (field != null) {
			field.setRequired(true);
		}
		return field;

	}

	/**
	 * Creates a new NewTicketField object.
	 *
	 * @param isCategoryIntern the is category intern
	 * @return the field
	 */
	protected Field createCategoryField(boolean isCategoryIntern) {
		NativeSelect select = new NativeSelect("Support Kategorie");
		select.setRequired(true);
		select.setImmediate(true);

		for (SupportCategory category : categories) {
			select.addItem(category);
			select.setItemCaption(category, category.getName());
		}
		select.setNullSelectionAllowed(false);
		select.setNewItemsAllowed(false);
		select.setValue(getDefaultSupportCategory());

		return select;
	}
	
	/**
	 * Gets the default support category.
	 *
	 * @return the default support category
	 */
	public SupportCategory getDefaultSupportCategory() {
		return categories.get(0);
	}
	
}