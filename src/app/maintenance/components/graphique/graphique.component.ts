// src/app/components/graphique/graphique.component.ts
import { Component, OnInit } from '@angular/core';
import { saveAs } from 'file-saver';  // Utilisez file-saver pour télécharger le fichier
import { MaintenanceService } from '../../services/maintenance.service';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-graphique',
  standalone:true,
  imports:[CommonModule, FormsModule],
  templateUrl: './graphique.component.html',
  styleUrls: ['./graphique.component.css']
})
export class GraphiqueComponent implements OnInit {
  imageUrl: string | null = null;
  idVehicule: number | null = null;

  constructor(
    private route: ActivatedRoute,
    private maintenanceService: MaintenanceService  // Injection du service
  ) { }

  ngOnInit(): void {
    // Récupérer l'ID du véhicule depuis l'URL
    this.idVehicule = +this.route.snapshot.paramMap.get('idVehicule')!;
    if (this.idVehicule) {
      // Utiliser le service pour récupérer le graphique
      this.getGraph();
    }
  }

  getGraph(): void {
    if (this.idVehicule !== null) {
      this.maintenanceService.getGraph(this.idVehicule).subscribe(
        (response: Blob) => {
          const objectURL = URL.createObjectURL(response);
          this.imageUrl = objectURL;
        },
        (error) => {
          console.error('Erreur lors du chargement du graphique', error);
        }
      );
    } else {
      console.error('ID du véhicule non valide');
    }
  }
  

}