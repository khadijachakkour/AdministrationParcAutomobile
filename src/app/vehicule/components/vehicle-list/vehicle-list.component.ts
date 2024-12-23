import { Component, OnInit } from '@angular/core';
import { VehicleService } from '../../services/vehicle.service';
import { Vehicle } from '../../models/vehicle.model';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RouterLink } from '@angular/router';
import { Router } from '@angular/router';

@Component({
  selector: 'app-vehicle-list',
  standalone: true,
  templateUrl: './vehicle-list.component.html',
  styleUrls: ['./vehicle-list.component.css'],
  imports: [RouterLink, CommonModule, FormsModule],
})
export class VehicleListComponent implements OnInit {
  vehicles: Vehicle[] = [];

  constructor(private router: Router,private vehicleService: VehicleService) {}

  ngOnInit(): void {
    this.loadVehicles();
  }

  loadVehicles(): void {
    this.vehicleService.getVehicles().subscribe(
      (data) => {
        this.vehicles = data;
      },
      (error) => {
        console.error('Error loading vehicles', error);
      }
    );
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


  deleteVehicle(vehicleId: number): void {
    if (confirm('Êtes-vous sûr de vouloir supprimer ce véhicule ?')) {
      this.vehicleService.deleteVehicle(vehicleId).subscribe(
        () => {
          alert('Véhicule supprimé avec succès.');
          this.vehicles = this.vehicles.filter(vehicle => vehicle.id !== vehicleId); // Met à jour la liste localement
        },
        (error) => {
          console.error('Erreur lors de la suppression du véhicule:', error);
          alert('Une erreur est survenue lors de la suppression du véhicule.');
        }
      );
    }
  }

  editVehicle(vehicleId: number): void {
    this.router.navigate(['/edit-vehicle', vehicleId]);

  }
}
