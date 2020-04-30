package mappingNativeTest;

import org.jboss.logging.Field;

import javax.persistence.*;


// It seems that with this kind of mapping you need to map all fields, even if they are not required. If
// any field is either missing here or in the query, exception is thrown.
@SqlResultSetMapping(name = "employeeResult", entities = {
        @EntityResult(entityClass = Employee.class, fields = {
                @FieldResult(name = "id", column = "id"),
                @FieldResult(name = "name", column = "name"),
                @FieldResult(name = "somethingElse", column = "somethingElse")
        })
})
@Entity
public class Employee {

    @Id @GeneratedValue
    int id;

    String name;

    String somethingElse;

    public Employee() {
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
