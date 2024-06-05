package org.pet.shop.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ParentsDTO {

    private String mother;
    private String father;
}
