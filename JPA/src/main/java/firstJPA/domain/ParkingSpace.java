package firstJPA.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity

public class ParkingSpace {

    @Id
    @GeneratedValue
    int id;

    @OneToMany(mappedBy = "parkingSpace")
    List<Person> persons = new ArrayList<>();

    public ParkingSpace() {
    }

    public ParkingSpace(int id) {
        this.id = id;
    }

    public void add(Person person) {
        this.persons.add(person);
    }
}
