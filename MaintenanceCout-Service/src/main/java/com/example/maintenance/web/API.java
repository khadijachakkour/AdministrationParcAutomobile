package com.example.maintenance.web;

import com.example.maintenance.entities.Maintenance_Cout;
import com.example.maintenance.service.MaintenanceCoutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.Map;

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


    // Endpoint pour générer un rapport des maintenances et retourner le PDF
    @GetMapping("/rapport-pdf")
    public ResponseEntity<byte[]> genererRapportEnPdf() {
        try {
            // Appel à la méthode pour générer le rapport en PDF
            maintenanceCoutService.genererRapportEnPdf();

            // Charger le fichier PDF généré
            File file = new File("rapport_maintenance.pdf");
            byte[] fileContent = Files.readAllBytes(file.toPath());

            // Retourner le fichier PDF en réponse
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=rapport_maintenance.pdf")
                    .contentType(MediaType.APPLICATION_PDF)
                    .body(fileContent);

        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Endpoint pour générer un graph sur le cout des maintenances d'un vehicule
    @GetMapping("/graphique/{idVehicule}")
    public ResponseEntity<InputStreamResource> getGraph(@PathVariable Long idVehicule) throws IOException {
        List<Maintenance_Cout> maintenances = maintenanceCoutService.getHistoriqueByVehicule(idVehicule);
        String filePath = "maintenance_graph.png";

        // Génération du graphique
        maintenanceCoutService.generateGraph(maintenances, filePath);

        // Lecture du fichier généré
        File file = new File(filePath);
        InputStreamResource resource = new InputStreamResource(new FileInputStream(file));

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + file.getName())
                .contentType(MediaType.IMAGE_PNG)
                .contentLength(file.length())
                .body(resource);
    }

    @GetMapping("/statistiques")
    public Map<String, Double> obtenirStatistiques() {
        return maintenanceCoutService.obtenirStatistiquesGlobales();
    }
}
