package firstJPA.util;

import javax.persistence.AttributeConverter;
import java.sql.Time;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class LocalDateTimeConverter implements AttributeConverter<LocalTime, Time> {

    @Override
    public Time convertToDatabaseColumn(LocalTime locTime) {
        return Time.valueOf(locTime);
    }

    @Override
    public LocalTime convertToEntityAttribute(Time dbTime) {
        return dbTime.toLocalTime();
    }
}

