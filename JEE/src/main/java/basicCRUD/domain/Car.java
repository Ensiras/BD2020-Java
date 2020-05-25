package basicCRUD.domain;

public class Car {
    private String brand;
    private int year;

    // Note: non-arg ctor needed for converting to json
    public Car() {
    }

    public Car(String brand, int year) {
        this.brand = brand;
        this.year = year;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    // Note: getters for all fields needed for converting to json
    public String getBrand() {
        return brand;
    }

    public int getYear() {
        return year;
    }
}
