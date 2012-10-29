package de.vlad.supportcenter.web.common.widget;

import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;


/**
 * The Class LabeledData.
 */
public class LabeledData extends HorizontalLayout {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 657567567651L;

	/** The caption. */
	protected Label caption = new Label(":");
	
	/** The data. */
	protected Label data = new Label();
	
	/**
	 * Instantiates a new labeled data.
	 */
	public LabeledData() {
		
		caption.setStyleName("labeled-data-caption");
		data.setStyleName("labeled-data-data");
		setSpacing(true);
		
		
		addComponent(caption);
		addComponent(data);
		
	}
	
	/**
	 * Instantiates a new labeled data.
	 *
	 * @param captionContent the caption content
	 */
	public LabeledData(String captionContent) {
		
		this();
		setCaptionContent(captionContent);
		
	}
	
	/**
	 * Instantiates a new labeled data.
	 *
	 * @param captionContent the caption content
	 * @param dataContent the data content
	 */
	public LabeledData(String captionContent, String dataContent) {
		this(captionContent);
		setDataContent(dataContent);
	}
	
	/**
	 * Sets the caption content.
	 *
	 * @param captionContent the new caption content
	 */
	public void setCaptionContent(String captionContent) {
		caption.setValue(captionContent + ":");
	}
	
	/**
	 * Gets the caption content.
	 *
	 * @return the caption content
	 */
	public String getCaptionContent() {
		return (String)caption.getValue();
	}
	
	/**
	 * Sets the data content.
	 *
	 * @param dataContent the new data content
	 */
	public void setDataContent(String dataContent) {
		data.setValue(dataContent);
	}
	
	/**
	 * Gets the data content.
	 *
	 * @return the data content
	 */
	public String getDataContent() {
		return (String)data.getValue();
	}
	
	/**
	 * Sets the caption width.
	 *
	 * @param width the new caption width
	 */
	public void setCaptionWidth(String width) {
		caption.setWidth(width);
		setExpandRatio(data, 1.0f);
	}
	

}
