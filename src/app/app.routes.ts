import { Routes } from '@angular/router';
import { VehicleListComponent } from './vehicule/components/vehicle-list/vehicle-list.component';
import { EditVehicleComponent } from './vehicule/components/edit-vehicle/edit-vehicle.component';
import { AddVehicleComponent } from './vehicule/components/add-vehicle/add-vehicle.component';


export const routes: Routes = [
  { path: 'vehicles', component: VehicleListComponent },
  { path: '', redirectTo: '/vehicles', pathMatch: 'full' },
  { path: 'edit-vehicle/:id', component: EditVehicleComponent },
  { path: 'add-vehicle', component: AddVehicleComponent }

];
