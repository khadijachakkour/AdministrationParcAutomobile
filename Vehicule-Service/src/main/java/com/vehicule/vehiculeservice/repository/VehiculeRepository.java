package com.vehicule.vehiculeservice.repository;


import com.vehicule.vehiculeservice.entities.Vehicule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VehiculeRepository extends JpaRepository<Vehicule, Long> {

    List<Vehicule> findByStatut(Vehicule.Statut statut);

    Optional<Vehicule> findById(int id);

}
