package firstJPA.domain;

import firstJPA.dao.BooleanTFConverter;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.CascadeType.MERGE;

@NamedQueries({
        @NamedQuery(name = "getAll", query = "SELECT p FROM Person p"),
        @NamedQuery(name = "getAllByName", query = "SELECT p FROM Person p WHERE p.name = :name")
})

@Entity
public class Person {

    @Id
    @GeneratedValue
    @Column(name = "personId")
    private int id;

    @Size(max = 20)
    private String name;
    private int age;

    @Enumerated
    private Gender gender;

    @Convert(converter = BooleanTFConverter.class)
    boolean human;

    @Email
    String email;

    @ManyToOne(cascade = ALL)
    private Department employedAt;

    @OneToMany(cascade = MERGE, mappedBy = "owner")
    private List<Laptop> laptops = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private HairColor hairColor;

    public Person() {}

    public Person(String name, int age, Gender gender, boolean human) {
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.human = human;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setEmployedAt(Department employedAt) {
        this.employedAt = employedAt;
    }

    public void setHairColor(HairColor haircolor) {
        this.hairColor = haircolor;
    }

    public void addLaptop(Laptop lp) {
        this.laptops.add(lp);
        lp.setOnwer(this);
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", gender=" + gender +
                ", human=" + human +
                ", employedAt=" + employedAt +
                ", hairColor=" + hairColor +
                '}';
    }
}

