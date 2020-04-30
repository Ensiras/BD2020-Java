package JPA.validation;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.*;
import java.util.Calendar;

import static javax.persistence.TemporalType.DATE;

// Validation
@Entity
public class Car {

    // Regex pattern
    @Id
    @Pattern(regexp = "^[0-9]-[A-Z]{3}-[0-9]{3}$")
    private String plate;

    // Required, minimum length 3.
    @NotNull
    @Size(min = 3)
    private String brand;

    @NotNull
    @Size(min = 2)
    private String type;

    // Force date type
    @Past
    @Temporal(DATE)
    private Calendar entryIntoService;

    @Min(0)
    private int power;

    // Decimal not negative, max. 10 digits before decimal point, 2 decimals
    @DecimalMin("0")
    @Digits(integer = 10, fraction = 2)
    private double listPrice;

    public Car() {
    }

    public void setPlate(String plate) {
        this.plate = plate;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setEntryIntoService(Calendar entryIntoService) {
        this.entryIntoService = entryIntoService;
    }

    public void setPower(int power) {
        this.power = power;
    }

    public void setListPrice(double listPrice) {
        this.listPrice = listPrice;
    }
}
