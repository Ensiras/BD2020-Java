package firstJPA.dao;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class BooleanTFConverter implements AttributeConverter<Boolean, String> {

    @Override
    public String convertToDatabaseColumn(Boolean attribute) {
        if (attribute) {
            return "T";
        } else {
            return "F";
        }
    }

    @Override
    public Boolean convertToEntityAttribute(String dbData) {
        if (dbData.equals("T")) {
            return true;
        } else {
            return false;
        }
    }
}
