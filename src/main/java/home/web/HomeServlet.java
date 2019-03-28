/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package home.web;

import home.ejb.HomeContent;
import home.ejb.HomeSection;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author belizbalim
 */
@WebServlet(urlPatterns = {"/app/home/"})
public class HomeServlet extends HttpServlet {
    
    @EJB
    HomeContent hc;
    
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Dashboard-Rubriken und Kacheln erzeugen und im Request Context ablegen
        List<HomeSection> sections = new ArrayList<>();
        
        
        hc.createHomeContent(sections);

        //Home-Rubriken und Kacheln im Request Context ablegen und Anfrage an die JSP weiterleiten
        request.setAttribute("sections", sections);
        request.getRequestDispatcher("/WEB-INF/dashboard/dashboard.jsp").forward(request, response);
    }
}
