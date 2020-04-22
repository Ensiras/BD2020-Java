package firstJPA.domain;

import javax.persistence.Entity;
import javax.persistence.Id;


@Entity
public class Department {

    @Id
    int id;
    String name;

    public Department() {
    }

    public Department(String name) {
        this.name = name;
    }
}
