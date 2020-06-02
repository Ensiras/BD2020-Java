package basicCRUD.resources;

import basicCRUD.dao.CarDao;
import basicCRUD.domain.Car;
import basicCRUD.services.CarService;
import org.jboss.weld.junit5.auto.AddBeanClasses;
import org.jboss.weld.junit5.auto.EnableAutoWeld;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;

import static org.assertj.core.api.Assertions.assertThat;


/* Possible solution to the problem: test at most 1 dependency deep. So test just the dao and DB communication, not
* all the way from resources to db. Then test every step along the way separately. */

@EnableAutoWeld
@AddBeanClasses({CarsResource.class, CarService.class, CarDao.class})
//@EnableAlternatives(EntityManagerProducerAlt.class)
public class CarsResourceIT {

    @Inject
    private CarsResource resource;

/*    @BeforeEach
    void setup() {
        // Crappy way to force h2 EntityManager on Dao.
        resource.getCarService().getCarDao().setEm(Persistence.createEntityManagerFactory("h2").createEntityManager());
    }*/

    @Test
    void whenCarIsPostedShouldBeStoredInAndRetrievableFromDB() {
        Car posted = resource.addCar(new Car("Just a blue one", "red"));
        Car car = resource.getById(posted.getId());
        assertThat(car.toString()).contains("Just a blue one", "red");
    }
}
