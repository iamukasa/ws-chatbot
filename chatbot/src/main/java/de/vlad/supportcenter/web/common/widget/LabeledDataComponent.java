package de.vlad.supportcenter.web.common.widget;

import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;


/**
 * The Class LabeledDataComponent.
 */
public class LabeledDataComponent extends LabeledData {
		

		/** The Constant serialVersionUID. */
		private static final long serialVersionUID = -5863441717149174713L;
		
		/** The data layout. */
		private HorizontalLayout dataLayout = new HorizontalLayout();
		
		/**
		 * Instantiates a new labeled data component.
		 */
		public LabeledDataComponent() {
			super();
			removeComponent(data);
			
			addComponent(dataLayout);
			dataLayout.setSpacing(true);
			dataLayout.addComponent(data);
			
		}
		
		/**
		 * Instantiates a new labeled data component.
		 *
		 * @param captionContent the caption content
		 */
		public LabeledDataComponent(String captionContent) {
			
			this();
			setCaptionContent(captionContent);
			
		}
		
		/**
		 * Instantiates a new labeled data component.
		 *
		 * @param captionContent the caption content
		 * @param dataContent the data content
		 */
		public LabeledDataComponent(String captionContent, String dataContent) {
			this(captionContent);
			setDataContent(dataContent);
		}
		
		/**
		 * Adds the data component.
		 *
		 * @param component the component
		 */
		public void addDataComponent(Component component) {
			dataLayout.addComponent(component);
		}
		
		/* (non-Javadoc)
		 * @see de.vlad.supportcenter.web.common.widget.LabeledData#setCaptionWidth(java.lang.String)
		 */
		public void setCaptionWidth(String width) {
			caption.setWidth(width);
			setExpandRatio(dataLayout, 1.0f);
		}


}
