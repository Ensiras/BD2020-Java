package basicCRUD.domain;

import javax.inject.Named;

@Named("Boat") // Named bean
public class Boat implements Drivable {
    @Override
    public String drive() {
        return "Riding the boat.";
    }
}
