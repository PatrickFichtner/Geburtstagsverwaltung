package administration.ejb;

import administration.jpa.User;
import javax.annotation.Resource;
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJBContext;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import administration.ejb.EntityBean;

/**
 * Spezielle EJB zum Anlegen eines Benutzers und Aktualisierung des Passworts.
 */
@Stateless
public class UserBean extends EntityBean<User, String> {

    @PersistenceContext
    EntityManager em;

    @Resource
    EJBContext ctx;

    /**
     * Gibt das Datenbankobjekt des aktuell eingeloggten Benutzers zurück,
     *
     * @return Eingeloggter Benutzer oder null
     */
    public User getCurrentUser() {
        return this.em.find(User.class, this.ctx.getCallerPrincipal().getName());
    }

    public UserBean() {
        super(User.class);
    }

    /**
     *
     * @param username
     * @param password
     * @throws UserBean.UserAlreadyExistsException
     */
    public void signup(String username, String password) throws UserAlreadyExistsException {
        if (em.find(User.class, username) != null) {
            throw new UserAlreadyExistsException("Der Benutzername $B ist bereits vergeben.".replace("$B", username));
        }

        User user = new User(username, password);
        user.addToGroup("app-user");
        em.persist(user);
    }

    /**
     * Passwort ändern (ohne zu speichern)
     *
     * @param user
     * @param oldPassword
     * @param newPassword
     * @throws UserBean.InvalidCredentialsException
     */
    @RolesAllowed("app-user")
    public void changePassword(User user, String oldPassword, String newPassword) throws InvalidCredentialsException {
        if (user == null || !user.checkPassword(oldPassword)) {
            throw new InvalidCredentialsException("Benutzername oder Passwort sind falsch.");
        }

        user.setPassword(newPassword);
    }

    /**
     * Benutzer löschen
     *
     * @param user Zu löschender Benutzer
     */
    @RolesAllowed("app-user")
    public void delete(User user) {
        this.em.remove(user);
    }

    /**
     * Benutzer aktualisieren
     *
     * @param user Zu aktualisierender Benutzer
     * @return Gespeicherter Benutzer
     */
    @RolesAllowed("app-user")
    public User update(User user) {
        return em.merge(user);
    }

    /**
     * Fehler: Der Benutzername ist bereits vergeben
     */
    public class UserAlreadyExistsException extends Exception {

        public UserAlreadyExistsException(String message) {
            super(message);
        }
    }

    /**
     * Fehler: Das übergebene Passwort stimmt nicht mit dem des Benutzers
     * überein
     */
    public class InvalidCredentialsException extends Exception {

        public InvalidCredentialsException(String message) {
            super(message);
        }
    }

    /**
     * Anmeldedaten eines Benutzers sowie Benutzergruppenzuordnung und
     * Zugehörigkeit zu einer der übergebenen Benutzergruppen prüfen.
     */
    public User validateUser(String username, String password, String... groups)
            throws InvalidCredentialsException, AccessRestrictedException {

        // Benutzer suchen und Passwort prüfen
        User user = em.find(User.class, username);
        boolean authorize = false;

        if (user == null || !user.checkPassword(password)) {
            throw new InvalidCredentialsException("Benutzername oder Passwort falsch.");
        }

        // Zugeordnete Benutzergruppen prüfen, mindestens eine muss vorhanden sein
        for (String group : groups) {
            if (user.getGroups().contains(group)) {
                authorize = true;
                break;
            }
        }

        if (!authorize) {
            throw new AccessRestrictedException("Sie sind hierfür nicht berechtigt.");
        }

        // Alles okay!
        return user;
    }

    public class AccessRestrictedException extends Exception {

        public AccessRestrictedException(String message) {
            super(message);
        }
    }

}
