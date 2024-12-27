import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ReservationfrontService {
//  private apiUrl = 'http://localhost:5106/api/reservation'; // URL de votre API
private apiUrl = 'http://localhost:5000/api/reservation'; // URL de votre API

  constructor(private http: HttpClient) {}

  getAllReservations(): Observable<any[]> {
    return this.http.get<any[]>(this.apiUrl);
  }

  getReservationById(id: string): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}/${id}`);
  }

  createReservation(reservation: any): Observable<any> {
    return this.http.post<any>(this.apiUrl, reservation);
  }  

  /* Méthode pour mettre à jour une réservation
  updateReservation(id: string, updatedReservation: any): Observable<any> {
    return this.http.put<any>(`${this.apiUrl}/${id}`, updatedReservation);
  }
  */
    // Méthode pour mettre à jour la réservation
    updateReservation(id: string, reservation: any): Observable<any> {
      return this.http.put(`${this.apiUrl}/${id}`, reservation);  // Utilise l'ID dans l'URL
    }
  
  
  

  deleteReservation(id: string): Observable<any> {
    return this.http.delete<any>(`${this.apiUrl}/${id}`);
  }
}