package com.vehicule.vehiculeservice.service;


import com.vehicule.vehiculeservice.entities.Vehicule;
import com.vehicule.vehiculeservice.repository.VehiculeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VehiculeService {

    @Autowired
    private VehiculeRepository vehiculeRepository;

    public List<Vehicule> getAllVehicules() {
        return vehiculeRepository.findAll();
    }

    public Vehicule getVehiculeById(Long id) {
        return vehiculeRepository.findById(id).orElse(null);
    }

    // Méthode pour créer un nouveau véhicule
    public Vehicule createVehicule(Vehicule vehicule) {
        return vehiculeRepository.save(vehicule);
    }

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
        }).orElseThrow(()-> new RuntimeException("Vehicule pas trouvée"));
    }

    // Méthode pour supprimer un véhicule
    public void deleteVehicule(Long id) {
        vehiculeRepository.deleteById(id);
    }

    // Méthode pour obtenir les véhicules par statut
    public List<Vehicule> getVehiculesByStatut(Vehicule.Statut statut) {
        return vehiculeRepository.findByStatut(statut);
    }
}
