// /src/app/vehicule/vehicule.module.ts
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { VehicleListComponent } from './components/vehicle-list/vehicle-list.component';
import { VehicleService } from './services/vehicle.service';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { AddVehicleComponent } from './components/add-vehicle/add-vehicle.component';
import { RouterModule } from '@angular/router';

@NgModule({
  declarations: [
    VehicleListComponent,
    AddVehicleComponent
  ],
  imports: [
    CommonModule,
    FormsModule,
    RouterModule,
    ReactiveFormsModule
  ],
  providers: [VehicleService]
})
export class VehiculeModule {}
