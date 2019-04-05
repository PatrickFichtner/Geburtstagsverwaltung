/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package geburtstag.soap;
import geburtstag.ejb.CategoryBean;
import geburtstag.ejb.GeburtstagBean;
import geburtstag.jpa.Category;
import geburtstag.jpa.Geburtstag;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.jws.WebMethod;
import javax.jws.WebResult;
import javax.jws.WebService;
import administration.ejb.UserBean;
import javax.jws.WebParam;

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
    public List<Geburtstag> getAllGeburtstage(
            @WebParam(name = "username") String username,
            @WebParam(name = "password") String password)
            throws UserBean.InvalidCredentialsException, 
            UserBean.AccessRestrictedException {
        
        this.userBean.validateUser(username, password, "app-user");
        
        return this.gb.findAll();
    }

    @WebMethod
    @WebResult(name="categories")
    public List<Category> getAllCategories(
            @WebParam(name = "username") String username,
            @WebParam(name = "password") String password) 
            throws UserBean.InvalidCredentialsException, 
            UserBean.AccessRestrictedException {
        
        this.userBean.validateUser(username, password, "app-user");
        return this.categoryBean.findAll();
    }

}
