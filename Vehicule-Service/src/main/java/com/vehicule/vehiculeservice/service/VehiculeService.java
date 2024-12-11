package com.vehicule.vehiculeservice.service;


import com.vehicule.vehiculeservice.entities.Vehicule;
import com.vehicule.vehiculeservice.repository.VehiculeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class VehiculeService {

    @Autowired
    private VehiculeRepository vehiculeRepository;

    // Méthode pour obtenir tous les véhicules
    public List<Vehicule> getAllVehicules() {
        return vehiculeRepository.findAll();
    }

    // Méthode pour obtenir un véhicule par son ID
    public Vehicule getVehiculeById(Long id) {
        return vehiculeRepository.findById(id).orElse(null);
    }

    // Méthode pour créer un nouveau véhicule
    public Vehicule createVehicule(Vehicule vehicule) {
        return vehiculeRepository.save(vehicule);
    }

    //// Méthode pour Modifier un véhicule
    public Vehicule Update_Vehicule(Long id,Vehicule vehicule){
        return vehiculeRepository.findById(id).map(v ->{
            v.setMarque(vehicule.getMarque());
            v.setModele(vehicule.getModele());
            v.setTypeVehicule(vehicule.getTypeVehicule());
            v.setCouleur(vehicule.getCouleur());
            v.setDateDerniereMaintenance(vehicule.getDateDerniereMaintenance());
            v.setDateAchat(vehicule.getDateAchat());
            v.setStatut(vehicule.getStatut());
            return vehiculeRepository.save(v);
        }).orElseThrow(()-> new RuntimeException("Vehicule non trouvée"));
    }

    // Méthode pour supprimer un véhicule
    public void deleteVehicule(Long id) {
        vehiculeRepository.deleteById(id);
    }

    // Méthode pour obtenir les véhicules par statut
    public List<Vehicule> getVehiculesByStatut(Vehicule.Statut statut) {
        return vehiculeRepository.findByStatut(statut);
    }


    // Mettre à jour la disponibilité d'un véhicule
    public Vehicule mettreAJourStatut(Long id, Vehicule.Statut statut) {
        Vehicule vehicule = vehiculeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Véhicule non trouvé"));
        vehicule.setStatut(statut);
        return vehiculeRepository.save(vehicule);
    }

    // Vérifier la disponibilité d'un véhicule
    public Boolean verifierDisponibilite(Long id) {
        Vehicule vehicule = vehiculeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Véhicule non trouvé"));
        return vehicule.getStatut() == Vehicule.Statut.DISPONIBLE;
    }

    // Récupérer tous les véhicules disponibles
    public List<Vehicule> getVehiculesDisponibles() {
        return vehiculeRepository.findByStatut(Vehicule.Statut.DISPONIBLE);
    }


    // Statistiques des véhicules
    public Map<String, Long> obtenirStatistiques() {
        long total = vehiculeRepository.count();
        long disponibles = vehiculeRepository.findByStatut(Vehicule.Statut.DISPONIBLE).size();
        long enMaintenance = vehiculeRepository.findByStatut(Vehicule.Statut.EN_MAINTENANCE).size();
        long reserves = vehiculeRepository.findByStatut(Vehicule.Statut.RESERVE).size();

        Map<String, Long> statistiques = new HashMap<>();
        statistiques.put("total", total);
        statistiques.put("disponibles", disponibles);
        statistiques.put("enMaintenance", enMaintenance);
        statistiques.put("reservés", reserves);

        return statistiques;
    }
}
