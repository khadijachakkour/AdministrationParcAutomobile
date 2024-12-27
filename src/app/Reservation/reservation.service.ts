import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Reservation } from './Models/reservation.model'; 

@Injectable({
  providedIn: 'root',
})
export class ReservationService {
 // private apiUrl = 'http://localhost:5106/api/reservation'; // Update the endpoint as needed
 private apiUrl = 'http://localhost:5000/api/reservation'; 

  constructor(private http: HttpClient) {}

  // Fetch all reservations
  getReservations(): Observable<Reservation[]> {
    return this.http.get<Reservation[]>(this.apiUrl);
  }

 // Méthode pour créer une nouvelle réservation
 createReservation(reservation: Reservation): Observable<Reservation> {
  return this.http.post<Reservation>(this.apiUrl, reservation);
}
  // Update a reservation
  updateReservation(id: string | number, reservation: Reservation): Observable<Reservation> {
    return this.http.put<Reservation>(`${this.apiUrl}/${id}`, reservation);
  }

 // Méthode pour annuler une réservation
 cancelReservation(id: string, cancellationTime: Date): Observable<boolean> {
  return this.http.delete<boolean>(`${this.apiUrl}/${id}`, {
    body: { cancellationTime: cancellationTime }
  });
}
}
