package com.vehicule.vehiculeservice.repository;


import com.vehicule.vehiculeservice.entities.Vehicule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface VehiculeRepository extends JpaRepository<Vehicule, Long> {

    List<Vehicule> findByStatut(Vehicule.Statut statut);

}


