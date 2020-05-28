package basicCRUD.resources;

import basicCRUD.App;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;
;
import java.io.File;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertFalse;

@RunWith(Arquillian.class)
public class CarsResourceIT {

    //    Arquillian automatically generates a .war needed for the test
    @Deployment
    public static Archive<?> createDeployment() {
        return ShrinkWrap.create(WebArchive.class, "test.war")
                .addClass(App.class)
                .addPackage(CarsResource.class.getPackage());

    }

/*    private static File[] assertJ() {
        return Maven.resolver()
                .loadPomFromFile("pom.xml")
                .resolve("org.assertj:assertj-core")
                .withTransitivity()
                .asFile();
    }*/

    @Test
    public void getAllTest() {
        String message = ClientBuilder.newClient()
                .target("http://localhost:9080/JEE_war_exploded/resources/cars")
                .request(MediaType.APPLICATION_JSON)
                .get(String.class);

        System.out.println(message);
//        assertFalse(message.isEmpty());
        assertThat(message).contains("Renault");

    }

}
