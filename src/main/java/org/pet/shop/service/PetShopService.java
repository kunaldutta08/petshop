package org.pet.shop.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.pet.shop.dto.PetDTO;
import org.pet.shop.entity.Pet;
import org.pet.shop.repository.PetRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class PetShopService {

    private PetRepository petRepository;

    public Long addPet(PetDTO petDTO) {
        log.info("About to save -> " + petDTO.toString());
        Pet pet = Pet.from(petDTO);
        petRepository.save(pet);

        return pet.getId();
    }

    public List<PetDTO> getAllPets() {
        List<Pet> petList = petRepository.findAll();
        log.info("Retrieving pets from database -> " + petList);

        return petList.stream()
                .map(PetDTO::from)
                .toList();
    }

    public PetDTO updatePet(PetDTO petDTO, String petId) {
        log.info("About to edit pet with id -> " + petId);

        Optional<Pet> updatedPet = petRepository.findById(petId);

        if(updatedPet.isPresent()){
            Pet pet = updatedPet.get();
            pet.setName(petDTO.getName());
            pet.setType(petDTO.getType());
            pet.setColor(petDTO.getColor());
            pet.getParents().setMother(petDTO.getParentsDTO().getMother());
            pet.getParents().setFather(petDTO.getParentsDTO().getFather());
            petRepository.save(pet);
        } else {
            updatedPet = Optional.of(Pet.from(petDTO));
            petRepository.save(updatedPet.get());
        }

        return PetDTO.from(updatedPet.get());
    }

    public PetDTO updatePetBeta(PetDTO petDTO, String petId) {
        log.info("About to edit pet with id -> " + petId);

        Pet updatedPet = petRepository.findById(petId)
                .map(pet -> {
                    pet.setName(petDTO.getName());
                    pet.setType(petDTO.getType());
                    pet.setColor(petDTO.getColor());
                    pet.getParents().setMother(petDTO.getParentsDTO().getMother());
                    pet.getParents().setFather(petDTO.getParentsDTO().getFather());
                    return petRepository.save(pet);
                }).orElseGet(() -> {
                    petRepository.save(Pet.from(petDTO));
                    return Pet.from(petDTO);
                });

        return PetDTO.from(updatedPet);
    }
}
