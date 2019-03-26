/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package geburtststag.jpa;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.TableGenerator;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Isabel
 */
public class category implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "category_ids")
    @TableGenerator(name = "category_ids", initialValue = 0, allocationSize = 10)
    private long id;
    
    @Column(length = 30)
    @NotNull(message = "Der Name darf nicht leer sein.")
    @Size(min = 3, max = 30, message = "Der Name muss zwischen drei und 30 Zeichen lang sein.")
    private String name;
    
    @OneToMany(mappedBy = "category")
    List<Geburtstag> geburtstage = new ArrayList<>();
    
    
    //Konstruktoren

    public category() {
    }

    public category(String name) {
        this.name = name;
    }
    
    
    //Getter und Setter

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Geburtstag> getGeburtstage() {
        return geburtstage;
    }

    public void setGeburtstage(List<Geburtstag> geburtstage) {
        this.geburtstage = geburtstage;
    }
    
}
