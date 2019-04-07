package administration.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import administration.ejb.UserBean;
import administration.ejb.ValidationBean;
import administration.jpa.User;
//import administration.web.FormValues;

@WebServlet(name = "EditUserServlet", urlPatterns = {"/app/user/edit/"})

public class EditUserServlet extends HttpServlet {

    @EJB
    private UserBean userBean;

    @EJB
    private ValidationBean validationBean;

    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        User currentUser = this.userBean.getCurrentUser();
        HttpSession session = request.getSession();

        //Daten für den Benutzer müssen nur geholt werden
        FormValues form = (FormValues) session.getAttribute("edit_form");
        if (form == null || form.getErrors().isEmpty()) {
            form = this.getFormValuesForCurrentUser(currentUser);
            session.setAttribute("edit_form", form);
        }

        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/admin/editUser.jsp");
        dispatcher.forward(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        String username = request.getParameter("username");
        String newPassword = request.getParameter("newPassword");
        String newPasswordConfirm = request.getParameter("newPasswordConfirm");
        String firstname = request.getParameter("firstname");
        String lastname = request.getParameter("lastname");

        User user = new User();
        user = this.userBean.getCurrentUser();
        List<String> errors = new ArrayList<>();

        if (!newPassword.equals("") && !newPasswordConfirm.equals("")) {
            if (!newPassword.equals(newPasswordConfirm)) {
                errors.add("Die Passwörter stimmen nicht überein!");
            } else {
                user.setPassword(newPassword);
            }
        }

        user.setFirstname(firstname);
        user.setLastname(lastname);
        errors = validationBean.validate(user, errors);

        if (errors.isEmpty()) {
            this.userBean.update(user);
            response.sendRedirect(request.getContextPath() + "/index.html");
        } else {
            FormValues form = new FormValues();
            form.setValues(request.getParameterMap());
            form.setErrors(errors);
            HttpSession session = request.getSession();
            session.setAttribute("edit_form", form);
            response.sendRedirect(request.getRequestURI());
        }
    }

    private FormValues getFormValuesForCurrentUser(User currentUser) {
        FormValues form = new FormValues();

        Map<String, String[]> formValues = new HashMap<>();
        formValues.put("username", new String[]{currentUser.getUsername()});
        formValues.put("firstname", new String[]{currentUser.getFirstname()});
        formValues.put("lastname", new String[]{currentUser.getLastname()});

        form.setValues(formValues);

        return form;
    }
}
