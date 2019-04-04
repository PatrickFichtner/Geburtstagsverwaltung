/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package geburtststag.web;


import administration.web.FormValues;
import geburtststag.ejb.CategoryBean;
import geburtststag.ejb.GeburtstagBean;
import geburtststag.jpa.Category;
import geburtststag.jpa.Geburtstag;
import java.io.IOException;
import java.util.List;
import javax.ejb.EJB;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Isabel
 */
@WebServlet(name = "CategoryServlet", urlPatterns = {"/app/geburtstage/categories/"})
public class CategoryServlet extends HttpServlet {

    @EJB
    CategoryBean categoryBean;

    @EJB
    GeburtstagBean geburtstagBean;

    @EJB
    ValidationBean validationBean;


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        //Alle Kategorien ermitteln
        request.setAttribute("categories", this.categoryBean.findAllSorted());

        //Anfrage an JSP weiterleiten
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/geburtstage/category.jsp");
        dispatcher.forward(request, response);

        //Alte Formulardaten aus der Session entfernen
        HttpSession session = request.getSession();
        session.removeAttribute("categories_form");
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Angeforderte Aktion ausführen
        String action = request.getParameter("action");

        if (action == null) {
            action = "";
        }

        switch (action) {
            case "create":
                this.createCategory(request, response);
                break;
            case "delete":
                this.deleteCategories(request, response);
                break;
        }
    }

    /**
     * Aufgerufen in doPost(): Neue Kategorie anlegen
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    private void createCategory(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Formulareingaben prüfen
        String name = request.getParameter("name");

        Category category = new Category(name);
        List<String> errors = this.validationBean.validate(category);

        // Neue Kategorie anlegen
        if (errors.isEmpty()) {
            this.categoryBean.saveNew(category);
        }

        // Browser auffordern, die Seite neuzuladen
        if (!errors.isEmpty()) {
            FormValues formValues = new FormValues();
            formValues.setValues(request.getParameterMap());
            formValues.setErrors(errors);

            HttpSession session = request.getSession();
            session.setAttribute("categories_form", formValues);
        }

        response.sendRedirect(request.getRequestURI());
    }

    /**
     * Aufgerufen in doPost(): Markierte Kategorien löschen
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    private void deleteCategories(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Markierte Kategorie IDs auslesen
        String[] categoryIds = request.getParameterValues("category");

        if (categoryIds == null) {
            categoryIds = new String[0];
        }

        // Kategorien löschen
        for (String categoryId : categoryIds) {
            // Zu löschende Kategorie ermitteln
            Category category;

            try {
                category = this.categoryBean.findById(Long.parseLong(categoryId));
            } catch (NumberFormatException ex) {
                continue;
            }

            if (category == null) {
                continue;
            }

            // Bei allen betroffenen Aufgaben, den Bezug zur Kategorie aufheben
            List<Geburtstag> geburtstage = category.getGeburtstage();

            if (geburtstage != null) {
                geburtstage.forEach((Geburtstag gb) -> {
                    gb.setCategory(null);
                    this.geburtstagBean.update(gb);
                });
            }

            // Und weg damit
            this.categoryBean.delete(category);
        }

        // Browser auffordern, die Seite neuzuladen
        response.sendRedirect(request.getRequestURI());
    }


}
