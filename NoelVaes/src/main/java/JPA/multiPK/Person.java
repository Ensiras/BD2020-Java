package JPA.multiPK;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;


// Class with composite key
@Entity
// Point towards class that defines PK
@IdClass(PersonPK.class)
public class Person {
    private String firstName;
    private String lastName;
    private int age;

    public Person() {
    }

    // @Id needed above every property that's part of the PK
    @Id
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Id
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setAge(int age) {
        this.age = age;
    }
}