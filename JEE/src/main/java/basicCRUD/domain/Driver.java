package basicCRUD.domain;

import basicCRUD.util.BicycleAnno;
import basicCRUD.util.CarAnno;


import javax.enterprise.inject.Any;
import javax.enterprise.inject.Default;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Provider;
import java.util.ArrayList;
import java.util.List;

public class Driver {

    private final List<Drivable> drivables = new ArrayList<>();

    @Inject // Field injection
    @CarAnno // Inject the Drivable with @CarAnno
    private Drivable drivableCar;

    private Drivable drivableBicycle;

    @Inject @Default // Inject default Drivable OR alternative if stated
    private Drivable drivableDefaultExplicit;

    @Inject
    private @Named("Boat")
    Drivable drivable4; // Injecting bean with the name "Boat"

    @Inject   // CTOR injection, note annotated parameter
    public Driver(@BicycleAnno Drivable d) {
        addDrivable(d);
    }

    @Inject
    @Any
    Instance<Drivable> allDrivables;

    @Inject
    Provider<Drivable> singleDrivableProvider; // Drivable will be boat (default)

    public String driveAll() {
        StringBuilder message = new StringBuilder();
        for (Drivable drivable : drivables) {
            message.append(drivable.drive()).append(" ");
        }
        return message.toString();
    }

    public Drivable getDrivableCar() {
        return drivableCar;
    }

    public Drivable getDrivableBicycle() {
        return drivableBicycle;
    }


    @Inject // Setter injection
    public void setDrivableBicycle(@BicycleAnno Drivable drivableBicycle) {
        this.drivableBicycle = drivableBicycle;
    }

    private void addDrivable(Drivable d) {
        drivables.add(d);
    }

    public Drivable getDrivable4() {
        return drivable4;
    }

    public Drivable getDrivableDefaultExplicit() {
        return drivableDefaultExplicit;
    }
}
