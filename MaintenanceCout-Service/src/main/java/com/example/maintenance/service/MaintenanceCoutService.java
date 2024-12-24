package com.example.maintenance.service;

import com.example.maintenance.client.VehiculeRestFeign;
import com.example.maintenance.entities.Maintenance_Cout;
import com.example.maintenance.module.Vehicule;
import com.example.maintenance.repository.MaintenanceCoutRepository;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.TextAlignment;
import feign.FeignException;
import jakarta.persistence.EntityNotFoundException;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@Service
public class MaintenanceCoutService {

    @Autowired
    private MaintenanceCoutRepository maintenanceCoutRepository;
    @Autowired
    private VehiculeRestFeign vehiculeRestFeign;
    public Maintenance_Cout enregistrerMaintenance(Maintenance_Cout maintenance) {
        vehiculeRestFeign.updateStatut(maintenance.getId_vehicule(), Vehicule.Statut.EN_MAINTENANCE);

        // Enregistrer la maintenance
        return maintenanceCoutRepository.save(maintenance);
    }


    // Liste des maintenances par vehicule
    public List<Maintenance_Cout> getHistoriqueByVehicule(Long idVehicule) {
        return maintenanceCoutRepository.findByIdVehicule(idVehicule);
    }

    // Méthode pour consulter les coûts des maintenances d'un véhicule
    public float consulterCouts(Long id_vehicule) {
        List<Maintenance_Cout> maintenances = maintenanceCoutRepository.findByIdVehicule(id_vehicule);
        float totalCout = 0;

        for (Maintenance_Cout maintenance : maintenances) {
            totalCout += maintenance.getCout();
        }

        System.out.println("Le coût total de maintenance pour le véhicule ID " + id_vehicule + " est : " + totalCout);
        return totalCout;
    }

    // Méthode pour obtenir toutes les maintenances avec les informations des véhicules
    public List<Maintenance_Cout> GetALL_MaintenanceVehicules() {
        List<Maintenance_Cout> maintenanceCouts = maintenanceCoutRepository.findAll();

        for (Maintenance_Cout m : maintenanceCouts) {
            m.setVehicule(vehiculeRestFeign.VehiculeById(m.getId_vehicule()));

        }

        return maintenanceCouts;
    }

    // Méthode pour récupérer une maintenance par ID
    public Maintenance_Cout getMaintenanceById(Long id) {
        Maintenance_Cout maintenance_cout = maintenanceCoutRepository.findById(id).orElse(null);
        maintenance_cout.setVehicule(vehiculeRestFeign.VehiculeById(maintenance_cout.getId_vehicule()));
        return maintenance_cout;
    }

    public void DeleteMaintenance(Long id) {
        Optional<Maintenance_Cout> maintenance = maintenanceCoutRepository.findById(id);

        if (maintenance.isPresent()) {
            maintenanceCoutRepository.deleteById(id);
        }
     else {
        throw new EntityNotFoundException("La maintenance avec l'ID " + id + " n'existe pas.");
    }
    }

    public Maintenance_Cout Update_Maintenance(Long id, Maintenance_Cout maintenance_cout) {
        return maintenanceCoutRepository.findById(id).map(m -> {
            m.setCout(maintenance_cout.getCout());
            m.setDate(maintenance_cout.getDate());
            m.setId_vehicule(maintenance_cout.getId_vehicule());
            return maintenanceCoutRepository.save(m);
        }).orElseThrow(() -> new RuntimeException("Maintenance non trouvée"));
    }

    // Méthode pour générer un rapport en PDF des coûts de maintenance
    public void genererRapportEnPdf() throws IOException{
        System.out.println("Génération du rapport des coûts de maintenance au format PDF : ");

        // Créer un fichier PDF en sortie
        String fileName = "rapport_maintenance.pdf";
        FileOutputStream fileOutputStream = new FileOutputStream(fileName);

        // Créer un PdfDocument
        PdfDocument pdfDocument = new PdfDocument(new com.itextpdf.kernel.pdf.PdfWriter(fileOutputStream));

        // Ajouter du contenu au PDF
        com.itextpdf.layout.Document document = new com.itextpdf.layout.Document(pdfDocument);

        // Ajouter un titre au document PDF
        document.add(new Paragraph("Rapport des Maintenances")
                .setFont(PdfFontFactory.createFont())
                .setFontSize(18)
                .setTextAlignment(TextAlignment.CENTER)
                .setBold());

        // Créer un tableau pour afficher les données
        Table table = new Table(5);
        table.addCell("ID Véhicule");
        table.addCell("Marque");
        table.addCell("Modéle");
        table.addCell("Coût Maintenance");
        table.addCell("Date Maintenance");

        // Récupérer toutes les maintenances
        List<Maintenance_Cout> toutesLesMaintenances = maintenanceCoutRepository.findAll();

        // Ajouter les données au tableau
        for (Maintenance_Cout maintenance : toutesLesMaintenances) {
            Vehicule vehicule = vehiculeRestFeign.VehiculeById(maintenance.getId_vehicule());
            table.addCell(String.valueOf(maintenance.getId_vehicule()));
            table.addCell(vehicule.getMarque());
            table.addCell(vehicule.getModele());
            table.addCell(String.valueOf(maintenance.getCout()));
            table.addCell(maintenance.getDate().toString());
        }

        document.add(table);

        document.close();

        System.out.println("Le rapport PDF a été généré avec succès : " + fileName);
    }

//Utilisation du bibliothèque JFreeChart pour générer un graphique à barres représentant les coûts de maintenance des véhicules
    public void generateGraph(List<Maintenance_Cout> maintenances, String filePath) throws IOException {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        maintenances.forEach(maintenance ->
                dataset.addValue(maintenance.getCout(), "Coût", maintenance.getDate().toString())
        );

        JFreeChart barChart = ChartFactory.createBarChart(
                "Coûts de Maintenance",
                "Date",
                "Coût",
                dataset
        );

        ChartUtils.saveChartAsPNG(new File(filePath), barChart, 800, 600);
    }



    // Méthode pour obtenir les statistiques globales des maintenances
    public Map<String, Double> obtenirStatistiquesGlobales() {
        List<Maintenance_Cout> maintenances = maintenanceCoutRepository.findAll();

        double totalCout = 0.0;
        double nombreTotalMaintenances = 0;
        double maxCout = 0.0;
        double vehiculeLePlusCouteuxId = -1;

        // Calculer le coût total, le nombre total de maintenances et le véhicule le plus coûteux
        for (Maintenance_Cout maintenance : maintenances) {
            totalCout += maintenance.getCout();
            nombreTotalMaintenances++;

            if (maintenance.getCout() > maxCout) {
                maxCout = maintenance.getCout();
                vehiculeLePlusCouteuxId = maintenance.getId_vehicule();
            }
        }

        // Calculer le coût moyen
        double coutMoyen = totalCout / nombreTotalMaintenances;

        Map<String, Double> statistiques = new HashMap<>();
        statistiques.put("ID du véhicule le plus coûteux: ", vehiculeLePlusCouteuxId);
        statistiques.put("Coût moyen de maintenance : ", coutMoyen);
        statistiques.put("Coût total des maintenances : ", totalCout);
        statistiques.put("Nombre total de maintenances : ", nombreTotalMaintenances);

        return statistiques;

    }

    //Methode  pour recuperer la liste de véhicules triés par leur coût total de maintenance en ordre décroissant
    public List<Vehicule> vehiculesLesPlusCouteux() {
        List<Maintenance_Cout> maintenances = maintenanceCoutRepository.findAll();

        // Calculer le coût total de maintenance pour chaque véhicule
        Map<Long, Double> vehiculeCouts = new HashMap<>();
        for (Maintenance_Cout maintenance : maintenances) {
            vehiculeCouts.put(
                    maintenance.getId_vehicule(),
                    vehiculeCouts.getOrDefault(maintenance.getId_vehicule(), 0.0) + maintenance.getCout()
            );
        }

        // Trier les véhicules par coût total décroissant
        List<Map.Entry<Long, Double>> sortedVehicules = vehiculeCouts.entrySet()
                .stream()
                .sorted((entry1, entry2) -> Double.compare(entry2.getValue(), entry1.getValue()))
                .toList();

        // Récupérer les détails des véhicules via le client Feign
        return sortedVehicules.stream()
                .map(entry -> vehiculeRestFeign.VehiculeById(entry.getKey()))
                .toList();
    }

}

