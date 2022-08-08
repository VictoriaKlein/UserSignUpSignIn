package com.victoriaKlein.UserSignUpSignIn.repository;

import com.victoriaKlein.UserSignUpSignIn.model.Animal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface AnimalRepo extends JpaRepository<Animal, Long> {
    List<Animal> findAnimalsByUserId(Long tutorialId);

    @Transactional
    void deleteByUserId(Long userId);
}
