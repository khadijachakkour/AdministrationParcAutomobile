import { CommonModule } from '@angular/common';
import { ReservationfrontService } from '../FrontReservation/reservationfront.service';
import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-reservationsfront',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './reservationsfront.component.html',
  styleUrl: './reservationsfront.component.css'
})
export class ReservationsfrontComponent implements OnInit {
  reservations: any[] = [];

  constructor(private reservationService: ReservationfrontService,
    private route: ActivatedRoute,
    private router: Router
    ) {}

  ngOnInit(): void {
    this.getReservations();
  }

  getReservations(): void {
    this.reservationService.getAllReservations().subscribe(
      (data) => {
        console.log('Données reçues:', data);
  
        // Transformation des données pour correspondre à votre modèle
        this.reservations = data.map((reservation: any) => ({
          id: reservation.id.timestamp, // Utiliser timestamp comme ID
          reservationDate: reservation.reservationDate,
          status: reservation.status,
          email: reservation.email,
          marque: reservation.marque,
          modele: reservation.modele,
          typeVehicule: reservation.typeVehicule,
          couleur: reservation.couleur,
        }));
      },
      (error) => {
        console.error('Erreur lors de la récupération des réservations:', error);
      }
    );
  }
  

//Ajout
newReservation = {
  email: '',
  marque: '',
  modele: '',
  typeVehicule: '',
  couleur: '',
  reservationDate: new Date().toISOString(), // Date par défaut
  status: 'En attente'  // Ajouter le statut ici
};

addReservation(): void {
  this.reservationService.createReservation(this.newReservation).subscribe(
    (response) => {
      console.log('Réservation créée avec succès :', response);
      this.reservations.push(response); // Ajouter la nouvelle réservation à la liste
      this.newReservation = {
        email: '',
        marque: '',
        modele: '',
        typeVehicule: '',
        couleur: '',
        reservationDate: new Date().toISOString(), // Réinitialiser le formulaire avec une nouvelle date
        status: 'En attente'  // Réinitialiser aussi le statut par défaut
      };
    },
    (error) => console.error('Erreur lors de la création de la réservation :', error)
  );
}


//update 
updatedReservation = {
  id: '',  // L'ID de la réservation
  reservationDate: '',
  status: '',
  email: '',
  marque: '',
  modele: '',
  typeVehicule: '',
  couleur: ''
};
updateReservation(): void {
  // Vérifier si l'ID est bien présent
  if (!this.updatedReservation.id) {
    console.error('ID manquant pour la réservation');
    return;
  }

  // Assurez-vous que toutes les autres propriétés nécessaires sont bien présentes
  const reservationData = {
    reservationDate: this.updatedReservation.reservationDate, // Assurez-vous que ce champ est défini
    status: this.updatedReservation.status, // Assurez-vous que ce champ est défini
    email: this.updatedReservation.email,
    marque: this.updatedReservation.marque,
    modele: this.updatedReservation.modele,
    typeVehicule: this.updatedReservation.typeVehicule,
    couleur: this.updatedReservation.couleur
  };

  // Appelez le service avec l'ID dans l'URL et les données dans le corps de la requête
  this.reservationService.updateReservation(this.updatedReservation.id, reservationData).subscribe(
    (response) => {
      console.log('Réservation mise à jour avec succès :', response);
      this.router.navigate(['/reservationsfront']);
    },
    (error) => {
      console.error('Erreur lors de la mise à jour de la réservation :', error);
    }
  );
}

}
