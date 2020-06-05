package observer;

import org.jboss.weld.junit5.auto.AddPackages;
import org.jboss.weld.junit5.auto.EnableAutoWeld;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;

@EnableAutoWeld
@AddPackages(Car.class)
//@AddBeanClasses(Driver.class) apparently not necessary
class ObserverIT {

    @Inject
    Driver driver;

    @Test
    void startingOrKillingEngineShouldBeObserved() {
        driver.startEngine();
        driver.killEngine();
    }
}
