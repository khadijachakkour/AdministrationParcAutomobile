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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;


@Service
public class MaintenanceCoutService {

    @Autowired
    private MaintenanceCoutRepository maintenanceCoutRepository;
    @Autowired
    private VehiculeRestFeign vehiculeRestFeign;

    public Maintenance_Cout enregistrerMaintenance(Maintenance_Cout maintenance_cout) {
        return maintenanceCoutRepository.save(maintenance_cout);
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
        maintenanceCoutRepository.deleteById(id);
    }

    public Maintenance_Cout Update_Maintenance(Long id, Maintenance_Cout maintenance_cout) {
        return maintenanceCoutRepository.findById(id).map(m -> {
            m.setCout(maintenance_cout.getCout());
            m.setDate(maintenance_cout.getDate());
            m.setId_vehicule(maintenance_cout.getId_vehicule());
            return maintenanceCoutRepository.save(m);
        }).orElseThrow(() -> new RuntimeException("Chercheur pas trouvée"));
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
        document.add(new Paragraph("Rapport des Coûts de Maintenance")
                .setFont(PdfFontFactory.createFont())
                .setFontSize(18)
                .setTextAlignment(TextAlignment.CENTER)
                .setBold());

        // Créer un tableau pour afficher les données
        Table table = new Table(5); // 4 colonnes (ID Véhicule, Marque, Coût, Date)
        table.addCell("ID Véhicule");
        table.addCell("Marque");
        table.addCell("Modéle");
        table.addCell("Coût");
        table.addCell("Date");

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

        // Ajouter le tableau au document PDF
        document.add(table);

        // Fermer le document PDF
        document.close();

        System.out.println("Le rapport PDF a été généré avec succès : " + fileName);
    }

}

