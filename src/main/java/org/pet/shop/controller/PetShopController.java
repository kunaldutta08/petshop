package org.pet.shop.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.pet.shop.dto.PetDTO;
import org.pet.shop.service.PetShopService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.constraints.NotBlank;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/v1/pets")
@RequiredArgsConstructor
@Slf4j
public class PetShopController {

    private final PetShopService petShopService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> getAllPets() {
        log.info("Received GET request to get all existing pets");
        List<PetDTO> pets = petShopService.getAllPets();
        log.info("Returning all existing pets -> " + pets.toString());

        return ResponseEntity
                .ok()
                .body(pets);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> addPet(@RequestBody PetDTO petDTO, UriComponentsBuilder uriComponentsBuilder,
                                         HttpServletRequest httpRequest) {
        log.info("Received CREATE request to create pet -> " + petDTO.toString());
        Long petId = petShopService.addPet(petDTO);
        log.info("Created new pet successfully with Id -> " + petId);

        URI createdPetURI = uriComponentsBuilder
                .path(httpRequest.getRequestURI())
                .pathSegment(petId.toString())
                .buildAndExpand()
                .toUri();

        return ResponseEntity
                .created(createdPetURI)
                .body(petId);
    }

    @PutMapping(path = "/{petId}",
            produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> editPetById
            (@PathVariable @NotBlank(message = "Pet id cannot be empty") String petId,
             @RequestBody PetDTO petDTO) {
        log.info("Received UPDATE request to edit pet with id -> " + petId);

        PetDTO updatePet = petShopService.updatePet(petDTO, petId);

        log.info("Update successful for pet {} with id: {}", updatePet, petId);
        return ResponseEntity
                .ok()
                .body(updatePet);
    }

}
