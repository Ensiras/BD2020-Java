package observer;

import javax.enterprise.event.Event;
import javax.inject.Inject;

public class Driver {

    @Inject
    Car car;

    @Inject
    Event<Car> carEvent; // Allows for firing events on Car

    public void startEngine() {
        car.setRunning(true);
        carEvent.fire(car); // Fire carEvent with Car as parameter, letting Observers know that something happend.
    }

    public void killEngine() {
        car.setRunning(false);
        carEvent.fire(car);
    }
}
