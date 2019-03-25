/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package geburtststag.web;

import dhbwka.wwi.vertsys.javaee.jtodo.common.ejb.UserBean;
import dhbwka.wwi.vertsys.javaee.jtodo.common.ejb.ValidationBean;
import geburtststag.ejb.GeburtstagBean;
import geburtststag.jpa.Geburtstag;
import java.io.IOException;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author belizbalim
 */

/**
 * Seite zum Anlegen oder Bearbeiten eines Geburtstags.
 */
@WebServlet(urlPatterns = "/app/geburtstage/geburtstag/*")
public class GeburtstagEditServlet extends HttpServlet {
    
    @EJB
    GeburtstagBean gb;
    
    //TODO EJB Category
    
    @EJB
    UserBean userBean;
    
    @EJB
    ValidationBean validationBean;
    
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        //TODO
        // Verfügbare Kategorien für die Suchfelder ermitteln
        //request.setAttribute("categories", this.categoryBean.findAllSorted());
        //request.setAttribute("statuses", TaskStatus.values());

        // Zu bearbeitende Geburtstag einlesen
        HttpSession session = request.getSession();

        Geburtstag geburtstag = this.getRequestedGeburtstag(request);
        request.setAttribute("edit", geburtstag.getId() != 0);
                                
        if (session.getAttribute("geburtstag_form") == null) {
            // Keine Formulardaten mit fehlerhaften Daten in der Session,
            // daher Formulardaten aus dem Datenbankobjekt übernehmen
            request.setAttribute("geburtstag_form", this.createGeburtstagForm(geburtstag));
        }

        // Anfrage an die JSP weiterleiten
        request.getRequestDispatcher("/WEB-INF/geburtstage/geburtstag_edit.jsp").forward(request, response);
        
        session.removeAttribute("gebutstag_form");
    }

    private Geburtstag getRequestedGeburtstag(HttpServletRequest request) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private Object createGeburtstagForm(Geburtstag geburtstag) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
