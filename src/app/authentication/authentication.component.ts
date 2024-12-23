import { Component } from '@angular/core';
import { UserService } from '../services/user.service';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-authentication',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './authentication.component.html',
  styleUrls: ['./authentication.component.css'],
})
export class AuthenticationComponent {
  email: string = '';
  password: string = '';
  loginFailed: boolean = false;

  constructor(private authService: UserService, private router: Router) {}

  onSubmit(): void {
    this.authService.getUserRoles(this.email, this.password).subscribe(
      (roles: string[]) => {
        console.log('Rôles récupérés depuis le backend:', roles); // Affiche les rôles en console

        if (roles.length > 0) {
          this.redirectBasedOnRole(roles[0]); // Redirige vers la première page de rôle
        } else {
          this.loginFailed = true; // Aucun rôle valide
          console.log('Aucun rôle valide trouvé');
        }
      },
      (error) => {
        console.error('Erreur d\'authentification:', error); // Affiche les erreurs backend
        this.loginFailed = true;
      }
    );
  }

  redirectBasedOnRole(role: string): void {
    switch (role) {
      case 'ADMIN':
        this.router.navigate(['/login']);
        break;
      case 'USER':
        this.router.navigate(['/dashboard']);
        break;
      case 'Parc_Gestionnaire':
        this.router.navigate(['/parc-dashboard']);
        break;
      case 'TECHNICIEN':
        this.router.navigate(['/technician-dashboard']);
        break;
      default:
        this.loginFailed = true;
        console.log('Rôle inconnu:', role);
        break;
    }
  }
  
}
