package basicCRUD.domain;

import java.util.Arrays;
import java.util.List;

public class Values {

    public static Car renault = new Car("Renault", 1995);
    public static Car BMW = new Car("BMW", 1995);
    public static Car renault2 = new Car("Renault", 2015);

    public static List<Car> CARLIST = Arrays.asList(renault, BMW, renault2);
}
