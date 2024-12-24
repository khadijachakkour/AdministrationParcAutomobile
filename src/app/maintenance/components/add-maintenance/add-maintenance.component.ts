import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';  // Pour rediriger après l'ajout
import { MaintenanceCout } from '../../models/maintenance-cout';
import { Vehicle } from '../../../vehicule/models/vehicle.model';
import { VehicleService } from '../../../vehicule/services/vehicle.service';
import { MaintenanceService } from '../../services/maintenance.service';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-add-maintenance',
  standalone:true,
  imports:[CommonModule, FormsModule],
  templateUrl: './add-maintenance.component.html',
  styleUrls: ['./add-maintenance.component.css']
})
export class AddMaintenanceComponent implements OnInit {
  maintenance: MaintenanceCout = {
    id: 0,
    id_vehicule: 0,
    cout: 0,
    date: new Date(),
    vehicule: { id: 0, marque: '', modele: '', typeVehicule: '', couleur: '', dateDerniereMaintenance: new Date(), dateAchat: new Date(), statut: 'DISPONIBLE' }
  };
  
  vehicules: Vehicle[] = [];

  constructor(
    private vehiculeService: VehicleService, 
    private maintenanceCoutService: MaintenanceService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.loadVehicules();
  }

  // Charger la liste des véhicules pour les afficher dans le select
  loadVehicules(): void {
    this.vehiculeService.getVehicles().subscribe((data: Vehicle[]) => {
      this.vehicules = data;
    });
  }

  onSubmit(): void {
    // Créez un objet avec uniquement les propriétés nécessaires
    const maintenanceData = {
      id_vehicule: this.maintenance.id_vehicule,
      cout: this.maintenance.cout,
      date: this.maintenance.date
    };
  
    // Envoi de l'objet maintenanceData via le service
    this.maintenanceCoutService.addMaintenanceCout(maintenanceData).subscribe(
      (response: MaintenanceCout) => {
        console.log('Maintenance ajoutée avec succès', response);
        this.router.navigate(['/maintenances']);
      },
      (error: any) => {
        console.error('Erreur lors de l\'ajout de la maintenance', error);
      }
    );
  }
}  