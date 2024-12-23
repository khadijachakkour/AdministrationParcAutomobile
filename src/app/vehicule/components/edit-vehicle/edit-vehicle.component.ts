import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { VehicleService } from '../../services/vehicle.service';
import { Vehicle } from '../../models/vehicle.model';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-edit-vehicle',
  standalone:true,
  templateUrl: './edit-vehicle.component.html',
  styleUrls: ['./edit-vehicle.component.css'],
  imports:[FormsModule]
})
export class EditVehicleComponent implements OnInit {
  vehicle: Vehicle = {} as Vehicle;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private vehicleService: VehicleService
  ) {}

  ngOnInit(): void {
    const vehicleId = +this.route.snapshot.paramMap.get('id')!;
    this.vehicleService.getVehicle(vehicleId).subscribe(
      (data) => {
        this.vehicle = data;
      },
      (error) => {
        console.error('Erreur lors du chargement du véhicule:', error);
      }
    );
  }

  onSubmit(): void {
    this.vehicleService.updateVehicle(this.vehicle).subscribe(
      () => {
        alert('Véhicule modifié avec succès.');
        this.router.navigate(['/vehicles']);
      },
      (error) => {
        console.error('Erreur lors de la modification:', error);
        alert('Une erreur est survenue.');
      }
    );
  }

  cancelEdit(): void {
    // Rediriger l'utilisateur vers la liste des véhicules (ou une autre page)
    this.router.navigate(['/vehicles']);
  }
}
