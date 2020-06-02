package basicCRUD.domain;

import basicCRUD.util.CarAnno;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@CarAnno
public class Car implements Drivable {

    @Id
    @GeneratedValue
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

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Car{" +
                "id=" + id +
                ", brand='" + brand + '\'' +
                ", color='" + color + '\'' +
                '}';
    }

    @Override
    public String drive() {
        return "Driving the car";
    }
}
