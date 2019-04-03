/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package geburtststag.web;

import dhbwka.wwi.vertsys.javaee.jtodo.common.ejb.UserBean;
import dhbwka.wwi.vertsys.javaee.jtodo.common.ejb.ValidationBean;
import dhbwka.wwi.vertsys.javaee.jtodo.common.web.FormValues;
import dhbwka.wwi.vertsys.javaee.jtodo.common.web.WebUtils;
import geburtststag.ejb.CategoryBean;
import geburtststag.ejb.GeburtstagBean;
import geburtststag.jpa.Category;
import geburtststag.jpa.Geburtstag;
import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
    
    @EJB
    CategoryBean cb;
    
    @EJB
    UserBean userBean;
    
    @EJB
    ValidationBean validationBean;
    
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Verfügbare Kategorien für die Suchfelder ermitteln  
        try {
            request.setAttribute("categories", this.cb.findAllSorted());
        }
        catch (NullPointerException npe) {
            
        }
        
       
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
    
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Angeforderte Aktion ausführen
        String action = request.getParameter("action");

        if (action == null) {
            action = "";
        }

        switch (action) {
            case "save":
                this.saveGeburtstag(request, response);
                break;
            case "delete":
                this.deleteGeburtstag(request, response);
                break;
        }
    }

     /**
     * Zu bearbeitende Geburtstag aus der URL ermitteln und zurückgeben. Gibt
     * entweder einen vorhandenen Datensatz oder ein neues, leeres Objekt
     * zurück.
     *
     * @param request HTTP-Anfrage
     * @return Zu bearbeitende Geburtstag
     */
    private Geburtstag getRequestedGeburtstag(HttpServletRequest request) {
    
        // Zunächst davon ausgehen, dass ein neuer Satz angelegt werden soll
        Geburtstag geburtstag = new Geburtstag ();
        geburtstag.setOwner(this.userBean.getCurrentUser());
        geburtstag.setName("");
        geburtstag.setDate(new Date(System.currentTimeMillis()));

        // ID aus der URL herausschneiden
        String geburtstagId = request.getPathInfo();

        if (geburtstagId == null) {
            geburtstagId = "";
        }

        geburtstagId = geburtstagId.substring(1);

        if (geburtstagId.endsWith("/")) {
            geburtstagId = geburtstagId.substring(0, geburtstagId.length() - 1);
        }

        // Versuchen, den Datensatz mit der übergebenen ID zu finden
        try {
            geburtstag = this.gb.findById(Long.parseLong(geburtstagId));
        } catch (NumberFormatException ex) {
            // Ungültige oder keine ID in der URL enthalten
        }

        return geburtstag; 
    }

    /**
     * Neues FormValues-Objekt erzeugen und mit den Daten eines aus der
     * Datenbank eingelesenen Datensatzes füllen. Dadurch müssen in der JSP
     * keine hässlichen Fallunterscheidungen gemacht werden, ob die Werte im
     * Formular aus der Entity oder aus einer vorherigen Formulareingabe
     * stammen.
     *
     * @param geburtstag Die zu bearbeitende AGeburtstag
     * @return Neues, gefülltes FormValues-Objekt
     */
    private Object createGeburtstagForm(Geburtstag geburtstag) {
         Map<String, String[]> values = new HashMap<>();

        values.put("geburtstag_owner", new String[]{
            geburtstag.getOwner().getUsername()
        });
        
        values.put("geburtstag_title", new String[]{
            geburtstag.getTitle()
        });
        
        values.put("geburtstag_name", new String[]{
            geburtstag.getName()
        });
        
        values.put("geburtstag_surname", new String[]{
            geburtstag.getSurname()
        });
        
        values.put("geburtstag_notiz", new String[]{
            geburtstag.getNotiz()
        });
        
        //TODO Category
        if (geburtstag.getCategory() != null) {
            values.put("geburtstag_category", new String[]{
                "" + geburtstag.getCategory().getId()
            });
        }


        values.put("geburtstag_date", new String[]{
            WebUtils.formatDate(geburtstag.getDate())
        });

        FormValues formValues = new FormValues();
        formValues.setValues(values);
        return formValues;
    
    }

    /**
     * Aufgerufen in doPost(): Neue oder vorhandene Geburtstag speichern
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    private void saveGeburtstag(HttpServletRequest request, HttpServletResponse response) throws IOException {
        
        // Formulareingaben prüfen
        List<String> errors = new ArrayList<>();

        String geburtstagTitle = request.getParameter("geburtstag_title");
        String geburtstagName = request.getParameter("geburtstag_name");
        String geburtstagSurname = request.getParameter("geburtstag_surname");
        String stringGeburtstagDate = request.getParameter("geburtstag_date");
        String geburtstagNotiz = request.getParameter("geburtstag_notiz");
        String geburtstagCategory = request.getParameter("geburtstag_category");
        
        Geburtstag geburtstag = this.getRequestedGeburtstag(request);

        /*if (geburtstagCategory != null && !geburtstagCategory.trim().isEmpty()) {
            try {
                geburtstag.setCategory(this.cb.findById(Long.parseLong(geburtstagCategory)));
            } catch (NumberFormatException ex) {
                // Ungültige oder keine ID mitgegeben
            }
        }*/
         
        geburtstag.setTitle(geburtstagTitle);
        
        if (!geburtstagName.trim().isEmpty()) {
            geburtstag.setName(geburtstagName.trim());
        } else {
            errors.add("Geben Sie bitte einen Namen ein.");
        }
        
        geburtstag.setSurname(geburtstagSurname.trim());
        Date geburtstagDate = WebUtils.parseDate(stringGeburtstagDate);
        geburtstag.setNotiz(geburtstagNotiz);
      
        
        if (geburtstagDate != null) {
            geburtstag.setDate(geburtstagDate);
        } else {
            errors.add("Das Datum muss dem Format dd.mm entsprechen.");
        }
        
        this.validationBean.validate(geburtstag, errors);

        // Datensatz speichern
        if (errors.isEmpty()) {
            this.gb.update(geburtstag);
        }

        // Weiter zur nächsten Seite
        if (errors.isEmpty()) {
            // Keine Fehler: Startseite aufrufen
            response.sendRedirect(WebUtils.appUrl(request, "/app/geburtstage/home/"));
        } else {
            // Fehler: Formuler erneut anzeigen
            FormValues formValues = new FormValues();
            formValues.setValues(request.getParameterMap());
            formValues.setErrors(errors);

            HttpSession session = request.getSession();
            session.setAttribute("geburtstag_form", formValues);

            response.sendRedirect(request.getRequestURI());
        }
    }

    /**
     * Aufgerufen in doPost: Vorhandene Task löschen
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    
    private void deleteGeburtstag(HttpServletRequest request, HttpServletResponse response) throws IOException {
       // Datensatz löschen
        Geburtstag geburtstag = this.getRequestedGeburtstag(request);
        this.gb.delete(geburtstag);

        // Zurück zur Übersicht
        response.sendRedirect(WebUtils.appUrl(request, "/app/geburtstage/list/"));
    }

}
