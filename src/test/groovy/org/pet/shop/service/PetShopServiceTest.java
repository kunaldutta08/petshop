package org.pet.shop.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.pet.shop.dto.ParentsDTO;
import org.pet.shop.dto.PetDTO;
import org.pet.shop.entity.Parents;
import org.pet.shop.entity.Pet;
import org.pet.shop.repository.PetRepository;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class PetShopServiceTest {

    private PetDTO petDTO;
    private PetRepository petRepository;
    private PetShopService petShopService;

    @BeforeEach
    void setUp() {
        petDTO = PetDTO.builder()
                .id("123")
                .name("somePet")
                .color("black")
                .type("dog")
                .parentsDTO(ParentsDTO.builder()
                        .mother("loving mother")
                        .father("strict father")
                        .build())
                .build();

        petRepository = mock(PetRepository.class);
        petShopService = new PetShopService(petRepository);

    }

    @Test
    void shouldSaveAPet() {
        petShopService.addPet(petDTO);
        verify(petRepository).save(any());
    }

    @Test
    void shouldGetAllPets() {
        petShopService.getAllPets();
        verify(petRepository).findAll();
    }

    @Test
    void shouldUpdateAPet() {
        when(petRepository.findById(any())).thenReturn(Optional.of(Pet.builder()
                .id(123L)
                .name("somePet")
                .color("black")
                .type("dog")
                .parents(Parents.builder()
                        .mother("strict mother")
                        .father("strict father")
                        .build())
                .build()));

        PetDTO updatePet = petShopService.updatePet(petDTO, "123");

        Assertions.assertNotNull(updatePet);
        verify(petRepository).findById("123");
        verify(petRepository).save(any());
    }
}