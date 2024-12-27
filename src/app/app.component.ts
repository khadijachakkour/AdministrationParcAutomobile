import { Component, NgModule } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { HttpClientModule } from '@angular/common/http';
import { UserService } from './services/user.service';
import { FormsModule, NgModel } from '@angular/forms';
import { BrowserModule } from '@angular/platform-browser';
import { CommonModule } from '@angular/common';
import { ReservationService } from './Reservation/reservation.service';
import { ReservationfrontService } from './FrontReservation/reservationfront.service';


@Component({
  selector: 'app-root',
  standalone: true,
  imports: [
    RouterOutlet, 
    HttpClientModule ,// Ajoutez ici HttpClientModule
    FormsModule,
    CommonModule,
  ],
  providers: [UserService, ReservationService, ReservationfrontService], 

  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css'] // Attention, c'est styleUrls avec un "s"
})


export class AppComponent {
  title = 'Service_User';
}
