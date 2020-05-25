package basicCRUD.services;

import basicCRUD.domain.Car;

import javax.ejb.Stateful;
import java.util.ArrayList;
import java.util.List;

import static basicCRUD.domain.Values.CARLIST;

@Stateful // CarService has state
public class CarService {

    List<Car> carList = new ArrayList<>(CARLIST);

    public Car getById(int id) {
        return carList.get(id);
    }

    public List<Car> getCarList() {
        return carList;
    }

    public Car removeById(int id) {
        return carList.remove(id);
    }

    public Car updateBrand(int id, String brand) {
        Car car = getById(id);
        car.setBrand(brand);
        return car;
    }

    public boolean add(Car car) {
        return carList.add(car);
    }
}
