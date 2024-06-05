package org.pet.shop.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.pet.shop.entity.Pet;

@Data
@Builder
@Slf4j
public class PetDTO {

    private String id;
    private String name;
    private String type;
    private String color;
    @JsonProperty("parents")
    private ParentsDTO parentsDTO;

    public static PetDTO from(Pet pet) {

        return PetDTO.builder()
                .id(pet.getId().toString())
                .name(pet.getName())
                .type(pet.getType())
                .color(pet.getColor())
                .parentsDTO(ParentsDTO.builder()
                        .mother(pet.getParents().getMother())
                        .father(pet.getParents().getFather())
                        .build())
                .build();
    }

    public String toJson(){
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            log.error("return empty string, because can't convert to json. " + this, e);
            throw new RuntimeException(e);
        }
    }
}
