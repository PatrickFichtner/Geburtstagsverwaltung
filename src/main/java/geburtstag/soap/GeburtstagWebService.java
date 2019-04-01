/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package geburtstag.soap;
import geburtststag.ejb.GeburtstagBean;
import geburtststag.jpa.Geburtstag;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.jws.WebMethod;
import javax.jws.WebResult;
import javax.jws.WebService;

/**
 *
 * @author belizbalim
 */
@Stateless
@WebService
public class GeburtstagWebService {
    
    @EJB
    GeburtstagBean gb;
    
    @WebMethod
    @WebResult(name="geburtstage")
    public List<Geburtstag> getAllGeburtstage() {
        return this.gb.findAll();
    }

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
