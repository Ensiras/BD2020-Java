package firstJPA.domain;

import javax.persistence.Entity;
import javax.persistence.EntityResult;
import javax.persistence.FieldResult;
import javax.persistence.SqlResultSetMapping;

@SqlResultSetMapping(name = "robotResult", entities = {
        @EntityResult(entityClass = Robot.class, fields = {
                @FieldResult(name = "id", column = "r.personId"),
                @FieldResult(name = "name", column = "p.name"),
                @FieldResult(name = "gender", column = "p.gender"),
                @FieldResult(name = "version", column = "r.version")
        })
})
@Entity
public class Robot extends Person {

    private String version;

    public Robot(String name, int age, Gender gender, boolean human, String version) {
        super(name, age, gender, human);
        this.version = version;
    }

    public Robot() {
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}
