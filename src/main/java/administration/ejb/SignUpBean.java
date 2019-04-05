/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package administration.ejb;

/**
 *
 * @author Patrick Fichtner
 */
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ejb.EJBContext;
import javax.ejb.Stateless;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import administration.jpa.User;

/**
 *
 * @author Patrick Fichtner
 */
@Stateless
public class SignUpBean {

@PersistenceContext
    EntityManager em;

@Resource
    EJBContext ctx;

@Resource
    Validator validator;

       /**
     * @return Liste mit allen Gästebucheinträgen
     */
    public List<User> findAllEntries() {
        return em.createQuery("SELECT e FROM User e ")
                .getResultList();
    }


     /** Speichert einen neuen Benutzer.
     * @param benutzername Name des Besuchers
     * @return Der gespeicherte Eintrag
     */

    public void signup(String username, String firstname, String lastname, String password1) throws  UserAlreadyExistsException {
         if (em.find(User.class, username) != null) {
            throw new UserAlreadyExistsException("Der Benutzername $B ist bereits vergeben.".replace("$B", username));
        }
        User user = new User(username, firstname, lastname, password1);
        user.addToGroup("app-user");
////moneymanager.user_group
        em.persist(user);
        //return em.merge(user);
    }

/**
     *
     * @param username
     * @param password
     * @throws UserBean.UserAlreadyExistsException
     */
    public void signup(String username, String password1) throws UserAlreadyExistsException {
        if (em.find(User.class, username) != null) {
            throw new UserAlreadyExistsException("Der Benutzername $B ist bereits vergeben.".replace("$B", username));
        }

        User user = new User(username, password1);
        user.addToGroup("app-user");
        //user.addToGroupBenutzer(benutzername);
        em.persist(user);
    }

 /**
     * Fehler: Der Benutzername ist bereits vergeben
     */
    public class UserAlreadyExistsException extends Exception {

        public UserAlreadyExistsException(String message) {
            super(message);
        }
    }




     /**public class UserExistsException extends Exception {

        public UserExistsException(String message) {
            super(message);
        }
    }

     public <T> List<String> validate(T object) {
        List<String> messages = new ArrayList<>();
        return this.validate(object, messages);
    }*/

        /**public <T> List<String> validate(T object) {
        List<String> messages = new ArrayList<>();
        return this.validate(object, messages);
    }*/

    /**
     * Wertet die "Bean Validation" Annotationes des übergebenen Objekts aus
     * und stellt die gefundenen Meldungen in messages.
     *
     * @param <T>
     * @param object Zu überprüfendes Objekt
     * @param messages List mit Fehlermeldungen
     * @return Selbe Liste wie messages
     */
    public <T> List<String> validate(T object, List<String> messages) {
        Set<ConstraintViolation<T>> violations = this.validator.validate(object);

        //violations.forEach((ConstraintViolation<T> violation) -> {
           // messages.add(violation.getMessage());
        //});

        return messages;
    }


}
