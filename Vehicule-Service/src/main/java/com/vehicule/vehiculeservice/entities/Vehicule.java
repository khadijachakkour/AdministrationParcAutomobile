package com.vehicule.vehiculeservice.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity  @Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class Vehicule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String marque;

    private String modele;


    private String typeVehicule;

    private String couleur;
    private LocalDate dateDerniereMaintenance;
    private LocalDate dateAchat;


    @Enumerated(EnumType.STRING)  // Utilisation de l'énumération comme chaîne de caractères
    private Statut statut;



    public enum Statut {
        DISPONIBLE,
        EN_MAINTENANCE,
        RESERVE
    }
}
