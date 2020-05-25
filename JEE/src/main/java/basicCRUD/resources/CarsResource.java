package basicCRUD.resources;

import basicCRUD.domain.Car;
import basicCRUD.services.CarService;

import javax.faces.bean.SessionScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("cars")
@Produces(MediaType.APPLICATION_JSON)
@SessionScoped
public class CarsResource {

    @Inject
    CarService carService;

    @GET
    public List<Car> getAll() {
        return carService.getCarList();
    }

    @GET
    @Path("/q")
    public Car getById(@QueryParam("id") int id) {
        return carService.getById(id);
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
