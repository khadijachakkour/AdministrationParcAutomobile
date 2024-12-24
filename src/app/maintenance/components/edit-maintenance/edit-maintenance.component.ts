import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { MaintenanceService } from '../../services/maintenance.service';
import { MaintenanceCout } from '../../models/maintenance-cout';
import { FormsModule, NgModel } from '@angular/forms';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-edit-maintenance',
  standalone:true,
  imports:[RouterLink, CommonModule, FormsModule],
  templateUrl: './edit-maintenance.component.html',
  styleUrls: ['./edit-maintenance.component.css'],
})
export class EditMaintenanceComponent implements OnInit {
  maintenance: MaintenanceCout | undefined;

  constructor(
    private router: Router,
    private route: ActivatedRoute,
    private maintenanceService: MaintenanceService
  ) {}

  ngOnInit(): void {
    const id = Number(this.route.snapshot.paramMap.get('id')); // Récupérer l'ID depuis l'URL
    this.maintenanceService.getMaintenanceById(id).subscribe(
      (data) => {
        this.maintenance = data; // Charger la maintenance existante
      },
      (error) => {
        console.error('Erreur lors du chargement de la maintenance:', error);
      }
    );
  }

  updateMaintenance(): void {
    if (this.maintenance) {
      this.maintenanceService.updateMaintenance(this.maintenance.id, this.maintenance).subscribe(
        (updatedMaintenance) => {
          console.log('Maintenance mise à jour:', updatedMaintenance);
          this.router.navigate(['/maintenances']); // Rediriger après la mise à jour
        },
        (error) => {
          console.error('Erreur lors de la mise à jour de la maintenance:', error);
        }
      );
    }
  }
}
