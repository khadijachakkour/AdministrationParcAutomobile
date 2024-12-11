package com.example.maintenance.web;

import com.example.maintenance.entities.Maintenance_Cout;
import com.example.maintenance.service.MaintenanceCoutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/maintenance")
public class API {

    @Autowired
    private MaintenanceCoutService maintenanceCoutService;

    // Endpoint pour enregistrer une maintenance
    @PostMapping()
    public ResponseEntity<String> enregistrerMaintenance(@RequestBody Maintenance_Cout maintenance_cout) {
        Maintenance_Cout m=maintenanceCoutService.enregistrerMaintenance(maintenance_cout);
        return ResponseEntity.ok("Maintenance bien enregistre");
    }

    // Endpoint pour consulter le coût total des maintenances d'un véhicule
    @GetMapping("/cout/{id_vehicule}")
    public ResponseEntity<Float> consulterCouts(@PathVariable Long id_vehicule) {
        float totalCout = maintenanceCoutService.consulterCouts(id_vehicule);
        return ResponseEntity.ok(totalCout);
    }

    // Endpoint pour récupérer toutes les maintenances avec les informations des véhicules
    @GetMapping("/all-maintenance")
    public ResponseEntity<List<Maintenance_Cout>> getAllMaintenancesWithVehiculeInfo() {
        List<Maintenance_Cout> maintenanceCouts = maintenanceCoutService.GetALL_MaintenanceVehicules();
        return ResponseEntity.ok(maintenanceCouts);
    }
    // Endpoint pour récupérer une maintenance par ID
    @GetMapping("/{id}")
    public ResponseEntity<Maintenance_Cout> getMaintenanceById(@PathVariable Long id) {
        Maintenance_Cout m = maintenanceCoutService.getMaintenanceById(id);
        return ResponseEntity.ok(m);
    }


    //Endpoint pour supprimer un enregistrement d'une maintenance
    @DeleteMapping("{id}")
    public void deleteMaintenance(@PathVariable Long id){
        maintenanceCoutService.DeleteMaintenance(id);
    }


    //Endpoint pour Modifier un enregistrement du maintennace
    @PutMapping("{id}")
    public ResponseEntity<Maintenance_Cout> updateMaintenance(@PathVariable Long id, @RequestBody Maintenance_Cout maintenance_cout)
    {
        Maintenance_Cout m=maintenanceCoutService.Update_Maintenance(id,maintenance_cout);
        return ResponseEntity.ok(m);
    }


}
