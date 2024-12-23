import { Component, OnInit } from '@angular/core';
import { VehicleService } from '../../vehicule/services/vehicle.service';

@Component({
  selector: 'app-dashboard',
  standalone:true,
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit {
  statistics: any = {
    total: 0,
    disponibles: 0,
    enMaintenance: 0,
    reserves:0,
  };

  constructor(private vehicleService: VehicleService) {}

  ngOnInit(): void {
    this.loadStatistics();
  }

  // Méthode pour charger les statistiques
  loadStatistics(): void {
    this.vehicleService.getVehicleStatistics().subscribe(
      (data) => {
        this.statistics = data;
      },
      (error) => {
        console.error('Erreur lors du chargement des statistiques', error);
      }
    );
  }

  
}
