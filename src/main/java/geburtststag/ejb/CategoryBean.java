/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package geburtststag.ejb;

import administration.ejb.EntityBean;
import geburtststag.jpa.Category;
import java.util.List;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Isabel
 */
@Stateless
@RolesAllowed("app-user")
public class CategoryBean extends EntityBean<Category, Long> {

    public CategoryBean() {
        super(Category.class);
    }


    /*
    @PersistenceContext
    EntityManager em;

    public Category saveNewCategory(Category category) {
        em.persist(category);
        return em.merge(category);
    }

    public Category deleteCategory(long id) {
        Category category = em.find(Category.class, id);

        if (category != null) {
            em.remove(category);
        }
        return category;
    }

    public Category updateCategory(Category category) {
        return em.merge(category);
    }


    public Category findCategory(long id) {
        return em.find(Category.class, id);
    }
    */

    public List<Category> findAllSorted() {
        return this.em.createQuery("SELECT c FROM Category c ORDER BY c.name").getResultList();
    }


}
