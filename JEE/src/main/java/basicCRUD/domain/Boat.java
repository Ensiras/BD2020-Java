package basicCRUD.domain;

import javax.inject.Named;

@Named("Boat") // Named bean, note that this doesn't make it non-default only custom qualifiers / alternative do
public class Boat implements Drivable {
    @Override
    public String drive() {
        return "Driving the boat.";
    }
}
