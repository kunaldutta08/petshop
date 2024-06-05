package org.pet.shop.entity;

import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.pet.shop.converter.ParentsToJson;
import org.pet.shop.dto.PetDTO;

@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Pet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String type;
    private String color;
    @Convert(converter = ParentsToJson.class)
    private Parents parents;

    public static Pet from(PetDTO petDTO) {

        return Pet.builder()
                .name(petDTO.getName())
                .type(petDTO.getType())
                .color(petDTO.getColor())
                .parents(Parents.builder()
                        .mother(petDTO.getParentsDTO().getMother())
                        .father(petDTO.getParentsDTO().getFather())
                        .build())
                .build();
    }

}
