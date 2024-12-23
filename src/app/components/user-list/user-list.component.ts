import { Component, OnInit } from '@angular/core';
import { UserService, UserDTO } from '../../services/user.service';
import { NO_ERRORS_SCHEMA } from '@angular/core';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-user-list',
  standalone: true,
  imports: [CommonModule],
  schemas: [NO_ERRORS_SCHEMA],
  templateUrl: './user-list.component.html',
  styleUrls: ['./user-list.component.css'],
  
})


export class UserListComponent implements OnInit {
  users: UserDTO[] = [];

  constructor(private userService: UserService) {}

  ngOnInit(): void {
    this.userService.getAllUsers().subscribe({
      next: (data) => {
        console.log('Utilisateurs reçus :', data);
        this.users = data;
      },
      error: (err) => {
        console.error('Erreur lors de la récupération des utilisateurs :', err);
      },
    });
  }
}
