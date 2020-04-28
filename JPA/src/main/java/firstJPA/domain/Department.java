package firstJPA.domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Entity
public class Department {

    @Id @GeneratedValue
    int id;
    String name;

    // Bidirectional weak side
    @ManyToMany(mappedBy = "worksAtDepartments")
    private List<Person> workers = new ArrayList<>();

    public Department() {
    }

    public Department(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Department{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    public void addWorker(Person p) {
        this.workers.add(p);
    }
}
