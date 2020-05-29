package basicCRUD.resources;

import basicCRUD.domain.Car;
import basicCRUD.domain.Cars;
import basicCRUD.services.CarService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;


@Path("cars")
@Produces(MediaType.APPLICATION_JSON)
public class CarsResource {

    @Inject
    CarService carService;

    public CarService getCarService() {
        return carService;
    }

    @GET
    public Car getAll() { // TODO: return a list Cars here instead of just one
        return carService.getCarList();
    }

    @GET
    @Path("{id}")
    public Car getById(@PathParam("id") int id) {
        return carService.getById(id);
    }

    // Get by brand
    @GET
    @Path("/q")
    public Cars getByBrand(@QueryParam("brand") String brand) {
        return carService.getByBrand(brand);
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Car addCar(Car car) {
        return carService.add(car);
    }

    @DELETE
    @Path("{id}")
    public Car removeCar(@PathParam("id") int id) {
        return carService.removeById(id);
    }

    @PUT
    public Car updateBrand(@QueryParam("id") int id,
                           @QueryParam("brand") String brand) {
        return carService.updateBrand(id, brand);
    }

    public void setCarService(CarService carService) {
        this.carService = carService;
    }

}
