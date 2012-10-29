package de.vlad.supportcenter.web.common.widget.fieldfactory;

import java.util.List;

import com.vaadin.data.Item;
import com.vaadin.data.Validator;
import com.vaadin.data.validator.EmailValidator;
import com.vaadin.data.validator.StringLengthValidator;
import com.vaadin.ui.Component;
import com.vaadin.ui.Field;
import com.vaadin.ui.NativeSelect;
import com.vaadin.ui.PasswordField;

import de.vlad.supportcenter.dto.FieldNames;
import de.vlad.supportcenter.model.Locale;


/**
 * A factory for creating AccountRegistrationField objects.
 */
public class AccountRegistrationFieldFactory extends AbstractFieldFactory {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -3839165968525497629L;
	
	/** The password field. */
	protected Field passwordField;
	
	/** The default locale. */
	protected Locale defaultLocale;
	
	/** The password length validator. */
	protected StringLengthValidator passwordLengthValidator = new StringLengthValidator(
			"Das Kennwort muss mindestens aus 8 und maximal aus 60 Zeichen bestehen",
			8, 60, false);
	
	/**
	 * Instantiates a new account registration field factory.
	 */
	public AccountRegistrationFieldFactory() {
		super();
		initialValues.put(FieldNames.PASSWORD, "");
		initialValues.put(FieldNames.CONFIRM_PASSWORD, "");
		initialValues.put(FieldNames.EMAIL, "");
	}

	/* (non-Javadoc)
	 * @see com.vaadin.ui.DefaultFieldFactory#createField(com.vaadin.data.Item, java.lang.Object, com.vaadin.ui.Component)
	 */
	@Override
	public Field createField(Item item, Object propertyId,
			Component uiContext) {
		Field field = null;

		if (FieldNames.EMAIL.equals(propertyId)) {
			field = super.createField(item, propertyId, uiContext);
			field.setCaption("E-Mail");
			field.setRequiredError("Teilen Sie uns Ihre E-Mail Adresse mit, um sich später authentifizieren zu können");
			field.setWidth("200px");
			field.addValidator(new EmailValidator("Die E-Mail Adresse ist ungültig"));
		} else if (FieldNames.PASSWORD.equals(propertyId)) {
			field = new PasswordField();
			field.setWidth("200px");
			field.setCaption("Kennwort");
			field.setRequiredError("Geben Sie ein Kennwort ein, um sich später authentifizieren zu können");
			field.addValidator(passwordLengthValidator);
			passwordField = field;
		} else if (FieldNames.CONFIRM_PASSWORD.equals(propertyId)) {
			field = new PasswordField();
			field.setWidth("200px");
			field.setCaption("Kennwort wiederholen");
			field.setRequiredError("Geben Sie bitte das Kennwort zur Sicherheit nochmal ein");
			field.addValidator(new Validator() {

				private static final long serialVersionUID = 15456765435467L;

				@Override
				public void validate(Object value)
						throws InvalidValueException {
					if (!isValid(value)) {
						throw new InvalidValueException(
								"Ihr Kennwort stimmt mit Ihrer wiederholten Eingabe nicht überein. Haben Sie sich vertippt?");
					}

				}

				@Override
				public boolean isValid(Object value) {
					return value != null && passwordField != null
							&& value.equals(passwordField.getValue());
				}

			});
		} else if (FieldNames.LANGUAGE.equals(propertyId)) {
			field = createLocaleSelect();
			field.setWidth("200px");
		} else {
			return super.createField(item, propertyId, uiContext);
		}
		if (field != null) {
			field.setRequired(true);
		}
		return field;

	}
	
	/**
	 * Creates a new AccountRegistrationField object.
	 *
	 * @return the field
	 */
	protected Field createLocaleSelect() {
		NativeSelect select = new NativeSelect("Sprache");
		select.setRequired(true);
		select.setImmediate(true);

		List<Locale> locales = Locale.findAllLocales();
		defaultLocale = locales.get(0);
		for (Locale locale : locales) {
			select.addItem(locale);
			select.setItemCaption(locale, locale.getLocaleName());
		}
		select.setNullSelectionAllowed(false);
		select.setNewItemsAllowed(false);
		select.setValue(defaultLocale);

		return select;
	}
	
	/**
	 * Sets the password min length.
	 *
	 * @param minLength the new password min length
	 */
	public void setPasswordMinLength(int minLength) {
		passwordLengthValidator.setMinLength(minLength);
		passwordLengthValidator.setErrorMessage("Das Kennwort muss mindestens aus "+minLength+" und maximal aus 60 Zeichen bestehen");
	}
	
	/**
	 * Gets the default locale.
	 *
	 * @return the default locale
	 */
	public Locale getDefaultLocale() {
		return defaultLocale;
	}
	
}
