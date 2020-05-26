package basicCRUD.services;

import basicCRUD.domain.Car;
import basicCRUD.domain.Cars;

import javax.ejb.Stateful;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static basicCRUD.domain.Values.CARLIST;

@Stateful // CarService has state
public class CarService {

    List<Car> carList = new ArrayList<>(CARLIST);

    public Car getById(int id) {
        return carList.get(id);
    }

    public Cars getCarList() {
        return Cars.getCars(this.carList);
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

    public Cars getByBrand(String brand) {
        List<Car> carsFiltered = carList.stream()
                .filter(s -> s.getBrand().toLowerCase().contains(brand.toLowerCase()))
                .collect(Collectors.toList());
        return Cars.getCars(carsFiltered);
    }
}
