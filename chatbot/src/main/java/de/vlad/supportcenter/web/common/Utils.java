package de.vlad.supportcenter.web.common;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.vaadin.terminal.Resource;
import com.vaadin.terminal.ThemeResource;
import com.vaadin.ui.Label;

import de.vlad.supportcenter.model.Portal;



/**
 * The Class Utils.
 */
public class Utils {
	
	/**
	 * Gets the formatted.
	 *
	 * @param formatPattern the format pattern
	 * @param date the date
	 * @return the formatted
	 */
	private static String getFormatted(String formatPattern, Date date) {
		SimpleDateFormat format = new SimpleDateFormat(formatPattern);
		return format.format(date);
	}
	
	/**
	 * Gets the formatted date time.
	 *
	 * @param date the date
	 * @return the formatted date time
	 */
	public static String getFormattedDateTime(Date date) {
		return getFormatted("dd.MM.yyyy - HH:mm", date);
	}
	
	
	/**
	 * Gets the formatted date.
	 *
	 * @param date the date
	 * @return the formatted date
	 */
	public static String getFormattedDate(Date date) {
		return getFormatted("dd.MM.yyyy", date);
	}
	
	/**
	 * Gets the formatted time.
	 *
	 * @param date the date
	 * @return the formatted time
	 */
	public static String getFormattedTime(Date date) {
		return getFormatted("HH:mm:ss", date);
	}
	
	/**
	 * Gets the client ip.
	 *
	 * @return the client ip
	 */
	public static String getClientIp() {
		return AppRegistry.getClientIp();
	}
	
	/**
	 * Gets the file name.
	 *
	 * @param filepath the filepath
	 * @return the file name
	 */
	public static String getFileName(String filepath) {
		int index = filepath.lastIndexOf("/");
		return filepath.substring(index +1);
	}
	
	/**
	 * Gets the upload url.
	 *
	 * @param relpath the relpath
	 * @return the upload url
	 */
	public static Resource getUploadURL(String relpath) {
		return new ThemeResource("./../../" + "uploads/" + relpath);
	}
	
	/**
	 * Gets the hR.
	 *
	 * @return the hR
	 */
	public static Label getHR() {
		Label label = new Label("<hr />");
		label.setContentMode(Label.CONTENT_XHTML);
		return label;
	}
	
	/**
	 * Gets the current theme name.
	 *
	 * @return the current theme name
	 */
	public static String getCurrentThemeName() {
		Portal portal = AppRegistry.getPortal();
		String extension = "reindeer";
		if(portal != null) {
			extension = portal.getTheme().toString().toLowerCase();
		}
		return "supportcenter_" + extension;
	}
	
	/**
	 * Gets the top url.
	 *
	 * @return the top url
	 */
	public static String getTopURL() {
		String url = AppRegistry.getAbstractApplication().getURL().toString();
		url = url.replaceFirst("/enquirer/", "");
		url = url.replaceFirst("/employee/", "");
		url = url.replaceFirst("/portal/", "");
		return url;
	}
	
	/**
	 * Gets the portal url for employee.
	 *
	 * @return the portal url for employee
	 */
	public static String getPortalURLForEmployee() {
		Portal portal = AppRegistry.getPortal();
		return getPortalURLForEmployee(portal.getId());
	}
	
	/**
	 * Gets the portal url for enquirer.
	 *
	 * @return the portal url for enquirer
	 */
	public static String getPortalURLForEnquirer() {
		Portal portal = AppRegistry.getPortal();
		return getPortalURLForEnquirer(portal.getId());
	}
	
	/**
	 * Gets the portal url for employee.
	 *
	 * @param portalId the portal id
	 * @return the portal url for employee
	 */
	public static String getPortalURLForEmployee(long portalId) {
		return getTopURL() + "/employee/?portal=" + portalId;
	}
	
	/**
	 * Gets the portal url for enquirer.
	 *
	 * @param portalId the portal id
	 * @return the portal url for enquirer
	 */
	public static String getPortalURLForEnquirer(long portalId) {
		return getTopURL() + "/enquirer/?portal=" + portalId;
	}
	
	/**
	 * Gets the portal iframe template.
	 *
	 * @return the portal iframe template
	 */
	private static String getPortalIframeTemplate() {
		return "<iframe src=\"##link##\" style=\"width:100%; height:800px; border:none\"></iframe>";
	}
	
	/**
	 * Gets the portal iframe for employee.
	 *
	 * @return the portal iframe for employee
	 */
	public static String getPortalIframeForEmployee() {
		return getPortalIframeTemplate().replaceFirst("##link##", getPortalURLForEmployee());
	}
	
	/**
	 * Gets the portal iframe for enquirer.
	 *
	 * @return the portal iframe for enquirer
	 */
	public static String getPortalIframeForEnquirer() {
		return getPortalIframeTemplate().replaceFirst("##link##", getPortalURLForEnquirer());
	}
	
	/**
	 * Gets the portal iframe for employee.
	 *
	 * @param portalId the portal id
	 * @return the portal iframe for employee
	 */
	public static String getPortalIframeForEmployee(long portalId) {
		return getPortalIframeTemplate().replaceFirst("##link##", getPortalURLForEmployee(portalId));
	}
	
	/**
	 * Gets the portal iframe for enquirer.
	 *
	 * @param portalId the portal id
	 * @return the portal iframe for enquirer
	 */
	public static String getPortalIframeForEnquirer(long portalId) {
		return getPortalIframeTemplate().replaceFirst("##link##", getPortalURLForEnquirer(portalId));
	}
}
