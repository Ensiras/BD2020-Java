package basicCRUD.services;

import basicCRUD.Dao.CarDao;
import basicCRUD.domain.Car;
import basicCRUD.domain.Cars;


import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Stateless
public class CarService {

    @Inject
    private CarDao carDao;

    List<Car> carList = new ArrayList<>();

    public Car getById(int id) {
        return carDao.get(id);
    }

    public Cars getCarList() {
        carList.add(new Car("Ferrari", "red"));
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

    public Car add(Car car) {
        return carDao.insert(car);
    }

    public Cars getByBrand(String brand) {
        List<Car> carsFiltered = carList.stream()
                .filter(s -> s.getBrand().toLowerCase().contains(brand.toLowerCase()))
                .collect(Collectors.toList());
        return Cars.getCars(carsFiltered);
    }
}
