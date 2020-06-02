package basicCRUD.domain;


import basicCRUD.util.BicycleAnno;


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
