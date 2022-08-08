package com.victoriaKlein.UserSignUpSignIn.service;

import com.victoriaKlein.UserSignUpSignIn.dto.AnimalResponse;
import com.victoriaKlein.UserSignUpSignIn.model.Animal;
import com.victoriaKlein.UserSignUpSignIn.repository.AnimalRepo;
import com.victoriaKlein.UserSignUpSignIn.repository.UserRepo;
import com.victoriaKlein.UserSignUpSignIn.repository.UserRepoCustom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class AnimalService {
    @Autowired
    private final UserRepo userRepo;
    @Autowired
    private final UserRepoCustom userRepoCustom;
    @Autowired
    private final AnimalRepo animalRepo;

    public AnimalService(UserRepo userRepo, UserRepoCustom userRepoCustom, AnimalRepo animalRepo) {
        this.userRepoCustom = userRepoCustom;
        this.userRepo = userRepo;
        this.animalRepo = animalRepo;
    }

    public List<Animal> getAnimalsIfUserExist(Long userId) {
        if (!userRepo.existsById(userId)) {
            throw new NoSuchElementException("Not found user with id = " + userId);
        }
        return animalRepo.findAnimalsByUserId(userId);
    }

    public Animal getAnimalByUserId(Long id) {
        System.out.println("animal id " + id);
        Animal animal = animalRepo.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Not found animal with id = " + id));
        return animal;
    }

    public Animal createAnimal(Long userId, Animal animal) {
        Animal a = userRepo.findById(userId).map(user -> {
            animal.setUser(user);
            return animalRepo.save(animal);
        }).orElseThrow(() -> new NoSuchElementException("Not found user with id = " + userId));
        return a;
    }

    public Animal updateAnimal(Long id, AnimalResponse animalResponse) {
        Animal a = animalRepo.findById(id)
                .orElseThrow(() -> new NoSuchElementException("animalId " + id + "not found"));
        a.setSpecies(animalResponse.getSpecies());
        a.setAnimalName(animalResponse.getAnimalName());
        return animalRepo.save(a);
    }

    public void deleteAnimal(Long id) {
        animalRepo.deleteById(id);
    }

    public void deleteAllAnimalsOfUser(Long userId) {
        if (!userRepo.existsById(userId)) {
            throw new NoSuchElementException("Not found user with id = " + userId);
        }
        animalRepo.deleteByUserId(userId);
    }
}
