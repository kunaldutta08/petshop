package org.pet.shop.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.pet.shop.dto.ParentsDTO;
import org.pet.shop.dto.PetDTO;
import org.pet.shop.service.PetShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
@ContextConfiguration(
        classes = {
                PetShopController.class
        }
)
class PetShopControllerTest {

    public static final String PETS_BASE_PATH = "/v1/pets";

    @MockBean
    private PetShopService petShopService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldCreatePet() throws Exception {
        String message = getIncomingPetAsJson();

        when(petShopService.addPet(any())).thenReturn(123L);

        MvcResult mvcResult = mockMvc.perform(post(PETS_BASE_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(message))
                .andExpect(status().isCreated())
                .andReturn();

        String actualResult = mvcResult.getResponse().getContentAsString();
        assertEquals("123", actualResult);
    }

    @Test
    void shouldGetAllPets() throws Exception {

        List<PetDTO> testPet = Collections.singletonList(getIncomingPet());

        when(petShopService.getAllPets()).thenReturn(testPet);

        MvcResult mvcResult = mockMvc.perform(get(PETS_BASE_PATH))
                .andExpect(status().isOk())
                .andReturn();

        String actualResult = mvcResult.getResponse().getContentAsString();

        Assertions.assertTrue(actualResult.contains("somePet"));
        Assertions.assertTrue(actualResult.contains("black"));
        Assertions.assertTrue(actualResult.contains("dog"));
        Assertions.assertTrue(actualResult.contains("loving mother"));
        Assertions.assertTrue(actualResult.contains("strict father"));
    }

    @Test
    void shouldUpdatePet() throws Exception {
        String petId = "123";
        PetDTO petDTO = getIncomingPet();
        petDTO.setName("newPet");
        petDTO.setId(petId);

        when(petShopService.updatePet(petDTO, petId)).thenReturn(petDTO);

        MvcResult mvcResult = mockMvc.perform(put(PETS_BASE_PATH + "/" + petId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(petDTO.toJson()))
                .andExpect(status().isOk()).andReturn();

        String actualResult = mvcResult.getResponse().getContentAsString();

        Assertions.assertTrue(actualResult.contains("newPet"));
        Assertions.assertTrue(actualResult.contains("123"));
    }

    private static PetDTO getIncomingPet() {
        return PetDTO.builder()
                .name("somePet")
                .color("black")
                .type("dog")
                .parentsDTO(ParentsDTO.builder()
                        .mother("loving mother")
                        .father("strict father")
                        .build())
                .build();
    }

    private String getIncomingPetAsJson() {
        Resource resource = new ClassPathResource("pet.json");
        try {
            File file = resource.getFile();
            String fullPath = file.getAbsolutePath();
            System.out.println("fullPath: " + fullPath);
            return Files.readString(Paths.get(fullPath));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}