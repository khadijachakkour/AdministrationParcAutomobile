// rapport-maintenance.component.ts
import { Component } from '@angular/core';
import { MaintenanceService } from '../../services/maintenance.service';
import { saveAs } from 'file-saver';

@Component({
  selector: 'app-rapport-maintenance',
  standalone:true,
  templateUrl: './rapport-maintenance.component.html',
  styleUrls: ['./rapport-maintenance.component.css']
})
export class RapportMaintenanceComponent {

  constructor(private maintenanceService: MaintenanceService) { }

  // Méthode pour appeler l'API et télécharger le fichier PDF
  downloadRapport() {
    this.maintenanceService.genererRapportPdf().subscribe(
      (response: Blob) => {
        // Utilisation de 'file-saver' pour enregistrer le fichier localement
        saveAs(response, 'rapport_maintenance.pdf');
      },
      (error) => {
        console.error('Erreur lors du téléchargement du rapport:', error);
      }
    );
  }
}
