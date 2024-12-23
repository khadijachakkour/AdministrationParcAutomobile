import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { VehicleService } from '../../services/vehicle.service';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-add-vehicule',
  standalone:true,
  imports:[FormsModule,CommonModule,ReactiveFormsModule],
  templateUrl: './add-vehicle.component.html',
  styleUrls: ['./add-vehicle.component.css']
})
export class AddVehicleComponent implements OnInit {
  vehiculeForm: FormGroup= new FormGroup({});;  
  submitted = false;

  constructor(
    private formBuilder: FormBuilder,
    private vehiculeService: VehicleService,  // Service pour l'ajout du véhicule
    private router: Router
  ) {}

  ngOnInit(): void {
    this.vehiculeForm = this.formBuilder.group({
      marque: ['', Validators.required],
      modele: ['', Validators.required],
      typeVehicule: ['', Validators.required],
      couleur: ['', Validators.required],
      dateDerniereMaintenance: ['', Validators.required],
      dateAchat: ['', Validators.required],
      statut: ['', Validators.required]
    });
  }

  get f() {
    return this.vehiculeForm.controls;
  }

  onSubmit(): void {
    this.submitted = true;

    // Arrêter si le formulaire est invalide
    if (this.vehiculeForm.invalid) {
      return;
    }

    // Créer l'objet vehicle à partir des données du formulaire
    const newVehicule = {
      marque: this.f['marque'].value,  // Utilisation de la notation entre crochets
      modele: this.f['modele'].value,
      typeVehicule: this.f['typeVehicule'].value,
      couleur: this.f['couleur'].value,
      dateDerniereMaintenance: new Date(this.f['dateDerniereMaintenance'].value),
      dateAchat: new Date(this.f['dateAchat'].value),
      statut: this.f['statut'].value
    };

    // Appeler le service pour ajouter le véhicule
    this.vehiculeService.addVehicle(newVehicule).subscribe(
      (response) => {
        // Rediriger vers la page d'affichage ou une autre page après succès
        this.router.navigate(['/vehicles']);
      },
      (error) => {
        console.error('Erreur lors de l\'ajout du véhicule', error);
      }
    );
  }
}
