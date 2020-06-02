package basicCRUD.resources;

import basicCRUD.domain.Car;
import basicCRUD.services.CarService;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;


public class CarsResourceTest {

    // Not @Mock because it does not seem to work (just results in null).
    private final CarService carServiceMock = mock(CarService.class);
    private final Car carMock = mock(Car.class);

    private final CarsResource resource = new CarsResource();

    @Test
    public void whenGetByIdShouldReturnCar() {
        resource.setCarService(carServiceMock); // Apparently need to manually set this dependency (to override @Inject?)
        when(carServiceMock.getById(anyInt())).thenReturn(carMock);

        assertThat(resource.getById(1)).isEqualTo(carMock);

        verify(carServiceMock.getById(1));
    }
}
