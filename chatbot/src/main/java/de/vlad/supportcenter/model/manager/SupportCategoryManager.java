package de.vlad.supportcenter.model.manager;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import de.vlad.supportcenter.dto.SupportCategoryDTO;
import de.vlad.supportcenter.model.SupportAgent;
import de.vlad.supportcenter.model.SupportCategory;
import de.vlad.supportcenter.web.common.AppRegistry;


/**
 * The Class SupportCategoryManager.
 */
@Component
public class SupportCategoryManager {
	
	/** The entity manager. */
	@PersistenceContext
	private transient EntityManager entityManager;
	
	
	/**
	 * Creates the support category.
	 *
	 * @param name the name
	 * @return the support category
	 */
	@Transactional
	public SupportCategory createSupportCategory(String name) {
		SupportCategory category = createSupportCategoryWithoutPersist(name);
		category.persist();
		return category;
	}
	
	/**
	 * Creates the support category without persist.
	 *
	 * @param name the name
	 * @return the support category
	 */
	@Transactional
	public SupportCategory createSupportCategoryWithoutPersist(String name) {
		SupportCategory category = new SupportCategory();
		category.setPortal(AppRegistry.getPortal());
		category.setActive(true);
		category.setName(name);
		category.setIntern(false);
		return category;
	}
	
	/**
	 * Update support category.
	 *
	 * @param dto the dto
	 * @return the support category
	 */
	@Transactional
	public SupportCategory updateSupportCategory(SupportCategoryDTO dto) {
		SupportCategory category = SupportCategory.findSupportCategory(dto.getId());
		category.setActive(dto.isActive());
		category.setIntern(dto.isIntern());
		category.setName(dto.getName());
		category.setPortal(AppRegistry.getPortal());
		Set<SupportAgent> supportAgents = category.getSupportAgents();
		for(Iterator<SupportAgent> i = supportAgents.iterator();i.hasNext();) {
			SupportAgent agent = i.next();
			if(!dto.getSupportAgents().contains(agent)) {
				i.remove();
				agent.getAdminCategories().remove(category);
				agent.merge();
			}
		}
		for(SupportAgent agent : dto.getSupportAgents()) {
			if(!supportAgents.contains(agent)) {
				supportAgents.add(agent);
				agent.getAdminCategories().add(category);
				agent.merge();
			}
		}
		return category.merge();
	}
	
	/**
	 * Gets the support category for edit.
	 *
	 * @param categoryId the category id
	 * @return the support category for edit
	 */
	@Transactional
	public SupportCategoryDTO getSupportCategoryForEdit(Long categoryId) {
		SupportCategory category = SupportCategory.findSupportCategory(categoryId);
		SupportCategoryDTO dto = new SupportCategoryDTO();
		dto.setId(category.getId());
		dto.setActive(category.isActive());
		dto.setIntern(category.isIntern());
		dto.setName(category.getName());
		dto.setSupportAgents(category.getSupportAgents());
		return dto;
	}
	
	/**
	 * Update support category status.
	 *
	 * @param categoryId the category id
	 * @param enabled the enabled
	 * @return the support category
	 */
	@Transactional
	public SupportCategory updateSupportCategoryStatus(Long categoryId, boolean enabled) {
		SupportCategory category = SupportCategory.findSupportCategory(categoryId);
		if(category != null) {
			category.setActive(enabled);
			return category.merge();
		} else {
			return null;
		}
	}
	
	/**
	 * Removes the support category.
	 *
	 * @param categoryId the category id
	 * @return true, if successful
	 */
	@Transactional
	public boolean removeSupportCategory(Long categoryId) {
		SupportCategory category = SupportCategory.findSupportCategory(categoryId);
		if(category != null) {
			category.remove();
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Find categories by portal.
	 *
	 * @param isIntern the is intern
	 * @return the list
	 */
	public List<SupportCategory> findCategoriesByPortal(boolean isIntern) {
		TypedQuery<SupportCategory> query = entityManager.createQuery("select s from SupportCategory s where s.portal = ?1 AND s.intern = ?2", SupportCategory.class);
        query.setParameter(1, AppRegistry.getPortal());
        query.setParameter(2, isIntern);
        return query.getResultList();
    }
	
	/**
	 * Find all categories.
	 *
	 * @return the sets the
	 */
	public Set<SupportCategory> findAllCategories() {
		TypedQuery<SupportCategory> query = entityManager.createQuery("select s from SupportCategory s where s.portal = ?1", SupportCategory.class);
        query.setParameter(1, AppRegistry.getPortal());
        return new HashSet<SupportCategory>(query.getResultList());
    }
    
	/**
	 * Find default category.
	 *
	 * @param isIntern the is intern
	 * @return the support category
	 */
	public SupportCategory findDefaultCategory(boolean isIntern) {
    	TypedQuery<SupportCategory> query = entityManager.createQuery("select s from SupportCategory s where s.portal = ?1 AND s.intern = ?2", SupportCategory.class);
         query.setParameter(1, AppRegistry.getPortal());
         query.setParameter(2, isIntern);
         query.setMaxResults(1);
         List<SupportCategory> result = query.getResultList();
         if(result != null && !result.isEmpty()) {
         	return result.get(0);
         } else {
         	return new SupportCategory();
         }
    }
    
}
