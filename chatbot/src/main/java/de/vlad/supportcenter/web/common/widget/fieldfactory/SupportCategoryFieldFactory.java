package de.vlad.supportcenter.web.common.widget.fieldfactory;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

import com.vaadin.data.Container;
import com.vaadin.data.Item;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.validator.StringLengthValidator;
import com.vaadin.ui.Component;
import com.vaadin.ui.Field;
import com.vaadin.ui.TwinColSelect;

import de.vlad.supportcenter.dto.FieldNames;
import de.vlad.supportcenter.dto.SupportCategoryDTO;
import de.vlad.supportcenter.model.SupportAgent;
import de.vlad.supportcenter.model.manager.SupportUserManager;


/**
 * A factory for creating SupportCategoryField objects.
 */
@Configurable
public class SupportCategoryFieldFactory  extends AbstractFieldFactory {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 625876126756927001L;
	
	/** The support user manager. */
	@Autowired
	private SupportUserManager supportUserManager;
	
	/** The category dto. */
	private SupportCategoryDTO categoryDTO;
	
	/**
	 * Instantiates a new support category field factory.
	 */
	public SupportCategoryFieldFactory() {
		this(null);
	}
	
	/**
	 * Instantiates a new support category field factory.
	 *
	 * @param categoryDTO the category dto
	 */
	public SupportCategoryFieldFactory(SupportCategoryDTO categoryDTO) {
		super();
		
		if(categoryDTO != null) {
			initialValues.put(FieldNames.CATEGORY_ACTIVE, categoryDTO.isActive());
			initialValues.put(FieldNames.CATEGORY_INTERN, categoryDTO.isIntern());
			initialValues.put(FieldNames.CATEGORY_NAME, categoryDTO.getName());
			initialValues.put(FieldNames.SUPPORT_AGENTS, categoryDTO.getSupportAgents());
			this.categoryDTO = categoryDTO;
		}
	}
	

	/* (non-Javadoc)
	 * @see com.vaadin.ui.DefaultFieldFactory#createField(com.vaadin.data.Item, java.lang.Object, com.vaadin.ui.Component)
	 */
	@Override
	public Field createField(Item item, Object propertyId,
			Component uiContext) {
		Field field = super.createField(item, propertyId, uiContext);
		field.setRequired(true);

		if (FieldNames.CATEGORY_NAME.equals(propertyId)) {
			field.setWidth("100%");
			field.setRequiredError("Sie müssen eine Bezeichnung für die Kategorie vergeben");
			field.addValidator(new StringLengthValidator("Die Bezeichnung für die Kategorie ist zu lang, es sind maximal 50 Zeichen erlaubt", 1, 50, false));
		} else if (FieldNames.SUPPORT_AGENTS.equals(propertyId)) {
			field = createAgentsField();
			field.setWidth("100%");
			field.setHeight("200px");
		}
		return field;

	}
	
	/**
	 * Creates a new SupportCategoryField object.
	 *
	 * @return the field
	 */
	private Field createAgentsField() {
		TwinColSelect agentsField = new TwinColSelect();
        Set<SupportAgent> agents = supportUserManager.getAllEmployees();
        agentsField.setMultiSelect(true);
        agentsField.setValue(categoryDTO.getSupportAgents());
        agentsField.setItemCaptionMode(TwinColSelect.ITEM_CAPTION_MODE_PROPERTY);
        agentsField.setItemCaptionPropertyId("displayName");
        agentsField.setLeftColumnCaption("Verfügbare Mitarbeiter");
        agentsField.setRightColumnCaption("Zugeordnete Mitarbeter");
        agentsField.setContainerDataSource(new BeanItemContainer<SupportAgent>(SupportAgent.class, agents));

		return agentsField;
	}


	/* (non-Javadoc)
	 * @see com.vaadin.ui.DefaultFieldFactory#createField(com.vaadin.data.Container, java.lang.Object, java.lang.Object, com.vaadin.ui.Component)
	 */
	@Override
	public Field createField(Container container, Object itemId,
			Object propertyId, Component uiContext) {
		Field field = super.createField(container, itemId, propertyId, uiContext);
		field.setCaption("");
		field.setRequired(true);

		if (FieldNames.CATEGORY_NAME.equals(propertyId)) {
			field.setWidth("100%");
			field.setRequiredError("Sie müssen eine Bezeichnung für die Kategorie vergeben");
			field.addValidator(new StringLengthValidator("Die Bezeichnung für die Kategorie ist zu lang, es sind maximal 50 Zeichen erlaubt", 1, 50, false));
		}
		return field;
	}
}