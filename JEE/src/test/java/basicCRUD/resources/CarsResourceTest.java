package basicCRUD.resources;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class CarsResourceTest {

    @Test
    public void name() {
        String message = "blabla";

        assertThat(message).contains("blabla");
    }
}
