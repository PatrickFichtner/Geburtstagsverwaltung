
package geburtststag.ejb;


import administration.ejb.EntityBean;
import geburtststag.jpa.Geburtstag;
import geburtststag.jpa.Category;
import java.sql.Date;
import java.util.List;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
 *
 * @author belizbalim
 */
@Stateless
@RolesAllowed("app-user")
public class GeburtstagBean extends EntityBean<Geburtstag, Long> {


    public GeburtstagBean() {
        super(Geburtstag.class);
    }

    /**
     * Alle Geburststage gespeichert von einem Benutzer, nach Fälligkeit sortiert zurückliefern.
     * @param username Benutzername
     * @return Alle Geburtstage gespeichert von einem Benutzer
     */
    public List<Geburtstag> findByUsername(String username) {
        return em.createQuery("SELECT g FROM Geburtstag g WHERE g.owner.username = :username ORDER BY g.date")
                 .setParameter("username", username)
                 .getResultList();
    }


    /**
     * Suche nach Geburtstage anhand des Namens und Kategorie.
     *
     * Anders als in der Vorlesung behandelt, wird die SELECT-Anfrage hier
     * mit der CriteriaBuilder-API vollkommen dynamisch erzeugt.
     *
     * @param name  Der Name des Geburtstagskind
     * @param category Kategorie (optional)
     * @return Liste mit den gefundenen Aufgaben
     */
    public List<Geburtstag> search(String name, Category category) {
        // Hilfsobjekt zum Bauen des Query
        CriteriaBuilder cb = this.em.getCriteriaBuilder();

        // SELECT g FROM Geburtstag g
        CriteriaQuery<Geburtstag> query = cb.createQuery(Geburtstag.class);
        Root<Geburtstag> from = query.from(Geburtstag.class);
        query.select(from);

        // ORDER BY date
        query.orderBy(cb.asc(from.get("date")));

        // WHERE t.name LIKE :name
        Predicate p = cb.conjunction();

        if (name != null && !name.trim().isEmpty()) {
            p = cb.and(p, cb.like(from.get("name"), "%" + name + "%"));
            query.where(p);
        }

        // WHERE t.category = :category
        if (category != null) {
            p = cb.and(p, cb.equal(from.get("category"), category));
            query.where(p);
        }


        return em.createQuery(query).getResultList();
    }


    /**
     * Die Geburtstage -gespeichert von einem Benutzer- nach einem bestimmten Datum suchen
     * @param username Benutzername
     * @param date Datum
     * @return Alle Geburtstage gespeichert von einem Benutzer an einem bestimmten Tag
     */
    public List<Geburtstag> findByDate (Date date) {
        return em.createQuery(
                        "SELECT g FROM Geburtstag g"
                      + "  WHERE g.date = :date"
                     )
                     .setParameter("date", date)
                     .getResultList();
    }


    public Geburtstag updateDate(long id, Date date) {
        Geburtstag geburtstag = super.em.find(Geburtstag.class, id);
        if (geburtstag == null)
            return null;

        geburtstag.setDate(date);
        return super.em.merge(geburtstag);
    }
}

