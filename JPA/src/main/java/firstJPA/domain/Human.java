package firstJPA.domain;

import firstJPA.util.BooleanTFConverter;

import javax.persistence.Convert;
import javax.persistence.Entity;

@Entity
public class Human extends Person {

    @Convert(converter = BooleanTFConverter.class)
    private boolean isAlive;

    public Human(String name, int age, Gender gender, boolean human, boolean isAlive) {
        super(name, age, gender, human);
        this.isAlive = isAlive;
    }

    public Human() {
    }
}
