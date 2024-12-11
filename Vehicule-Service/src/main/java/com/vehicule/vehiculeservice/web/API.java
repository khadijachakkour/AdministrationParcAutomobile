package com.vehicule.vehiculeservice.web;


import com.vehicule.vehiculeservice.entities.Vehicule;
import com.vehicule.vehiculeservice.service.VehiculeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vehicules")
public class API {

    @Autowired
    private VehiculeService vehiculeService;



    // Route pour obtenir tous les véhicules
    @GetMapping
    public List<Vehicule> getAllVehicules() {
        return vehiculeService.getAllVehicules();
    }

    // Route pour obtenir un véhicule par son ID
    @GetMapping("/{id}")
    public ResponseEntity<Vehicule> getVehiculeById(@PathVariable Long id) {
        Vehicule vehicule = vehiculeService.getVehiculeById(id);
        return ResponseEntity.ok(vehicule);

    }


    // Route pour créer un nouveau véhicule
    @PostMapping
    public ResponseEntity<Vehicule> createVehicule(@RequestBody Vehicule vehicule) {
        Vehicule createdVehicule = vehiculeService.createVehicule(vehicule);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdVehicule);
    }

    // Route pour mettre à jour un véhicule existant
    @PutMapping("/{id}")
    public ResponseEntity<Vehicule> updateVehicule(@PathVariable Long id, @RequestBody Vehicule vehicule) {
        Vehicule updatedVehicule = vehiculeService.Update_Vehicule(id, vehicule);
        return ResponseEntity.ok(updatedVehicule);

    }


    // Route pour supprimer un véhicule
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVehicule(@PathVariable Long id) {
        vehiculeService.deleteVehicule(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    // Route pour obtenir les véhicules par statut
    @GetMapping("/statut/{statut}")
    public List<Vehicule> getVehiculesByStatut(@PathVariable Vehicule.Statut statut) {
        return vehiculeService.getVehiculesByStatut(statut);
    }
}
