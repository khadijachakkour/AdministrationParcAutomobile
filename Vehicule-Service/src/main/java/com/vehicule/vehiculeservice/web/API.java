package com.vehicule.vehiculeservice.web;

import com.vehicule.vehiculeservice.entities.Vehicule;
import com.vehicule.vehiculeservice.service.NotificationService;
import com.vehicule.vehiculeservice.service.VehiculeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/vehicules")
public class API {

    @Autowired
    private VehiculeService vehiculeService;

    @Autowired
    private NotificationService notificationService;



    // Route pour obtenir tous les véhicules
    @Operation(summary = "Get all vehicles", description = "Retrieve a list of all vehicles")
    @ApiResponse(responseCode = "200", description = "List of vehicles retrieved successfully")
    @ApiResponse(responseCode = "404", description = "List of vehicles not found")
    @GetMapping
    public List<Vehicule> getAllVehicles() {
        return vehiculeService.getAllVehicules();
    }


    // Route pour obtenir un véhicule par son ID
    @Operation(summary = "Get vehicle by ID", description = "Retrieve a vehicle by its ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Vehicle retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Vehicle not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Vehicule> getVehicleById(@PathVariable Long id) {
        Vehicule vehicule = vehiculeService.getVehiculeById(id);
        if (vehicule == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.ok(vehicule);

    }

    // Route pour créer un nouveau véhicule
    @Operation(summary = "Create a new vehicle", description = "Create a new vehicle entry")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Vehicle created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    @PostMapping
    public ResponseEntity<Vehicule> createVehicle(@RequestBody Vehicule vehicule) {
        try {
            Vehicule createdVehicule = vehiculeService.createVehicule(vehicule);
            // Envoyer une notification après l'enregistrement
            String message = "Nouveau véhicule enregistré : " + createdVehicule.getId();
            notificationService.sendUserNotification(message);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdVehicule);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    // Route pour mettre à jour un véhicule
    @Operation(summary = "Update vehicle", description = "Update an existing vehicle's details")
    @ApiResponse(responseCode = "200", description = "Vehicle updated successfully")
    @PutMapping("/{id}")
    public ResponseEntity<Vehicule> updateVehicule(@PathVariable Long id, @RequestBody Vehicule vehicule) {
        Vehicule updatedVehicule = vehiculeService.Update_Vehicule(id, vehicule);
        return ResponseEntity.ok(updatedVehicule);

    }


    // Route pour supprimer un véhicule
    @Operation(summary = "Delete vehicle", description = "Delete a vehicle by its ID")
    @ApiResponse(responseCode = "204", description = "Vehicle deleted successfully")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVehicle(@PathVariable Long id) {
        vehiculeService.deleteVehicule(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    // Route pour obtenir les véhicules par statut
    @Operation(summary = "Get vehicles by status", description = "Retrieve a list of vehicles filtered by status")
    @ApiResponse(responseCode = "200", description = "List of vehicles with the given status")
    @ApiResponse(responseCode = "404", description = "No vehicles found with the given status")
    @GetMapping("/statut/{statut}")
    public ResponseEntity<List<Vehicule>> getVehiclesByStatut(@PathVariable Vehicule.Statut statut) {
        List<Vehicule> vehicules = vehiculeService.getVehiculesByStatut(statut);

        if (vehicules.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(null);
        }

        return ResponseEntity.ok(vehicules);
    }


    // Route pour récupérer les véhicules disponibles
    @Operation(summary = "Get available vehicles", description = "Retrieve a list of vehicles that are available")
    @ApiResponse(responseCode = "200", description = "List of available vehicles retrieved successfully")
    @ApiResponse(responseCode = "404", description = "No available vehicles found")
    @GetMapping("/disponibles")
    public ResponseEntity<List<Vehicule>> getVehiculesDisponibles() {
            List<Vehicule> vehiculesDisponibles = vehiculeService.getVehiculesDisponibles();

            if (vehiculesDisponibles.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(null);
            }

            return ResponseEntity.ok(vehiculesDisponibles);
    }

    // Route pour mettre à jour le statut d'un véhicule
    @Operation(summary = "Update vehicle status", description = "Update the status of a specific vehicle")
    @ApiResponse(responseCode = "200", description = "Vehicle status updated successfully")
    @ApiResponse(responseCode = "404", description = "Vehicle not found")
    @PutMapping("/{id}/statut")
    public ResponseEntity<Vehicule> mettreAJourStatut(@PathVariable Long id, @RequestParam Vehicule.Statut statut) {
        Vehicule vehicule = vehiculeService.getVehiculeById(id);
        if (vehicule == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        Vehicule updatedVehicule = vehiculeService.mettreAJourStatut(id, statut);

        return ResponseEntity.ok(updatedVehicule);
    }

    // Route pour vérifier la disponibilité d'un véhicule
    @Operation(summary = "Check vehicle availability", description = "Check if a specific vehicle is available")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Availability checked successfully"),
            @ApiResponse(responseCode = "404", description = "Vehicle not found")
    })
    @GetMapping("/{id}/disponibilite")
    public ResponseEntity<Boolean> verifierDisponibilite(@PathVariable Long id) {
        try {
            Boolean disponible = vehiculeService.verifierDisponibilite(id);
            return ResponseEntity.ok(disponible);  // Retourne true ou false en fonction de la disponibilité
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    // Route pour obtenir des statistiques sur les véhicules
    @Operation(summary = "Get vehicle statistics", description = "Retrieve statistics related to the vehicles")
    @ApiResponse(responseCode = "200", description = "Statistics retrieved successfully")
    @GetMapping("/statistiques")
    public Map<String, Long> obtenirStatistiques() {
        return vehiculeService.obtenirStatistiques();
    }
}
