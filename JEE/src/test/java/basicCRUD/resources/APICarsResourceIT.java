package basicCRUD.resources;

import basicCRUD.App;
import basicCRUD.Dao.CarDao;
import basicCRUD.domain.Car;
import basicCRUD.services.CarService;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;

import java.io.File;
import java.net.URL;

import static org.assertj.core.api.Assertions.assertThat;


@RunWith(Arquillian.class)
public class APICarsResourceIT {


    @ArquillianResource
    private URL deploymentURL;

    private String carsResource;

    @Before
    public void setup() {
        carsResource = deploymentURL + "resources/cars";
    }

    // Arquillian automatically generates a .war needed for the test
    // Persistence-test.xml and empty beans.xml file only needed for 'semi-embedded' Arquillian on wlp.
    @Deployment
    public static Archive<?> createDeployment() {
        WebArchive webArchive = ShrinkWrap.create(WebArchive.class, "test.war")
                .addClass(App.class)
                .addClass(CarDao.class)
                .addClass(CarsResource.class)
                .addClass(CarService.class)
                .addPackage(Car.class.getPackage())
                .addAsResource("persistence-test.xml", "META-INF/persistence.xml")
                .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml")
                .addAsLibraries(assertJ()) // create files of assertJ and hibernate libs and add to war
                .addAsLibraries(hibernate());
        System.out.println(webArchive.toString(true));
        return webArchive;
    }

    // Needed to provide hibernate & assertJ functionality to war
    private static File[] assertJ() {
        return Maven.resolver()
                .loadPomFromFile("pom.xml")
                .resolve("org.assertj:assertj-core")
                .withTransitivity()
                .asFile();
    }

    private static File[] hibernate() {
        return Maven.resolver()
                .loadPomFromFile("pom.xml")
                .resolve("org.hibernate:hibernate-entitymanager")
                .withTransitivity().asFile();
    }

    @Test
    public void getByIdShouldReturnCarFromDBAsJSON() {
        String message = ClientBuilder.newClient()
                .target(carsResource + "/1")
                .request(MediaType.APPLICATION_JSON)
                .get(String.class);

        System.out.println(message);
        assertThat(message).contains("Ferrari");
    }

    @Test
    public void postCarsShouldStoreInDB() {
        Car car = new Car("Porsche", "blue");
        Car response = ClientBuilder.newClient()
                .target(carsResource)
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.json(car), Car.class);

        String message = ClientBuilder.newClient()
                .target(carsResource + "/" + response.getId())
                .request(MediaType.APPLICATION_JSON)
                .get(String.class);

        assertThat(message).contains(String.valueOf(response.getId()),
                response.getBrand(),
                response.getColor());
    }
}

