import { Routes } from '@angular/router';
import { VehicleListComponent } from './vehicule/components/vehicle-list/vehicle-list.component';
import { EditVehicleComponent } from './vehicule/components/edit-vehicle/edit-vehicle.component';
import { AddVehicleComponent } from './vehicule/components/add-vehicle/add-vehicle.component';
import { PageNotFoundComponent } from './core/page-not-found/page-not-found.component';
import { DashboardComponent } from './core/dashboard/dashboard.component';


export const routes: Routes = [
  { path: '', component: DashboardComponent },
  { path: 'dashboard', component: DashboardComponent },
  { path: 'vehicles', component: VehicleListComponent },
  { path: 'edit-vehicle/:id', component: EditVehicleComponent },
  { path: 'add-vehicle', component: AddVehicleComponent },
  { path: '**', component: PageNotFoundComponent}

];
