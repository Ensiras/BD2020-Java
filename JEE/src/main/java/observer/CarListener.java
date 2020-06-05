package observer;

import javax.enterprise.event.Observes;

public class CarListener {

    // Method is invoked when an event on Car is fired.
    public void onCarState(@Observes Car car) {
        System.out.println("Car state changed!");
        if(car.isRunning()) {
            System.out.println("Car is now running.");
        } else {
            System.out.println("Car stopped running.");
        }
    }
}
