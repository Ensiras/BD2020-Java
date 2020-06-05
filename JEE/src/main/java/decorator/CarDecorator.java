package decorator;

import javax.decorator.Decorator;
import javax.decorator.Delegate;
import javax.inject.Inject;

@Decorator
public class CarDecorator implements Drivable{

    @Inject
    @Delegate
    Car car;

    @Override
    public String drive(String driverName) {
        return car.drive(driverName + " 'the maniac'");
    }
}
