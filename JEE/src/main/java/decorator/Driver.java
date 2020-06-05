package decorator;

import javax.inject.Inject;

public class Driver {

    @Inject Car car;

    public String drive(String driverName) {
        return car.drive(driverName);
    }

}
