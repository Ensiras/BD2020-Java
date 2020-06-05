package decorator;

public class Car implements Drivable {

    @Override
    public String drive(String driverName) {
        return driverName + " is driving the car.";
    }
}
