/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package geburtststag.web;

import geburtststag.ejb.CategoryBean;
import geburtststag.ejb.GeburtstagBean;
import geburtststag.jpa.Category;
import geburtststag.jpa.Geburtstag;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Isabel
 */
@WebServlet(name = "GeburtstagListServlet", urlPatterns = {"/app/geburtstage/list/"})
public class GeburtstagListServlet extends HttpServlet {

    @EJB
    private CategoryBean categoryBean;
    
    @EJB
    private GeburtstagBean geburtstagBean;
    

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
            
        // Verfügbare Kategorien für die Suchfelder ermitteln
        request.setAttribute("categories", this.categoryBean.findAllSorted());

        // Suchparameter aus der URL auslesen
        String searchText = request.getParameter("search_text");
        String searchCategory = request.getParameter("search_category");

        // Anzuzeigende Aufgaben suchen
        Category category = null;

        if (searchCategory != null) {
            try {
                category = this.categoryBean.findById(Long.parseLong(searchCategory));
            } catch (NumberFormatException ex) {
                category = null;
            }
        }

        List<Geburtstag> geburtstage = this.geburtstagBean.search(searchText, category);
        request.setAttribute("geburtstage", geburtstage);

        // Anfrage an die JSP weiterleiten
        request.getRequestDispatcher("/WEB-INF/geburtstage/geburtstag_list.jsp").forward(request, response);
    }


    /*
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {   
        
    }
    */

}
