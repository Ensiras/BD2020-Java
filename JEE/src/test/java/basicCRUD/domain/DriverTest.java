package basicCRUD.domain;

import org.jboss.weld.junit5.auto.AddBeanClasses;
import org.jboss.weld.junit5.auto.AddPackages;
import org.jboss.weld.junit5.auto.EnableAlternatives;
import org.jboss.weld.junit5.auto.EnableAutoWeld;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;

import static org.assertj.core.api.Assertions.assertThat;

@EnableAutoWeld
@AddPackages(Driver.class)
@AddBeanClasses(Driver.class)
@EnableAlternatives(Motorcycle.class) // Make sure the @Alternative bean is used
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
        Drivable drivable = driver.getDrivable();
        assertThat(drivable).isInstanceOf(Car.class);
    }

    @Test
    void whenInitializedDriverDrivable2FieldShouldContainBicycle() {
        Drivable drivable = driver.getDrivable2();
        assertThat(drivable).isInstanceOf(Bicycle.class);
    }

    @Test
    void whenInitializedDriverDrivable3FieldShouldContainMotorcycle() {
        Drivable drivable = driver.getDrivable3();
        assertThat(drivable).isInstanceOf(Motorcycle.class);
    }

    @Test
    void whenInitializedDriverDrivable3FieldShouldContainBoat() {
        Drivable drivable = driver.getDrivable4();
        assertThat(drivable).isInstanceOf(Boat.class);
    }
}
