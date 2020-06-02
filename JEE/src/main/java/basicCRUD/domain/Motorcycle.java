package basicCRUD.domain;

import javax.enterprise.inject.Alternative;

@Alternative
public class Motorcycle implements Drivable {

    @Override
    public String drive() {
        return "Riding the motorcycle.";
    }
}
