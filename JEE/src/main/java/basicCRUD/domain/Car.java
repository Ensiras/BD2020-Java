package basicCRUD.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/*@Entity*/
public class Car {

/*    @Id
    @GeneratedValue*/
    private int id;
    private String brand;
    private String color;

    // Note: non-arg ctor needed for converting to json (and JPA)
    public Car() {
    }

    public Car(String brand, String color) {
        this.brand = brand;
        this.color = color;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    // Note: getters for all fields needed for converting to json
    public String getBrand() {
        return brand;
    }

    public String getColor() {
        return color;
    }
}
