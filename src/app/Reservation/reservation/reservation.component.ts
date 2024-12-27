import { Component, OnInit } from '@angular/core';
import { ReservationService } from '../reservation.service';
import { Reservation } from '../Models/reservation.model'; 
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';


@Component({
  selector: 'app-reservation',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './reservation.component.html',
  styleUrls: ['./reservation.component.css'],
})
export class ReservationComponent implements OnInit {
  reservations: Reservation[] = []; // Liste des réservations
  newReservation: Reservation = { // Données pour une nouvelle réservation
    id: 0,
    email: '',
    marque: '',
    modele: '',
    typeVehicule: '',
    couleur: '',
    reservationDate: new Date(),
    status: 'En attente',
    userId: 0,  // Aucune valeur par défaut ici, elle sera récupérée côté back-end
    vehicleId: 0  // Le vehicleId sera récupéré aussi côté back-end
  };
  
  constructor(
    private reservationService: ReservationService,
    private router: Router 
    ) {}

  ngOnInit(): void {
    // Charger les réservations dès le chargement du composant
    this.loadReservations();
  }

  loadReservations(): void {
    this.reservationService.getReservations().subscribe(
      (data) => {
        this.reservations = data; // Stocker les réservations dans la variable
      },
      (error) => {
        console.error('Erreur de récupération des réservations', error);
      }
    );
  }

  onSubmit(): void {
    // Appeler la méthode pour ajouter une nouvelle réservation
    this.reservationService.createReservation(this.newReservation).subscribe(
      (data) => {
        // Ajouter la nouvelle réservation à la liste locale après ajout
        this.reservations.push(data);
        // Réinitialiser le formulaire
        this.newReservation = {
          id: 0,
          email: '',
          marque: '',
          modele: '',
          typeVehicule: '',
          couleur: '',
          reservationDate: new Date(),
          status: 'En attente',
          userId: 0,
          vehicleId: 0
        };
        // Rediriger l'utilisateur vers une autre page après la soumission
        this.router.navigate(['/home']); // Par exemple, vers une page de confirmation
      },
      (error) => {
        console.error('Erreur lors de la création de la réservation', error);
      }
    );
  }

  onCancel(id: string | number): void {
    if (id !== undefined && id !== null) {
      console.log('Annuler la réservation avec l\'id :', id);
      // Logique de suppression ici
    } else {
      console.error('L\'ID de la réservation est invalide');
    }
  }
  
  
}
