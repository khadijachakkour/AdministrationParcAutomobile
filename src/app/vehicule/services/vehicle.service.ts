// /src/app/vehicule/services/vehicle.service.ts
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Vehicle } from '../models/vehicle.model';

@Injectable({
  providedIn: 'root'
})
export class VehicleService {
  private apiUrl = 'http://localhost:8085/api/v1/vehicules'; // URL du service

  constructor(private http: HttpClient) {}

  getVehicles(): Observable<Vehicle[]> {
    return this.http.get<Vehicle[]>(this.apiUrl);
  }

  getVehicle(id: number): Observable<Vehicle> {
    return this.http.get<Vehicle>(`${this.apiUrl}/${id}`);
  }

  addVehicle(vehicle: Vehicle): Observable<Vehicle> {
    return this.http.post<Vehicle>(this.apiUrl, vehicle);
  }

  updateVehicle(vehicle: Vehicle): Observable<Vehicle> {
    return this.http.put<Vehicle>(`${this.apiUrl}/${vehicle.id}`, vehicle);
  }

  deleteVehicle(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }
  getAvailableVehicles(): Observable<any> {
    return this.http.get(`${this.apiUrl}/disponibles`);
  }

  updateVehicleStatus(id: number, status: string): Observable<any> {
    return this.http.patch(`${this.apiUrl}/${id}/statut`, null, {
      params: { statut: status }
    });
  }

  getStatistics(): Observable<any> {
    return this.http.get(`${this.apiUrl}/statistiques`);
  }

  filterVehicles(filters: any): Observable<any> {
    return this.http.get(`${this.apiUrl}/filter`, { params: filters });
  }

  getVehicleStatistics(): Observable<any> {
    return this.http.get(`${this.apiUrl}/statistiques`);
  }

  
}
