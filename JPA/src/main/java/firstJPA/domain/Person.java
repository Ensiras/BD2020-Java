package firstJPA.domain;

import firstJPA.dao.BooleanTFConverter;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.CascadeType.MERGE;
import static javax.persistence.FetchType.LAZY;
import static javax.persistence.TemporalType.*;

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

    @Max(value = 100)
    private int age;

    @Enumerated
    private Gender gender;

    @Convert(converter = BooleanTFConverter.class)
    boolean human;

    @Email
    String email;

    // Field valued relationships --------------------

    // Unidirectional, owning side
    @ManyToOne(cascade = ALL)
    private Department employedAt;

    // Bidirectional, owning side
    @ManyToOne(cascade = ALL)
    private ParkingSpace parkingSpace;

    // Collection valued relationships ---------------

    // Unidirectional: forced to create linking table
    @OneToMany(cascade = ALL)
    private List<Phone> phones = new ArrayList<>();

    // Bidirectional, passive side: foreign key field indicated by mappedBy.
    @OneToMany(cascade = MERGE, mappedBy = "owner")
    private List<Laptop> laptops = new ArrayList<>();

    //Bidirectional
    @ManyToMany
    private Set<Department> worksAtDepartments;

    @Enumerated(EnumType.STRING)
    private HairColor hairColor;

    @Temporal(DATE)
    private Date dateOfEntry = new Date();

    @Lob @Basic(fetch = LAZY)
    private String biography;

    public ParkingSpace getParkingSpace() {
        return parkingSpace;
    }

    public void setParkingSpace(ParkingSpace parkingSpace) {
        this.parkingSpace = parkingSpace;
    }

    public String getBiography() {
        return biography;
    }

    public void setBiography(String biography) {
        this.biography = biography;
    }

    public Person() {}

    public Person(String name, int age, Gender gender, boolean human) {
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.human = human;
    }

    public Date getDateOfEntry() {
        return dateOfEntry;
    }

    public void setDateOfEntry(Date dateOfEntry) {
        this.dateOfEntry = dateOfEntry;
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

    // Add new laptop to person and set its owner (needed to keep relationship symmetrical)
    public void addLaptop(Laptop lp) {
        this.laptops.add(lp);
        lp.setOnwer(this);
    }

    public void addPhone(Phone p) {
        phones.add(p);
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

