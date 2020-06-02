package basicCRUD.domain;

import basicCRUD.util.BicycleAnno;
import basicCRUD.util.CarAnno;


import javax.inject.Inject;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.List;

public class Driver {

    private final List<Drivable> drivables = new ArrayList<>();

    @Inject // Field injection
    @CarAnno // Inject the Drivable with @CarAnno
    private Drivable drivable;

    private Drivable drivable2;

    @Inject // Inject default Drivable OR alternative if stated
    private Drivable drivable3;

    @Inject
    private @Named("Boat") Drivable drivable4; // Injecting bean with the name "Boat"

    @Inject   // CTOR injection, note annotated parameter
    public Driver(@BicycleAnno Drivable d) {
        addDrivable(d);
    }

    public String driveAll() {
        StringBuilder message = new StringBuilder();
        for (Drivable drivable : drivables) {
            message.append(drivable.drive()).append(" ");
        }
        return message.toString();
    }

    public Drivable getDrivable() {
        return drivable;
    }

    public Drivable getDrivable2() {
        return drivable2;
    }

    public Drivable getDrivable3() {
        return drivable3;
    }

    @Inject // Setter injection
    public void setDrivable(@BicycleAnno Drivable drivable) {
        this.drivable2 = drivable;
    }

    private void addDrivable(Drivable d) {
        drivables.add(d);
    }

    public Drivable getDrivable4() {
        return drivable4;
    }
}
