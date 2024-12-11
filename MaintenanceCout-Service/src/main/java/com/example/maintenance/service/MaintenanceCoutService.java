package com.example.maintenance.service;

import com.example.maintenance.client.VehiculeRestFeign;
import com.example.maintenance.entities.Maintenance_Cout;
import com.example.maintenance.repository.MaintenanceCoutRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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


}

