package basicCRUD.domain;

import basicCRUD.util.BicycleAnno;
import javax.enterprise.inject.Default;

@BicycleAnno
public class Bicycle implements Drivable {

    @Override
    public String drive() {
        return ride();
    }

    private String ride() {
        return "Riding the bike.";
    }
}
