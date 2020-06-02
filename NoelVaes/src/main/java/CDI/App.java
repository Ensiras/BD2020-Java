package CDI;

import org.jboss.weld.environment.se.Weld;
import org.jboss.weld.environment.se.WeldContainer;

// Example of using @Alternative bean for injection (see beans.xml)
public class App {
    public static void main(String[] args) {
        Weld weld = new Weld();
        WeldContainer container = weld.initialize();

        Cleaner cleaner = container.select(Cleaner.class).get();

        cleaner.clean();
    }
}
