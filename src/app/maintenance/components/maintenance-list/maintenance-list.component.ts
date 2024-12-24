import { Component, OnInit } from '@angular/core'; 
import { MaintenanceService } from '../../services/maintenance.service';
import { MaintenanceCout } from '../../models/maintenance-cout';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RouterLink } from '@angular/router';
import { Router } from '@angular/router';

@Component({
  selector: 'app-list-maintenance',
  standalone: true,
  imports: [RouterLink, CommonModule, FormsModule],
  templateUrl: './maintenance-list.component.html',
  styleUrls: ['./maintenance-list.component.css'],
})
export class ListMaintenanceComponent implements OnInit {
  maintenances: MaintenanceCout[] = [];

  constructor(private router: Router, private maintenanceService: MaintenanceService) {}

  ngOnInit(): void {
    this.loadMaintenances();
  }

  loadMaintenances(): void {
    this.maintenanceService.getAllMaintenancesWithVehicules().subscribe(
      (data) => {
        this.maintenances = data;
      },  
      (error) => {
        console.error('Erreur lors du chargement des maintenances :', error);
      }
    );
  }

  deleteMaintenance(id: number): void {
    if (confirm('Êtes-vous sûr de vouloir supprimer cette maintenance ?')) {
      this.maintenanceService.deleteMaintenance(id).subscribe(
        () => {
          console.log('Maintenance supprimée avec succès');
          // Mettre à jour localement la liste
          this.maintenances = this.maintenances.filter(maintenance => maintenance.id !== id);
        },
        (error) => {
          if (error.status === 404) {
            alert('Maintenance introuvable.');
          } else {
            console.error('Erreur lors de la suppression de la maintenance :', error);
            alert('Une erreur est survenue lors de la suppression.');
          }
        }
      );
    }
  }
  
  

  editMaintenance(id: number): void {
    this.router.navigate([`/edit-maintenance/${id}`]);  // Rediriger vers une page d'édition (créée ailleurs dans votre application)
  }
}
