package basicCRUD.domain;

import org.jboss.weld.junit5.auto.AddBeanClasses;
import org.jboss.weld.junit5.auto.AddPackages;
import org.jboss.weld.junit5.auto.EnableAlternatives;
import org.jboss.weld.junit5.auto.EnableAutoWeld;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.InstanceOfAssertFactories.predicate;

@EnableAutoWeld
@AddPackages(Driver.class)
@AddBeanClasses(Driver.class)
//@EnableAlternatives({Motorcycle.class, Bus.class}) // Make sure the @Alternative beans are used
class DriverTest {

    @Inject
    private Driver driver;

    @Test
    void whenDriveAllShouldReturnMessage() {
        String result = driver.driveAll();
        assertThat(result).contains("bike");
    }

    @Test
    void whenInitializedDriverDrivableFieldShouldContainCar() {
        Drivable drivable = driver.getDrivableCar();
        assertThat(drivable).isInstanceOf(Car.class);
    }

    @Test
    void whenInitializedDriverDrivable2FieldShouldContainBicycle() {
        Drivable drivable = driver.getDrivableBicycle();
        assertThat(drivable).isInstanceOf(Bicycle.class);
    }


    @Test
    void whenInitializedDriverDrivable3FieldShouldContainBoat() {
        Drivable drivable = driver.getDrivable4();
        assertThat(drivable).isInstanceOf(Boat.class);
    }

    @Test
    void anyInjectInInstanceTest() {
        // Getting a specific bean within the Instance collection
        Drivable d = driver.allDrivables.select(Boat.class).get();
        System.out.println(d.drive());

        // Getting a different bean is also possible
        Drivable d2 = driver.allDrivables.select(Bicycle.class).get();
        System.out.println(d2.drive());

         /* And iterating through all Drivables falling with constraints.
         * Note that apparently when enabling alternatives @Any refers to all alternatives
         * instead of all 'normal' beans. */
        System.out.println("Iterating: ---------------------------------");
        for (Drivable drivable : driver.allDrivables) {
            System.out.println(drivable.drive());
        }
    }

    @Test
    void providerTest() {
        // Getting the actual bean from the provider
        Drivable drivable = driver.singleDrivableProvider.get();
        System.out.println(drivable.drive());
    }
}
