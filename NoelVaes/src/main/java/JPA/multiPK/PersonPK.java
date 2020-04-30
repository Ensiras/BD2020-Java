package JPA.multiPK;

import java.io.Serializable;
import java.util.Objects;

// Specific PK class
/* Requirements:
*  - implements Seriazable
*  - Default ctor
*  - Accessor methods for every field that's part of PK
*  - Implementation of equals() and hashCode() */

public class PersonPK implements Serializable {
    private String firstName;
    private String lastName;

    public PersonPK() {
    }

    public PersonPK(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PersonPK personPK = (PersonPK) o;
        return firstName.equals(personPK.firstName) &&
                lastName.equals(personPK.lastName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName);
    }
}
