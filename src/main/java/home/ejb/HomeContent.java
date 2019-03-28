/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package home.ejb;


import dhbwka.wwi.vertsys.javaee.jtodo.common.web.WebUtils;
import geburtststag.ejb.GeburtstagBean;
import geburtststag.jpa.Geburtstag;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author belizbalim
 */
@Stateless
public class HomeContent {

    @EJB 
    GeburtstagBean gb;
    
    public void createHomeContent(List<HomeSection> sections) {
   
        HomeSection section = new HomeSection();
        section.setLabel("Heute");
       
        String stringToday = WebUtils.formatUtilDate(new Date());
        java.sql.Date sqlToday = WebUtils.parseDate(stringToday);
              
        List<Geburtstag> geburtstage = gb.findByDate(sqlToday);
       
        Iterator it = geburtstage.iterator();
        
        
        while (it.hasNext()) {
            HomeTile tile = new HomeTile ();
            Geburtstag geburtstag = (Geburtstag) it.next();
            
            tile.setName(geburtstag.getFullname());
            section.getTiles().add(tile);
        }
        
        boolean add = sections.add(section);
        //ADD HREF?? TODO        
    }
    
}
