package basicCRUD;

import basicCRUD.resources.CarsResource;
import com.github.phillipkruger.apiee.ApieeService;


import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@ApplicationPath("resources")
public class App extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        return new HashSet<>(Arrays.asList(
                CarsResource.class,
                // add more resources here...
                ApieeService.class
        ));
    }
}
