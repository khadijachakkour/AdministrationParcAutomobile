import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { switchMap, map } from 'rxjs/operators'; 

export interface UserDTO {
  id?: number;
  username: string;
  password: string;
  email: string;
  roleName: string;
}

@Injectable({
  providedIn: 'root',
})
export class UserService {
  private baseUrl = 'http://localhost:8090/api/users'; // URL de votre backend
  //private validateUrl = 'http://localhost:8090/api/users/validate'; 

  constructor(private http: HttpClient) {}

  getAllUsers(): Observable<UserDTO[]> {
    return this.http.get<UserDTO[]>(`${this.baseUrl}`);
  }

  createUser(user: UserDTO): Observable<UserDTO> {
    return this.http.post<UserDTO>(`${this.baseUrl}`, user);
  }

  getUserById(id: number): Observable<UserDTO> {
    return this.http.get<UserDTO>(`${this.baseUrl}/${id}`);
  }
/*
  validateUser(email: string, password: string): Observable<boolean> {
    const params = new HttpParams().set('email', email).set('password', password);
    return this.http.get<boolean>(`${this.baseUrl}/validate`, { params });
  }

  // Vérification du rôle de l'utilisateur
  checkUserRole(email: string, role: string): Observable<boolean> {
    const params = new HttpParams().set('email', email).set('role', role);
    return this.http.get<boolean>(`${this.baseUrl}/hasRole`, { params });
  }
  */

   // Méthode pour obtenir les rôles d'un utilisateur
   getUserRoles(email: string, password: string): Observable<string[]> {
    const params = new HttpParams().set('email', email).set('password', password);
    return this.http.post<string[]>(`${this.baseUrl}/roles`, null, { params });
  }
}
