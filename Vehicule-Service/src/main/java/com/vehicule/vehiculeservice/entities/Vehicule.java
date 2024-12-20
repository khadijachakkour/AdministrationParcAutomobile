package com.vehicule.vehiculeservice.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDate;

@AllArgsConstructor @NoArgsConstructor
@Getter @Setter
@Entity
public class Vehicule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "La marque est obligatoire")
    @Size(min = 2, max = 50, message = "La marque doit contenir entre 2 et 50 caractères")
    private String marque;

    @NotNull(message = "Le modèle est obligatoire")
    @Size(min = 1, max = 50, message = "Le modèle doit contenir entre 1 et 50 caractères")
    private String modele;


    @NotNull(message = "Le type de véhicule est obligatoire")
    private String typeVehicule;

    @NotNull(message = "La couleur est obligatoire")
    private String couleur;
    @NotNull(message = "La date de derniere maintenace est obligatoire")
    private LocalDate dateDerniereMaintenance;
    @NotNull(message = "La date d'achat est obligatoire")
    private LocalDate dateAchat;


    @NotNull(message = "Le statut est obligatoire")
    @Enumerated(EnumType.STRING)  // Utilisation de l'énumération comme chaîne de caractères
    private Statut statut;



    public enum Statut {
        DISPONIBLE,
        EN_MAINTENANCE,
        RESERVE
    }
}
