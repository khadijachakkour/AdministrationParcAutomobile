package com.example.maintenance.web;

import com.example.maintenance.entities.Maintenance_Cout;
import com.example.maintenance.module.Vehicule;
import com.example.maintenance.service.MaintenanceCoutService;
import com.example.maintenance.service.NotificationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Maintenance API", description = "Endpoints pour gérer les maintenances et les coûts associés")
public class API {

    @Autowired
    private MaintenanceCoutService maintenanceCoutService;

    @Autowired
    NotificationService notificationService;

    // Endpoint pour enregistrer une maintenance
    @Operation(summary = "Enregistrer une maintenance", description = "Ajoute une nouvelle maintenance pour un véhicule donné.")
    @ApiResponse(responseCode = "200", description = "Maintenance enregistrée avec succès.")
    @PostMapping
    public Maintenance_Cout enregistrerMaintenance(@RequestBody Maintenance_Cout maintenance) {
        return maintenanceCoutService.enregistrerMaintenance(maintenance);
    }
    // Endpoint pour consulter le coût total des maintenances d'un véhicule
    @Operation(summary = "Consulter le coût total des maintenances", description = "Renvoie le coût total des maintenances pour un véhicule spécifique.")
    @ApiResponse(responseCode = "200", description = "Coût total des maintenances récupéré avec succès.")
    @GetMapping("/cout/{id_vehicule}")
    public ResponseEntity<Float> consulterCouts(@PathVariable Long id_vehicule) {
        float totalCout = maintenanceCoutService.consulterCouts(id_vehicule);
        return ResponseEntity.ok(totalCout);
    }

    // Endpoint pour récupérer toutes les maintenances avec les informations des véhicules
    @Operation(summary = "Lister toutes les maintenances avec informations des véhicules", description = "Récupère toutes les maintenances avec les détails des véhicules associés.")
    @ApiResponse(responseCode = "200", description = "Liste des maintenances récupérée avec succès.")
    @GetMapping("/all-maintenance")
    public ResponseEntity<List<Maintenance_Cout>> getAllMaintenancesWithVehiculeInfo() {
        List<Maintenance_Cout> maintenanceCouts = maintenanceCoutService.GetALL_MaintenanceVehicules();
        return ResponseEntity.ok(maintenanceCouts);
    }

    // Endpoint pour récupérer une maintenance par ID
    @Operation(summary = "Récupérer une maintenance par ID", description = "Renvoie les détails d'une maintenance spécifique.")
    @ApiResponse(responseCode = "200", description = "Détails de la maintenance récupérés avec succès.")
    @GetMapping("/{id}")
    public ResponseEntity<Maintenance_Cout> getMaintenanceById(@PathVariable Long id) {
        Maintenance_Cout m = maintenanceCoutService.getMaintenanceById(id);
        return ResponseEntity.ok(m);
    }


    //Endpoint pour supprimer un enregistrement d'une maintenance
    @Operation(summary = "Supprimer une maintenance", description = "Supprime une maintenance donnée par ID.")
    @ApiResponse(responseCode = "200", description = "Maintenance supprimée avec succès.")
    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteMaintenance(@PathVariable Long id){

        maintenanceCoutService.DeleteMaintenance(id);
        // Envoyer une notification
        String message = "Maintenance supprimée avec l'ID : " + id;
        notificationService.sendUserNotification(message);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }


    //Endpoint pour Modifier un enregistrement du maintennace
    @Operation(summary = "Modifier une maintenance", description = "Met à jour les informations d'une maintenance spécifique.")
    @ApiResponse(responseCode = "200", description = "Maintenance mise à jour avec succès.")
    @PutMapping("{id}")
    public ResponseEntity<Maintenance_Cout> updateMaintenance(@PathVariable Long id, @RequestBody Maintenance_Cout maintenance_cout)
    {
        Maintenance_Cout m=maintenanceCoutService.Update_Maintenance(id,maintenance_cout);
        // Envoyer une notification
        String message = "Maintenance mise à jour pour l'ID : " + id;
        notificationService.sendUserNotification(message);
        return ResponseEntity.ok(m);
    }


    // Endpoint pour générer un rapport des maintenances et retourner le PDF
    @Operation(summary = "Générer un rapport PDF", description = "Génère un rapport en format PDF pour les maintenances.")
    @ApiResponse(responseCode = "200", description = "Rapport généré avec succès.")
    @GetMapping("/rapport-pdf")
    public ResponseEntity<byte[]> genererRapportEnPdf() {
        try {
            // Appel à la méthode pour générer le rapport en PDF
            maintenanceCoutService.genererRapportEnPdf();

            // Charger le fichier PDF généré
            File file = new File("rapport_maintenance.pdf");
            byte[] fileContent = Files.readAllBytes(file.toPath());
            // Envoyer une notification
            notificationService.sendUserNotification("Le rapport des maintenances a été généré.");
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
    @Operation(summary = "Générer un graphique des coûts de maintenance", description = "Génère un graphique des coûts de maintenance pour un véhicule donné.")
    @ApiResponse(responseCode = "200", description = "Graphique généré avec succès.")
    @GetMapping("/graphique/{idVehicule}")
    public ResponseEntity<InputStreamResource> getGraph(@PathVariable Long idVehicule) throws IOException {
        List<Maintenance_Cout> maintenances = maintenanceCoutService.getHistoriqueByVehicule(idVehicule);
        String filePath = "maintenance_graph.png";

        // Génération du graphique
        maintenanceCoutService.generateGraph(maintenances, filePath);

        // Lecture du fichier généré
        File file = new File(filePath);
        InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
        // Envoyer une notification
        notificationService.sendUserNotification("Graphique généré pour le véhicule ID : " + idVehicule);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + file.getName())
                .contentType(MediaType.IMAGE_PNG)
                .contentLength(file.length())
                .body(resource);
    }

    @Operation(summary = "Obtenir des statistiques globales", description = "Récupère des statistiques globales des maintenances.")
    @ApiResponse(responseCode = "200", description = "Statistiques récupérées avec succès.")
    @GetMapping("/statistiques")
    public Map<String, Double> obtenirStatistiques() {
        return maintenanceCoutService.obtenirStatistiquesGlobales();
    }


    //Endpoint pour recuperer la liste de véhicules triés par leur coût total de maintenance en ordre décroissant
    @Operation(summary = "Lister les véhicules les plus coûteux", description = "Récupère une liste des véhicules triés par leur coût total de maintenance en ordre décroissant.")
    @ApiResponse(responseCode = "200", description = "Liste des véhicules récupérée avec succès.")
    @GetMapping("/vehicules/top-maintenance")
    public ResponseEntity<List<Vehicule>> vehiculesLesPlusCouteux() {
        List<Vehicule> topVehicules = maintenanceCoutService.vehiculesLesPlusCouteux();
        return ResponseEntity.ok(topVehicules);
    }
}
