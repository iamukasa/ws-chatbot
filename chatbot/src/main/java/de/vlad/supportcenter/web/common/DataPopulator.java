package de.vlad.supportcenter.web.common;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.springframework.transaction.annotation.Transactional;

import de.vlad.supportcenter.model.Locale;
import de.vlad.supportcenter.model.Portal;
import de.vlad.supportcenter.model.SupportAgent;
import de.vlad.supportcenter.model.SupportCategory;
import de.vlad.supportcenter.model.SupportUser;
import de.vlad.supportcenter.model.enums.SystemStatus;
import de.vlad.supportcenter.model.enums.Theme;


/**
 * The Class DataPopulator.
 */
public class DataPopulator {
	
	/**
	 * Populate data.
	 */
	@SuppressWarnings("unused")
	@Transactional
    public void populateData() {
		
		if(SupportUser.countSupportUsers() == 0) {
			
	    	Locale localeDe = new Locale();
	    	localeDe.setLocaleKey("de");
	    	localeDe.setLocaleName("Deutsch");
	    	localeDe.persist();
	    	
	    	Portal portal = new Portal();
	    	portal.setEmail("max@mustermann.de");
	    	portal.setPassword("server");
	    	portal.setLocale(localeDe);
	    	portal.setLastChange(new Date());
	    	
	    	portal.setChatEnabled(true);
	    	portal.setSystemStatus(SystemStatus.ONLINE);
	    	portal.setTheme(Theme.REINDEER);
	    	portal.setHostFilterEnabled(false);
	    	portal.persist();

	    	SupportCategory categoryGeneral = createSupportCategory("Allgemein", false, portal);
	    	SupportCategory categoryTechnical = createSupportCategory("Technisch", false, portal);
	    	SupportCategory categoryIntern = createSupportCategory("Intern", true, portal);
	    	SupportCategory categoryInternSpecial = createSupportCategory("Intern Spezial", true, portal);
	    	
	    	SupportUser user = new SupportUser();
	    	user.setDisplayName("Max Mustermann");
	    	user.setEmail("max@mustermann.de");
	    	user.setActive(true);
	    	user.setPassword("server");
	    	user.setPortal(portal);
	    	user.persist();
	    	
	    	Set<SupportCategory> adminCategories = new HashSet<SupportCategory>();
	    	adminCategories.add(categoryIntern);
//	    	adminCategories.add(categoryTechnical);
	    	adminCategories.add(categoryGeneral);
	    	
	    	SupportAgent agent = new SupportAgent();
	    	agent.setDisplayName("John Doe");
	    	agent.setEmail("john@doe.de");
	    	agent.setActive(true);
	    	agent.setPassword("lalala");
	    	agent.setPortal(portal);
	    	agent.setChatPermission(true);
	    	agent.setChatEnabled(true);
	    	agent.setAdminCategories(adminCategories);
	    	agent.persist();
	    	
	    	categoryIntern.getSupportAgents().add(agent);
	    	categoryGeneral.getSupportAgents().add(agent);
	    	categoryIntern = categoryIntern.merge();
	    	categoryGeneral = categoryGeneral.merge();
	    	
	    	
		}
    	
    }
	
	/**
	 * Creates the support category.
	 *
	 * @param name the name
	 * @param isIntern the is intern
	 * @param portal the portal
	 * @return the support category
	 */
	private SupportCategory createSupportCategory(String name, boolean isIntern, Portal portal) {
    	
    	SupportCategory category = new SupportCategory();
    	category.setActive(true);
    	category.setIntern(isIntern);
    	category.setPortal(portal);
    	category.setName(name);
    	category.persist();
    	return category;
	}
	
}
