/**
 *
 * @author belizbalim
 */
package geburtststag.jpa;

import dhbwka.wwi.vertsys.javaee.jtodo.common.jpa.User;
import java.io.Serializable;
import java.sql.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;


@Entity
public class Geburtstag implements Serializable {
 
    @Id
    @GeneratedValue
    private long id;
    
    @ManyToOne
    @NotNull(message = "Der Geburtstag muss einem Benutzer geordnet werden.")
    private User owner;
    
    //ToDO & auch die Getter und Setter
    //@ManyToOne
    //private Category category;
  
    private String title;
    
    @NotNull(message = "Der Name darf nicht leer sein.")
    private String name;
    
    private String surname;
    
    @NotNull(message = "Das Datum darf nicht leer sein.")
    private Date date;
    
    private String notiz;
    
    //Konstruktoren
    
    public Geburtstag() {
    }
    
   //TODO - Konstruktor mit allen Variablen auch mit Category

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getFullname() {
    String name = this.title + " " + this.name + " " + this.surname;
    return name.trim();
    }
    
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }      

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public String getNotiz() {
        return notiz;
    }

    public void setNotiz(String notiz) {
        this.notiz = notiz;
    }
    
    
    
  
}
