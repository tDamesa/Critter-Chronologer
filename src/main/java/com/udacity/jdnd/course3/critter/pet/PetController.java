package com.udacity.jdnd.course3.critter.pet;

import com.udacity.jdnd.course3.critter.repository.CustomerRepository;
import com.udacity.jdnd.course3.critter.services.CustomerService;
import com.udacity.jdnd.course3.critter.services.PetService;
import com.udacity.jdnd.course3.critter.user.Customer;
import com.udacity.jdnd.course3.critter.user.CustomerDTO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Handles web requests related to Pets.
 */
@RestController
@RequestMapping("/pet")
public class PetController {
    @Autowired
    PetService petService;

    @Autowired
    CustomerService customerService;

    @PostMapping
    public PetDTO savePet(@RequestBody PetDTO petDTO) {
        long ownerId = petDTO.getOwnerId();
        Customer customer = customerService.getCustomer(ownerId);
        Pet pet = petService.createPet(convertPetDTOToEntity(petDTO, customer));
        customer.addPet(pet);
        customerService.saveCustomer(customer);
        return convertEntityToPetDTO(pet, ownerId);
    }

    @GetMapping("/{petId}")
    public PetDTO getPet(@PathVariable long petId) {
        Pet pet = petService.getPet(petId);
        return convertEntityToPetDTO(pet, pet.getOwner().getId());
    }

    @GetMapping
    public List<PetDTO> getPets(){
        List<Pet> pets = petService.getAllPets();
        return pets.stream().map(pet -> convertEntityToPetDTO(pet, pet.getOwner().getId())).collect(Collectors.toList());
    }

    @GetMapping("/owner/{ownerId}")
    public List<PetDTO> getPetsByOwner(@PathVariable long ownerId) {
        List<Pet> pets = petService.getPetsByOwner(ownerId);
        return pets.stream().map(pet -> convertEntityToPetDTO(pet,ownerId)).collect(Collectors.toList());
    }

    private PetDTO convertEntityToPetDTO(Pet pet, long ownerId){
        PetDTO petDTO = new PetDTO();
        petDTO.setOwnerId(ownerId);
        BeanUtils.copyProperties(pet, petDTO);
        return petDTO;
    }

    private Pet convertPetDTOToEntity(PetDTO petDTO, Customer owner){
        Pet pet = new Pet();
        pet.setOwner(owner);
        BeanUtils.copyProperties(petDTO, pet);
        return pet;
    }
}
