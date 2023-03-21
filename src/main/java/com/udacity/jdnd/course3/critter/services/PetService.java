package com.udacity.jdnd.course3.critter.services;

import com.udacity.jdnd.course3.critter.pet.Pet;
import com.udacity.jdnd.course3.critter.repository.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PetService {
    @Autowired
    PetRepository petRepository;

    public Pet createPet(Pet pet){
        return petRepository.save(pet);
    }

    public List<Pet> getAllPets(){
        return petRepository.findAll();
    }

    public List<Pet> getPetsByOwner(long ownerId){
        return petRepository.getAllByOwnerId(ownerId);
    }
    public Pet getPet(long petId){
        return petRepository.getOne(petId);
    }

    public List<Pet> findAllById(List<Long> petIds) {
        return petRepository.findAllById(petIds);
    }
}
