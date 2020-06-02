package basicCRUD.domain;

import basicCRUD.util.BicycleAnno;
import basicCRUD.util.CarAnno;

import javax.enterprise.inject.Alternative;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

public class Driver {

    private final List<Drivable> drivables = new ArrayList<>();

    @Inject // Field injection
    @CarAnno
    private Drivable drivable;

    private Drivable drivable2;

    @Inject  // CTOR injection, note annotated parameter
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

    @Inject // Setter injection
    public void setDrivable(@BicycleAnno Drivable drivable) {
        this.drivable2 = drivable;
    }

    private void addDrivable(Drivable d) {
        drivables.add(d);
    }
}
