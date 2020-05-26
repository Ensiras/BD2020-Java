package helloRest;

import com.github.phillipkruger.apiee.ApieeService;
import helloRest.resources.HelloWorldResource;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/* Note that path needs to be different here since I have two App classes. Pointing towards the same path breaks
* Swagger (another option is to load all the resources in one App class) */
@ApplicationPath("hello-resources")
public class App extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        return new HashSet<>(Arrays.asList(
                HelloWorldResource.class,
                // add more resources here...
                ApieeService.class
        ));
    }
}
