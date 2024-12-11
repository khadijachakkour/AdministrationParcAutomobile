package com.example.maintenance.repository;

import com.example.maintenance.entities.Maintenance_Cout;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import java.util.List;

public interface MaintenanceCoutRepository extends JpaRepository<Maintenance_Cout, Long> {

    //Récupérer toutes les maintenances associées à un véhicule
    @Query("SELECT m FROM Maintenance_Cout m WHERE m.id_vehicule = :idVehicule")
    List<Maintenance_Cout> findByIdVehicule(@Param("idVehicule") Long idVehicule);



}


