package decorator;

import org.jboss.weld.junit5.auto.AddEnabledDecorators;
import org.jboss.weld.junit5.auto.AddPackages;
import org.jboss.weld.junit5.auto.EnableAutoWeld;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;

@EnableAutoWeld
@AddPackages(Car.class)
@AddEnabledDecorators(CarDecorator.class)
class DecoratorTest {

    @Inject
    Driver driver;

    @Test
    void decoratorTest() {
        String benny = driver.drive("Benny");
        System.out.println(benny);
    }
}
