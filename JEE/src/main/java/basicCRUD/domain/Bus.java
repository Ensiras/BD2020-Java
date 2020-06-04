package basicCRUD.domain;

import basicCRUD.util.BusAnno;

import javax.enterprise.inject.Alternative;

@Alternative @BusAnno
public class Bus implements Drivable{
    @Override
    public String drive() {
        return "Driving the bus.";
    }
}
