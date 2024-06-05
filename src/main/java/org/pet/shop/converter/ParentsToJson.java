package org.pet.shop.converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import lombok.extern.slf4j.Slf4j;
import org.pet.shop.entity.Parents;

import java.io.IOException;

@Slf4j
@Converter(autoApply = true)
public class ParentsToJson implements AttributeConverter<Parents, String> {

    private final static ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(Parents meta) {
        try {
            return objectMapper.writeValueAsString(meta);
        } catch (JsonProcessingException e) {
            log.error("Unexpected error while encoding json: " + e.getMessage(), e);
            return null;
        }
    }

    @Override
    public Parents convertToEntityAttribute(String dbData) {
        try {
            return objectMapper.readValue(dbData, Parents.class);
        } catch (IOException e) {
            log.error("Unexpected IOEx decoding json from database: " + dbData, e);
            return null;
        }
    }
}
