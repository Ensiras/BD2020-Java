package basicCRUD.resources;

import basicCRUD.domain.Car;
import basicCRUD.domain.Cars;
import basicCRUD.services.CarService;
import io.swagger.annotations.Api;

import javax.faces.bean.SessionScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;


@Path("cars")
@Api(value = "cars")
@Produces(MediaType.APPLICATION_JSON)
@SessionScoped
public class CarsResource {

    @Inject
    CarService carService;

    @GET
    public Cars getAll() {
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
        if (carService.add(car)) {
            return car;
        }
        return null;
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


}
