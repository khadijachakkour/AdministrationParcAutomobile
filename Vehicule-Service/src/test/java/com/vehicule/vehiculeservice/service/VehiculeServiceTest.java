package com.vehicule.vehiculeservice.service;

import com.vehicule.vehiculeservice.entities.Vehicule;
import com.vehicule.vehiculeservice.repository.VehiculeRepository;
import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@ExtendWith(MockitoExtension.class)
class VehiculeServiceTest {

    @Mock
    private VehiculeRepository vehiculeRepository;

    @InjectMocks
    private VehiculeService vehiculeService;

    @Test
    void create_Vehicule() {
        Vehicule vehicule = new Vehicule(null, "Marque1", "Modele1", "SUV", "Rouge", LocalDate.now(), LocalDate.now(), Vehicule.Statut.DISPONIBLE);
        Vehicule vehiculeSaved = new Vehicule(1L, "Marque1", "Modele1", "SUV", "Rouge", LocalDate.now(), LocalDate.now(), Vehicule.Statut.DISPONIBLE);

        Mockito.when(vehiculeRepository.save(vehicule)).thenReturn(vehiculeSaved);

        Vehicule result = vehiculeService.createVehicule(vehicule);

        AssertionsForClassTypes.assertThat(result).isNotNull();
        AssertionsForClassTypes.assertThat(result).usingRecursiveComparison().isEqualTo(vehiculeSaved);
    }

    @Test
    void getAll_Vehicules() {
        List<Vehicule> vehiculesList = List.of(
                new Vehicule(1L, "Marque1", "Modele1", "SUV", "Rouge", LocalDate.now(), LocalDate.now(), Vehicule.Statut.DISPONIBLE),
                new Vehicule(2L, "Marque2", "Modele2", "Berline", "Noir", LocalDate.now(), LocalDate.now(), Vehicule.Statut.EN_MAINTENANCE)
        );

        Mockito.when(vehiculeRepository.findAll()).thenReturn(vehiculesList);

        List<Vehicule> result = vehiculeService.getAllVehicules();

        AssertionsForClassTypes.assertThat(result).isNotNull();
        AssertionsForClassTypes.assertThat(result).usingRecursiveComparison().isEqualTo(vehiculesList);
    }

    @Test
    void get_VehiculeById() {
        Long id = 1L;
        Vehicule vehicule = new Vehicule(1L, "Marque1", "Modele1", "SUV", "Rouge", LocalDate.now(), LocalDate.now(), Vehicule.Statut.DISPONIBLE);

        Mockito.when(vehiculeRepository.findById(id)).thenReturn(Optional.of(vehicule));

        Vehicule result = vehiculeService.getVehiculeById(id);

        AssertionsForClassTypes.assertThat(result).isNotNull();
        AssertionsForClassTypes.assertThat(result).usingRecursiveComparison().isEqualTo(vehicule);
    }
    @Test

    void updateVehicule() {
        Long id = 1L;
        Vehicule vehicule = new Vehicule(1L, "Marque1", "Modele1", "SUV", "Rouge", LocalDate.now(), LocalDate.now(), Vehicule.Statut.DISPONIBLE);
        Vehicule updatedVehicule = new Vehicule(1L, "Marque1", "Modele1", "SUV", "Bleu", LocalDate.now(), LocalDate.now(), Vehicule.Statut.RESERVE);

        Mockito.when(vehiculeRepository.findById(id)).thenReturn(Optional.of(vehicule));
        Mockito.when(vehiculeRepository.save(vehicule)).thenReturn(updatedVehicule);

        Vehicule result = vehiculeService.UpdateVehicule(id, updatedVehicule);

        AssertionsForClassTypes.assertThat(result).isNotNull();
        AssertionsForClassTypes.assertThat(result).usingRecursiveComparison().isEqualTo(updatedVehicule);
    }

    @Test
    void delete_Vehicule() {
        Long id = 1L;
        Vehicule vehicule = new Vehicule(1L, "Marque1", "Modele1", "SUV", "Rouge", LocalDate.now(), LocalDate.now(), Vehicule.Statut.DISPONIBLE);

        Mockito.when(vehiculeRepository.findById(id)).thenReturn(Optional.of(vehicule));

        vehiculeService.deleteVehicule(id);

    }

    @Test
    void get_VehiculesByStatut() {
        Vehicule vehicule1 = new Vehicule(1L, "Marque1", "Modele1", "SUV", "Rouge", LocalDate.now(), LocalDate.now(), Vehicule.Statut.DISPONIBLE);
        Vehicule vehicule2 = new Vehicule(2L, "Marque2", "Modele2", "Berline", "Noir", LocalDate.now(), LocalDate.now(), Vehicule.Statut.DISPONIBLE);

        List<Vehicule> vehiculesList = List.of(vehicule1, vehicule2);

        Mockito.when(vehiculeRepository.findByStatut(Vehicule.Statut.DISPONIBLE)).thenReturn(vehiculesList);

        List<Vehicule> result = vehiculeService.getVehiculesByStatut(Vehicule.Statut.DISPONIBLE);

        AssertionsForClassTypes.assertThat(result).isNotNull();
        AssertionsForClassTypes.assertThat(result).usingRecursiveComparison().isEqualTo(vehiculesList);
    }

    @Test
    void get_Statistiques() {
        Mockito.when(vehiculeRepository.count()).thenReturn(5L);
        Mockito.when(vehiculeRepository.findByStatut(Vehicule.Statut.DISPONIBLE)).thenReturn(List.of(new Vehicule(), new Vehicule()));
        Mockito.when(vehiculeRepository.findByStatut(Vehicule.Statut.EN_MAINTENANCE)).thenReturn(List.of(new Vehicule()));
        Mockito.when(vehiculeRepository.findByStatut(Vehicule.Statut.RESERVE)).thenReturn(List.of(new Vehicule(), new Vehicule()));

        Map<String, Long> expectedStats = Map.of(
                "total", 5L,
                "disponibles", 2L,
                "enMaintenance", 1L,
                "reservés", 2L
        );

        Map<String, Long> result = vehiculeService.obtenirStatistiques();

        AssertionsForClassTypes.assertThat(result).isNotNull();
        AssertionsForClassTypes.assertThat(result).usingRecursiveComparison().isEqualTo(expectedStats);
    }

    @Test
    void filter_Vehicules() {
        Vehicule vehicule = new Vehicule(1L, "Marque1", "Modele1", "SUV", "Rouge", LocalDate.now(), LocalDate.now(), Vehicule.Statut.DISPONIBLE);

        Mockito.when(vehiculeRepository.findByMarque("Marque1")).thenReturn(List.of(vehicule));

        List<Vehicule> result = vehiculeService.filterVehicles("Marque1", null, null, null, null, null, null);

        AssertionsForClassTypes.assertThat(result).isNotNull();
        AssertionsForClassTypes.assertThat(result).usingRecursiveComparison().isEqualTo(List.of(vehicule));
    }

    @Test
    void updateVehiculeStatusTest() {

        Long id = 1L;
        Vehicule.Statut nouveauStatut = Vehicule.Statut.DISPONIBLE;

        Vehicule vehiculeExistant = new Vehicule();
        vehiculeExistant.setId(id);
        vehiculeExistant.setStatut(Vehicule.Statut.RESERVE);

        Mockito.when(vehiculeRepository.findById(id)).thenReturn(Optional.of(vehiculeExistant));
        Mockito.when(vehiculeRepository.save(Mockito.any(Vehicule.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        Vehicule resultat = vehiculeService.mettreAJourStatut(id, nouveauStatut);

        // Assert
        Assertions.assertNotNull(resultat, "Le résultat ne doit pas être null.");
        Assertions.assertEquals(nouveauStatut, resultat.getStatut(), "Le statut doit être mis à jour.");
        Assertions.assertEquals(id, resultat.getId(), "L'ID du véhicule doit rester inchangé.");

        // Vérification des interactions
        Mockito.verify(vehiculeRepository, Mockito.times(1)).findById(id);
        Mockito.verify(vehiculeRepository, Mockito.times(1)).save(vehiculeExistant);
        Mockito.verifyNoMoreInteractions(vehiculeRepository);
    }

    @Test
    void verifierDisponibilite_vehiculeDisponible_retourneVrai() {
        // Arrange
        Long id = 1L;
        Vehicule vehiculeExistant = new Vehicule();
        vehiculeExistant.setId(id);
        vehiculeExistant.setStatut(Vehicule.Statut.DISPONIBLE);

        Mockito.when(vehiculeRepository.findById(id)).thenReturn(Optional.of(vehiculeExistant));

        // Act
        Boolean resultat = vehiculeService.verifierDisponibilite(id);

        // Assert
        Assertions.assertTrue(resultat, "La méthode doit retourner vrai si le véhicule est disponible.");
        Mockito.verify(vehiculeRepository, Mockito.times(1)).findById(id);
        Mockito.verifyNoMoreInteractions(vehiculeRepository);
    }

    @Test
    void verifierDisponibilite_vehiculeIndisponible_retourneFaux() {
        // Arrange
        Long id = 2L;
        Vehicule vehiculeExistant = new Vehicule();
        vehiculeExistant.setId(id);
        vehiculeExistant.setStatut(Vehicule.Statut.RESERVE);

        Mockito.when(vehiculeRepository.findById(id)).thenReturn(Optional.of(vehiculeExistant));

        // Act
        Boolean resultat = vehiculeService.verifierDisponibilite(id);

        // Assert
        Assertions.assertFalse(resultat, "La méthode doit retourner faux si le véhicule est indisponible.");
        Mockito.verify(vehiculeRepository, Mockito.times(1)).findById(id);
        Mockito.verifyNoMoreInteractions(vehiculeRepository);
    }


    @Test
    void GetPaginatedVehiclesTest() {
        // Arrange
        Pageable pageable = PageRequest.of(0, 2);
        List<Vehicule> vehicules = List.of(
               new Vehicule(1L, "Marque1", "Modele1", "SUV", "Rouge", LocalDate.now(), LocalDate.now(), Vehicule.Statut.DISPONIBLE),
               new Vehicule(1L, "Marque2", "Modele2", "SUV", "Jaune", LocalDate.now(), LocalDate.now(), Vehicule.Statut.RESERVE));

        Page<Vehicule> vehiculePage = new PageImpl<>(vehicules, pageable, vehicules.size());

        Mockito.when(vehiculeRepository.findAll(pageable)).thenReturn(vehiculePage);

        // Act
        Page<Vehicule> result = vehiculeService.getPaginatedVehicles(pageable);

        // Assert
        Assertions.assertNotNull(result);
        Assertions.assertEquals(2, result.getContent().size());
        Assertions.assertEquals(vehicules.get(0).getId(), result.getContent().get(0).getId());
        Assertions.assertEquals(vehicules.get(1).getId(), result.getContent().get(1).getId());

        Mockito.verify(vehiculeRepository, Mockito.times(1)).findAll(pageable);
    }

}
