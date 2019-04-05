/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package geburtstag.soap;
import administration.ejb.SignUpBean;
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
import administration.jpa.User;
import java.sql.Date;
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
            @WebParam(name = "username", header = true) String username,
            @WebParam(name = "password", header = true) String password)
            throws UserBean.InvalidCredentialsException {
        
        this.userBean.validateUser(username, password, "app-user");
        
        return this.gb.findAll();
    }
    
    @WebMethod
    @WebResult(name="geburtstage")
    public List<Geburtstag> getGeburtstagByDate(
            @WebParam(name = "username", header = true) String username,
            @WebParam(name = "password", header = true) String password,
            @WebParam(name = "date", header = false) Date date) throws UserBean.InvalidCredentialsException {
        
        this.userBean.validateUser(username, password, "app-user");
        return this.gb.findByDate(date);
    }

    @WebMethod
    @WebResult(name="categories")
    public List<Category> getAllCategories(
            @WebParam(name = "username", header = true) String username,
            @WebParam(name = "password", header = true) String password) {
        
        this.userBean.validateUser(username, password, "app-user");
        
        return this.categoryBean.findAll();
    }

}
