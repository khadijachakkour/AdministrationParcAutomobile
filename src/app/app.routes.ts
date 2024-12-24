import { Routes } from '@angular/router';
import { VehicleListComponent } from './vehicule/components/vehicle-list/vehicle-list.component';
import { AddVehicleComponent } from './vehicule/components/add-vehicle/add-vehicle.component';
import { PageNotFoundComponent } from './core/page-not-found/page-not-found.component';
import { DashboardComponent } from './core/dashboard/dashboard.component';
import { ListMaintenanceComponent } from './maintenance/components/maintenance-list/maintenance-list.component';
import { EditMaintenanceComponent } from './maintenance/components/edit-maintenance/edit-maintenance.component';
import { EditVehicleComponent } from './vehicule/components/edit-vehicle/edit-vehicle.component';
import { AddMaintenanceComponent } from './maintenance/components/add-maintenance/add-maintenance.component';


export const routes: Routes = [
  { path: '', component: DashboardComponent },
  { path: 'dashboard', component: DashboardComponent },
  { path: 'vehicles', component: VehicleListComponent },
  { path: 'edit-vehicle/:id', component: EditVehicleComponent},
  { path: 'edit-maintenance/:id', component: EditMaintenanceComponent }, 
  { path: 'add-vehicle', component: AddVehicleComponent },
  { path: 'add-maintenance', component: AddMaintenanceComponent },
  { path: 'maintenances', component: ListMaintenanceComponent },
  { path: '**', component: PageNotFoundComponent}

];
