import { Component } from '@angular/core';
import { UserService } from '../services/user.service';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-user-registration',
  standalone: true,
  imports: [
    CommonModule,
    FormsModule,
  ],
  templateUrl: './user-registration.component.html',
  styleUrls: ['./user-registration.component.css']
})
export class UserRegistrationComponent {

  user = {
    username: '',
    email: '',
    password: '',
    roleName: 'USER'  // Le rôle est statique et défini comme "USER"
  };

  constructor(private userService: UserService, private router: Router) {}

  onSubmit() {
    // Le rôle est toujours "USER" et est inclus lors de la soumission
    const userToSend = {
      username: this.user.username,
      email: this.user.email,
      password: this.user.password,
      roleName: this.user.roleName  // Le rôle "USER" est envoyé avec l'utilisateur
    };

    this.userService.createUser(userToSend).subscribe(
      (data) => {
        console.log('Utilisateur créé avec succès', data);
        this.router.navigate(['/auth']);  // Redirige vers la page de connexion après l'inscription
      },
      (error) => {
        console.error('Erreur lors de la création de l\'utilisateur', error);
      }
    );
  }
}
