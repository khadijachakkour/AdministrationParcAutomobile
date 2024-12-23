import { Component } from '@angular/core';
import { UserService } from '../services/user.service';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-registration',
  standalone: true,
  imports: [
    CommonModule,
    FormsModule,
  
  ],
  templateUrl: './registration.component.html',
  styleUrls: ['./registration.component.css']
})
export class RegistrationComponent {

  user = {
    username: '',
    email: '',
    password: '',
    roleName: ''  // Le rôle sera défini par l'utilisateur
  };

  roles = ['USER', 'Parc_Gestionnaire', 'TECHNICIEN'];  // Liste des rôles disponibles

  constructor(private userService: UserService, private router: Router) {}

  onSubmit() {
    const userToSend = {
      username: this.user.username,
      email: this.user.email,
      password: this.user.password,
      roleName: this.user.roleName
    };

    this.userService.createUser(userToSend).subscribe(
      (data) => {
        console.log('Utilisateur créé avec succès', data);
        this.router.navigate(['/login']);  // Redirige vers la page de connexion après l'inscription
      },
      (error) => {
        console.error('Erreur lors de la création de l\'utilisateur', error);
      }
    );
  }
}
