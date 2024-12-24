package com.vehicule.vehiculeservice.service;


import com.vehicule.vehiculeservice.entities.Vehicule;
import com.vehicule.vehiculeservice.repository.VehiculeRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.*;

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
        return vehiculeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Véhicule avec l'ID " + id + " non trouvé."));
    }

    // Méthode pour créer un nouveau véhicule
    public Vehicule createVehicule(@Valid Vehicule vehicule) {

        return vehiculeRepository.save(vehicule);
    }

    // Méthode pour Modifier un véhicule
    public Vehicule UpdateVehicule(Long id, Vehicule vehicule){
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
        Vehicule vehicule = vehiculeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Véhicule avec l'ID " + id + " non trouvé."));
        vehiculeRepository.delete(vehicule);
    }

    // Méthode pour obtenir les véhicules par statut
    public List<Vehicule> getVehiculesByStatut(Vehicule.Statut statut) {
        return vehiculeRepository.findByStatut(statut);
    }


    // Mettre à jour la disponibilité d'un véhicule
    public Vehicule mettreAJourStatut(Long id, Vehicule.Statut statut) {
        return vehiculeRepository.findById(id).map(v -> {
            v.setStatut(statut);  // Mise à jour du statut
            return vehiculeRepository.save(v);
        }).orElseThrow(() -> new RuntimeException("Véhicule non trouvé"));
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
        statistiques.put("reserves", reserves);

        return statistiques;
    }


    public Page<Vehicule> getPaginatedVehicles(Pageable pageable) {
        return vehiculeRepository.findAll(pageable);
    }


    // affiches les vehicules par filtre
    public List<Vehicule> filterVehicles(String marque, String modele, String typeVehicule, Vehicule.Statut statut,
                                         LocalDate dateAchat, LocalDate dateDerniereMaintenance, String couleur) {

        // Filtrer par marque
        if (marque != null && !marque.isEmpty()) {
            return vehiculeRepository.findByMarque(marque);
        }

        // Filtrer par modèle
        if (modele != null && !modele.isEmpty()) {
            return vehiculeRepository.findByModele(modele);
        }

        // Filtrer par type de véhicule
        if (typeVehicule != null && !typeVehicule.isEmpty()) {
            return vehiculeRepository.findByTypeVehicule(typeVehicule);
        }

        // Filtrer par statut
        if (statut != null) {
            return vehiculeRepository.findByStatut(statut);
        }


        // Filtrer par date d'achat
        if (dateAchat != null) {
            return vehiculeRepository.findByDateAchat(dateAchat);
        }

        // Filtrer par date de dernière maintenance
        if (dateDerniereMaintenance != null) {
            return vehiculeRepository.findByDateDerniereMaintenance(dateDerniereMaintenance);
        }

        // Filtrer par couleur
        if (couleur != null && !couleur.isEmpty()) {
            return vehiculeRepository.findByCouleur(couleur);
        }

        // Si aucun critère n'est spécifié, retourne tout
        return vehiculeRepository.findAll();
    }


    public Vehicule updateStatut(Long id, Vehicule.Statut statut) {
        // Trouver le véhicule par son ID
        Vehicule vehicule = vehiculeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Véhicule introuvable avec l'ID: " + id));

        // Mettre à jour le statut du véhicule
        vehicule.setStatut(statut);

        // Sauvegarder le véhicule mis à jour dans la base de données
        return vehiculeRepository.save(vehicule);
    }

}
