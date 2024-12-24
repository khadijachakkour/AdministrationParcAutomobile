import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { ListMaintenanceComponent } from './components/maintenance-list/maintenance-list.component';




@NgModule({
  declarations: [ListMaintenanceComponent],
  imports: [
    CommonModule,
    MaintenanceModule,
    FormsModule,
    ReactiveFormsModule,
  ]
})
export class MaintenanceModule { }
