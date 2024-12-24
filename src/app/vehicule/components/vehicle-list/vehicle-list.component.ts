import { Component, OnInit } from '@angular/core';
import { VehicleService } from '../../services/vehicle.service';
import { Vehicle } from '../../models/vehicle.model';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RouterLink } from '@angular/router';
import { Router } from '@angular/router';
import Swal from 'sweetalert2';


@Component({
  selector: 'app-vehicle-list',
  standalone: true,
  templateUrl: './vehicle-list.component.html',
  styleUrls: ['./vehicle-list.component.css'],
  imports: [RouterLink, CommonModule, FormsModule],
})
export class VehicleListComponent implements OnInit {
  vehicles: Vehicle[] = [];
  filteredVehicles: Vehicle[] = [];
  searchQuery: string = ''; 
  filterOption: 'all' | 'available' = 'all';
  currentPage: number = 0;
  totalPages: number = 0;
  pageSize: number = 10;


  constructor(private router: Router,private vehicleService: VehicleService) {}

  ngOnInit(): void {
    this.loadVehicles();
  }

  loadVehicles(): void {
    this.vehicleService.getVehicles().subscribe(
      (data) => {
        this.vehicles = data;
        this.filterVehicles(); 
      },
      (error) => {
        console.error('Error loading vehicles', error);
      }
    );
  }
 
// Mettre à jour le filtre actif
setFilterOption(option: 'all' | 'available'): void {
  this.filterOption = option;
  this.filterVehicles();
}

// Méthode de filtrage
filterVehicles(): void {
  const query = this.searchQuery.toLowerCase();
  this.filteredVehicles = this.vehicles.filter(vehicle =>
    (this.filterOption === 'all' || vehicle.statut === 'DISPONIBLE') &&
    (
      vehicle.marque.toLowerCase().includes(query) ||
      vehicle.modele.toLowerCase().includes(query) ||
      vehicle.typeVehicule.toLowerCase().includes(query)
    )
  );
}

searchVehicles(): void {
  this.filterVehicles(); // Appeler le filtre global
}
  getStatutClass(statut: 'DISPONIBLE' | 'EN_MAINTENANCE' | 'RESERVE'): string {
    switch (statut) {
      case 'DISPONIBLE':
        return 'disponible';
      case 'EN_MAINTENANCE':
        return 'en-maintenance';
      case 'RESERVE':
        return 'reserve';
      default:
        return '';
    }
  }


  // Méthode de suppression avec confirmation via SweetAlert2
  deleteVehicle(vehicleId: number): void {
    Swal.fire({
      title: 'Êtes-vous sûr ?',
      text: 'Vous êtes sur le point de supprimer ce véhicule.',
      icon: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#3085d6',
      cancelButtonColor: '#d33',
      confirmButtonText: 'Oui, supprimer !',
    }).then((result) => {
      if (result.isConfirmed) {
        this.vehicleService.deleteVehicle(vehicleId).subscribe(
          () => {
            Swal.fire('Supprimé!', 'Le véhicule a été supprimé.', 'success');
            this.filteredVehicles = this.filteredVehicles.filter(vehicle => vehicle.id !== vehicleId); // Mise à jour locale
          },
          (error) => {
            console.error('Erreur lors de la suppression du véhicule:', error);
            Swal.fire('Erreur!', "Une erreur est survenue lors de la suppression du véhicule.", 'error');
          }
        );
      }
    });
  }

  editVehicle(vehicleId: number): void {
    this.router.navigate([`/edit-vehicle/${vehicleId}`]);  

  }
}
