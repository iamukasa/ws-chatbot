// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package de.vlad.supportcenter.model;

import de.vlad.supportcenter.model.SupportCategory;
import java.lang.Long;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PersistenceContext;
import org.springframework.transaction.annotation.Transactional;

privileged aspect SupportCategory_Roo_Entity {
    
    declare @type: SupportCategory: @Entity;
    
    @PersistenceContext
    transient EntityManager SupportCategory.entityManager;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long SupportCategory.id;
    
    public Long SupportCategory.getId() {
        return this.id;
    }
    
    public void SupportCategory.setId(Long id) {
        this.id = id;
    }
    
    @Transactional
    public void SupportCategory.persist() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.persist(this);
    }
    
    @Transactional
    public void SupportCategory.remove() {
        if (this.entityManager == null) this.entityManager = entityManager();
        if (this.entityManager.contains(this)) {
            this.entityManager.remove(this);
        } else {
            SupportCategory attached = SupportCategory.findSupportCategory(this.id);
            this.entityManager.remove(attached);
        }
    }
    
    @Transactional
    public void SupportCategory.flush() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.flush();
    }
    
    @Transactional
    public void SupportCategory.clear() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.clear();
    }
    
    @Transactional
    public SupportCategory SupportCategory.merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        SupportCategory merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
    }
    
    public static final EntityManager SupportCategory.entityManager() {
        EntityManager em = new SupportCategory().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }
    
    public static long SupportCategory.countSupportCategorys() {
        return entityManager().createQuery("SELECT COUNT(o) FROM SupportCategory o", Long.class).getSingleResult();
    }
    
    public static List<SupportCategory> SupportCategory.findAllSupportCategorys() {
        return entityManager().createQuery("SELECT o FROM SupportCategory o", SupportCategory.class).getResultList();
    }
    
    public static SupportCategory SupportCategory.findSupportCategory(Long id) {
        if (id == null) return null;
        return entityManager().find(SupportCategory.class, id);
    }
    
    public static List<SupportCategory> SupportCategory.findSupportCategoryEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM SupportCategory o", SupportCategory.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }
    
}