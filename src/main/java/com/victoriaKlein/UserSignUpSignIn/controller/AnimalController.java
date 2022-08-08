package com.victoriaKlein.UserSignUpSignIn.controller;

import com.victoriaKlein.UserSignUpSignIn.dto.AnimalResponse;
import com.victoriaKlein.UserSignUpSignIn.service.AnimalService;
import com.victoriaKlein.UserSignUpSignIn.service.UserService;
import com.victoriaKlein.UserSignUpSignIn.model.Animal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/")
public class AnimalController {
    @Autowired
    private final UserService userService;
    @Autowired
    private final AnimalService animalService;

    public AnimalController(UserService userService, AnimalService animalService) {
        this.userService = userService;
        this.animalService = animalService;
    }

    @GetMapping("/users/{userId}/animals")
    public ResponseEntity<List<Animal>> getAnimalsByUserId(@PathVariable(value = "userId") Long userId) {
        List<Animal> animals = animalService.getAnimalsIfUserExist(userId);
        return new ResponseEntity<>(animals, HttpStatus.OK);
    }

    @GetMapping("/users/{userId}/animals/{id}")
    public ResponseEntity<Animal> getAnimalByUserId(@PathVariable(value = "id") Long id) {
        Animal animal = animalService.getAnimalByUserId(id);
        return new ResponseEntity<>(animal, HttpStatus.OK);
    }

    @PostMapping("/users/{userId}/animals")
    public ResponseEntity<Animal> createAnimal(@PathVariable(value = "userId") Long userId,
                                               @RequestBody Animal animal) {
        Animal a = animalService.createAnimal(userId, animal);
        return new ResponseEntity<>(a, HttpStatus.CREATED);
    }

    @PutMapping("/users/{userId}/animals/{id}")
    public ResponseEntity<Animal> updateAnimal(@PathVariable("id") Long id, @RequestBody AnimalResponse animalResponse) {
        Animal a = animalService.updateAnimal(id, animalResponse);
        return new ResponseEntity<>(a, HttpStatus.OK);
    }

    @DeleteMapping("/users/{userId}/animals/{id}")
    public ResponseEntity<HttpStatus> deleteAnimal(@PathVariable("id") long id) {
        animalService.deleteAnimal(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/users/{userId}/animals")
    public ResponseEntity<List<Animal>> deleteAllAnimalsOfUser(@PathVariable(value = "userId") Long userId) {
        animalService.deleteAllAnimalsOfUser(userId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
