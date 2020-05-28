package basicCRUD.domain;

import java.util.ArrayList;
import java.util.List;

public class Cars {

    List<Car> cars = new ArrayList<>();

    public Cars() {
    }

    private Cars(List<Car> cars) {
        this.cars.addAll(cars);
    }

    public static Cars getCars(List<Car> cars) {
        return new Cars(cars);
    }

    public List<Car> getCars() {
        return cars;
    }
}
