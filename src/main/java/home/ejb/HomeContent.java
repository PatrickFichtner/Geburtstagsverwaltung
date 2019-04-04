/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package home.ejb;


import administration.web.WebUtils;
import geburtststag.ejb.GeburtstagBean;
import geburtststag.jpa.Category;
import geburtststag.jpa.Geburtstag;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 * EJB zur Definition der Home-Kacheln für Aufgaben.
 * @author belizbalim
 */
@Stateless
public class HomeContent {

    @EJB
    GeburtstagBean gb;


    /**
     * Vom Home aufgerufenen Methode, um die anzuzeigenden Rubriken und
     * Kacheln zu ermitteln.
     *
     * @param sections Liste der Home-Rubriken und Kacheln, an die die neuen Rubriken
     * angehängt werden müssen
     */
    public void createHomeContent(List<HomeSection> sections) {

        // Zunächst einen Abschnitt mit Geburtstage von Heute erstellen und einfügen
        HomeSection section1 = this.createSection("Heute");
        sections.add(section1);
    }

    private HomeSection createSection(String label) {
        HomeSection section = new HomeSection();
        section.setLabel(label);

        //Ermitteln von Geburtstage von heute anhand eine Datenbankquery
            String stringToday = WebUtils.formatUtilDate(new Date());
            java.sql.Date sqlToday = WebUtils.parseDate(stringToday);
            List<Geburtstag> geburtstage = gb.findByDate(sqlToday);
            Iterator it = geburtstage.iterator();

            //Tiles erstellen, beschriften und auf den Abschnitt einfügen
            while (it.hasNext()) {
                HomeTile tile = new HomeTile ();
                Geburtstag geburtstag = (Geburtstag) it.next();
                Category category = geburtstag.getCategory();
                if (category != null){
                    tile.setCategory(geburtstag.getCategory().getName());
                }
                else {
                    tile.setCategory("");
                }
                tile.setName(geburtstag.getFullname());
                tile.setIcon("calendar");
                tile.setHref("/app/geburtstage/geburtstag/"+geburtstag.getId());
                section.getTiles().add(tile);
            }
        return section;
    }
}


