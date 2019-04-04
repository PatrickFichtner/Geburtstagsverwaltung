/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package geburtstag.soap;
import geburtststag.ejb.CategoryBean;
import geburtststag.ejb.GeburtstagBean;
import geburtststag.jpa.Category;
import geburtststag.jpa.Geburtstag;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.jws.WebMethod;
import javax.jws.WebResult;
import javax.jws.WebService;
import administration.ejb.UserBean;
import administration.jpa.User;

/**
 *
 * @author belizbalim
 */
@Stateless
@WebService
public class GeburtstagWebService {

    @EJB
    GeburtstagBean gb;

    @EJB
    CategoryBean categoryBean;

    @EJB
    UserBean userBean;

    @WebMethod
    @WebResult(name="geburtstage")
    public List<Geburtstag> getAllGeburtstage() {
        return this.gb.findAll();
    }

    @WebMethod
    @WebResult(name="categories")
    public List<Category> getAllCategories() {
        return this.categoryBean.findAll();
    }

    /**@WebMethod
    @WebResult(name="User")
    public List<User> getAllUser() {
        return this.userBean.findAll();
    }*/

    /*@WebMethod
    @WebResult(name="gig")
    public List<Gig> getAllGigs() {
        return this.gigBean.findAll();
    }

    @WebMethod
    @WebResult(name="gig")
    public Gig getGigDetails(@WebParam(name="id") long id) {
        return this.gigBean.findById(id);
    }*/

}
