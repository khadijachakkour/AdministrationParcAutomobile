package com.example.maintenance.client;

import com.example.maintenance.module.Vehicule;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "vehicule-service", url = "http://localhost:8081")
public interface VehiculeRestFeign {

    @GetMapping("/api/v1/vehicules/{id}")
    Vehicule VehiculeById(@PathVariable("id") Long id);  // Appel pour récupérer les informations du véhicule par ID
}
