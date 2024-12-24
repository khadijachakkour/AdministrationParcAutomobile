import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { MaintenanceCout } from '../models/maintenance-cout';
import { Vehicule } from '../models/vehicule';

@Injectable({
  providedIn: 'root',
})
export class MaintenanceService {
  private apiUrl = 'http://localhost:8082/api/v1/maintenance';

  constructor(private http: HttpClient) {}

  // Méthode pour ajouter une maintenance
  addMaintenanceCout(maintenance: any): Observable<any> {
    const url = `${this.apiUrl}`; // API URL
    return this.http.post(url, maintenance);
  }
  consulterCouts(idVehicule: number): Observable<number> {
    return this.http.get<number>(`${this.apiUrl}/cout/${idVehicule}`);
  }

  getAllMaintenancesWithVehicules(): Observable<MaintenanceCout[]> {
  return this.http.get<MaintenanceCout[]>(`${this.apiUrl}/all-maintenance`);
}


  getMaintenanceById(id: number): Observable<MaintenanceCout> {
    return this.http.get<MaintenanceCout>(`${this.apiUrl}/${id}`);
  }

  deleteMaintenance(id: number): Observable<void> {
    const url = `${this.apiUrl}/${id}`;
    return this.http.delete<void>(url); 
  }
  

  updateMaintenance(id: number, data: MaintenanceCout): Observable<MaintenanceCout> {
    return this.http.put<MaintenanceCout>(`${this.apiUrl}/${id}`, data);
  }

  generateReport(): Observable<Blob> {
    return this.http.get(`${this.apiUrl}/rapport-pdf`, { responseType: 'blob' });
  }

  generateGraph(idVehicule: number): Observable<Blob> {
    return this.http.get(`${this.apiUrl}/graphique/${idVehicule}`, { responseType: 'blob' });
  }

  obtenirStatistiques(): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}/statistiques`);
  }

  vehiculesLesPlusCouteux(): Observable<Vehicule[]> {
    return this.http.get<Vehicule[]>(`${this.apiUrl}/vehicules/top-maintenance`);
  }
}
