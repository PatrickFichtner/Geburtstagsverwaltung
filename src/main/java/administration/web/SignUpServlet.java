/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package administration.web;

/**
 *
 * @author Patrick Fichtner
 */
//import dhbwka.wwi.vertsys.javaee.jtodo.common.ejb.ValidationBean;
import administration.ejb.UserBean;
import administration.ejb.ValidationBean;
import administration.ejb.SignUpBean;
import administration.jpa.User;
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
 * Servlet für die Registrierungsseite. Hier kann sich ein neuer Benutzer
 * registrieren. Anschließend wird der auf die Startseite weitergeleitet.
 */
@WebServlet(urlPatterns = {"/signup/"})
public class SignUpServlet extends HttpServlet {

    @EJB
    ValidationBean validationBean;

    @EJB
    UserBean userBean;

    @EJB
    SignUpBean signupBean;

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Anfrage an dazugerhörige JSP weiterleiten
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/login/signup.jsp");
        dispatcher.forward(request, response);

        // Alte Formulardaten aus der Session entfernen
        HttpSession session = request.getSession();
        session.removeAttribute("signup_form");
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Formulareingaben auslesen
        String username = request.getParameter("signup_username");
        String address = request.getParameter("signup_address");
        String email = request.getParameter("signup_email");
        String password1 = request.getParameter("signup_password1");
        String password2 = request.getParameter("signup_password2");

        // Eingaben prüfen
        User user = new User(username, password1, address, email);
        //List<String> errors = this.validationBean.validate(user);
        //this.validationBean.validate(user.getPassword(), errors);

        //if (password1 != null && password2 != null && !password1.equals(password2)) {
            //errors.add("Die beiden Passwörter stimmen nicht überein.");
        //} else {
             this.signupBean.signup(username, password1, address, email);
                request.getRequestDispatcher("/WEB-INF/login/login.jsp").forward(request, response);
                //}

        // Neuen Benutzer anlegen
        /**if (errors.isEmpty()) {
            try {
                //this.userBean.signup(username, password1);
                  this.signupBean.signup(username, address, email, password1);
            } catch (UserBean.UserAlreadyExistsException ex) {
                errors.add(ex.getMessage());
            }
        }*/

        // Weiter zur nächsten Seite
        /**if (errors.isEmpty()) {
            // Keine Fehler: Startseite aufrufen
            request.login(username, password1);
            //response.sendRedirect(WebUtils.appUrl(request, "/app/dashboard/"));
        } else {
            // Fehler: Formuler erneut anzeigen
            //FormValues formValues = new FormValues();
            //formValues.setValues(request.getParameterMap());
            //formValues.setErrors(errors);

            HttpSession session = request.getSession();
            //session.setAttribute("signup_form", formValues);

            response.sendRedirect(request.getRequestURI());
        }*/

    }

}