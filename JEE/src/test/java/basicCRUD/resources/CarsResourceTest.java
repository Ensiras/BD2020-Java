package basicCRUD.resources;

import basicCRUD.domain.Car;
import basicCRUD.services.CarService;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CarsResourceTest {

    private final CarService carServiceMock = mock(CarService.class); // Not @Mock because it does not seem to work (just returns null).

    private final Car carMock = mock(Car.class);

//    @InjectMocks
    private CarsResource resource = new CarsResource();;

    @Test
    public void whenGetByIdShouldReturnCar() {
        resource.setCarService(carServiceMock); // Apparently needed to manually inject this dependency (to override @Inject?)
        when(carServiceMock.getById(anyInt())).thenReturn(carMock);

        assertThat(resource.getById(1)).isEqualTo(carMock);

        verify(carServiceMock.getById(1));
    }
}
