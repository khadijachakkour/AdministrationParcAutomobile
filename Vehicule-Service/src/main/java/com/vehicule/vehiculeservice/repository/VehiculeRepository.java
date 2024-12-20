package com.vehicule.vehiculeservice.repository;


import com.vehicule.vehiculeservice.entities.Vehicule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface VehiculeRepository extends JpaRepository<Vehicule, Long> {

    // Méthode générique de recherche
    List<Vehicule> findByStatut(Vehicule.Statut statut);
    List<Vehicule> findByMarque(String marque);

    List<Vehicule> findByModele(String modele);

    List<Vehicule> findByTypeVehicule(String typeVehicule);


    List<Vehicule> findByDateAchat(LocalDate dateAchat);

    List<Vehicule> findByDateDerniereMaintenance(LocalDate dateDerniereMaintenance);

    List<Vehicule> findByCouleur(String couleur);
}


