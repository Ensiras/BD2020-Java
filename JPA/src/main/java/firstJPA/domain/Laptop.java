package firstJPA.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import static javax.persistence.CascadeType.MERGE;

@Entity
public class Laptop {

    @Id @GeneratedValue
    int id;
    String brand;
    @ManyToOne(cascade = MERGE)
    Person owner;

    public Laptop() {
    }

    public Laptop(String brand) {
        this.brand = brand;
    }

    public void setOnwer(Person p) {
        this.owner = p;
    }
}
