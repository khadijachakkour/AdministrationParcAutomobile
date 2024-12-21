package com.vehicule.vehiculeservice.repository;

import com.vehicule.vehiculeservice.entities.Vehicule;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import java.time.LocalDate;
import java.util.List;


@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)  // Utilise MySQL au lieu d'une base embarquée
class VehiculeRepositoryTest {

    @Autowired
    private VehiculeRepository vehiculeRepository;

    @BeforeEach
    void setUp() {
        vehiculeRepository.save(new Vehicule(null, "Toyota", "Corolla", "Voiture", "Bleu", LocalDate.of(2020, 5, 20), LocalDate.of(2023, 3, 15), Vehicule.Statut.DISPONIBLE));
        vehiculeRepository.save(new Vehicule(null, "Honda", "Civic", "Voiture", "Noir", LocalDate.of(2018, 8, 10), LocalDate.of(2022, 7, 10), Vehicule.Statut.RESERVE));
    }

    @Test
    void findByMarque() {
        String marque = "Toyota";

        List<Vehicule> result = vehiculeRepository.findByMarque(marque);

        Assertions.assertThat(result).isNotNull();

        Assertions.assertThat(result).isNotEmpty();

        Assertions.assertThat(result).extracting(Vehicule::getMarque).containsOnly(marque);

    }

    @Test
    void findByStatut() {
        Vehicule.Statut statut = Vehicule.Statut.DISPONIBLE;

        List<Vehicule> result = vehiculeRepository.findByStatut(statut);

        Assertions.assertThat(result).isNotNull();

        Assertions.assertThat(result).isNotEmpty();

        // Vérifie que tous les véhicules ont le statut "DISPONIBLE"
        Assertions.assertThat(result).extracting(Vehicule::getStatut).containsOnly(statut);

    }

    @Test
    void notFindByMarque() {
        String marque = "BMW";

        List<Vehicule> result = vehiculeRepository.findByMarque(marque);

        Assertions.assertThat(result).isNotNull();

        Assertions.assertThat(result).isEmpty();

    }
}
