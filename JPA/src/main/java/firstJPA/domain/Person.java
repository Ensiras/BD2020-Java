package firstJPA.domain;

import firstJPA.util.BooleanTFConverter;
import firstJPA.util.LocalDateTimeConverter;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Size;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static javax.persistence.CascadeType.*;
import static javax.persistence.EnumType.STRING;
import static javax.persistence.FetchType.LAZY;
import static javax.persistence.InheritanceType.JOINED;
import static javax.persistence.TemporalType.DATE;


@NamedQueries({
        @NamedQuery(name = "getAll", query = "SELECT p FROM Person p"),
        @NamedQuery(name = "getAllByName", query = "SELECT p FROM Person p WHERE p.name = :name")
})

@SqlResultSetMapping(name = "personResultShort", entities = {
        @EntityResult(entityClass = Person.class, fields = {
                @FieldResult(name = "id", column = "personId"),
                @FieldResult(name = "name", column = "name"),
                @FieldResult(name = "age", column = "age"),
                @FieldResult(name = "gender", column = "gender"),
                @FieldResult(name = "human", column = "human")
        })
})


@Entity
@Inheritance(strategy = JOINED)
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

    @Enumerated(STRING)
    private HairColor hairColor;

    @Temporal(DATE)
    private Date dateOfEntry = new Date();

    @Convert(converter = LocalDateTimeConverter.class)
    private LocalTime dateOfBirth = LocalTime.now();

    @Lob
    @Basic(fetch = LAZY)
    private String biography;

    // Field valued relationships --------------------

    @OneToOne(cascade = ALL)
    private Car leaseCar;

    // Unidirectional, owning side. Linking table not necessary.
    @ManyToOne(cascade = ALL)
    private Department employedAt;

    // Bidirectional, owning side
    @ManyToOne(cascade = ALL)
    private ParkingSpace parkingSpace;

    // Collection valued relationships ---------------

    // Unidirectional: forced to create linking table
    @OneToMany(cascade = {MERGE, PERSIST}, fetch = LAZY)
    private List<Phone> phones = new ArrayList<>();

    // Bidirectional, passive side: foreign key field indicated by mappedBy.
    @OneToMany(cascade = MERGE, mappedBy = "owner")
    private List<Laptop> laptops = new ArrayList<>();

    //Bidirectional, owning side
    @ManyToMany(cascade = ALL)
    @JoinTable(name = "workersDepartments")
    private List<Department> worksAtDepartments;

    public Person() {
    }

    public Person(String name, int age, Gender gender, boolean human) {
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.human = human;
    }

    // Add new laptop to person and set its owner (needed to keep relationship symmetrical)
    public void addLaptop(Laptop lp) {
        this.laptops.add(lp);
        lp.setOnwer(this);
    }

    public void addDepartment(Department department) {
        this.worksAtDepartments.add(department);
        department.addWorker(this);
    }

    public void addPhone(Phone p) {
        phones.add(p);
    }

    public List<Phone> getPhones() {
        return phones;
    }

    public void setPhones(List<Phone> phones) {
        this.phones = phones;
    }

    public ParkingSpace getParkingSpace() {
        return parkingSpace;
    }

    public void setParkingSpace(ParkingSpace parkingSpace) {
        this.parkingSpace = parkingSpace;
        parkingSpace.add(this);
    }

    public String getBiography() {
        return biography;
    }

    public void setBiography(String biography) {
        this.biography = biography;
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

    public void setLeaseCar(Car leaseCar) {
        this.leaseCar = leaseCar;
    }

    @Cascade(value = org.hibernate.annotations.CascadeType.ALL)
    public Car getLeaseCar() {
        return leaseCar;
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

