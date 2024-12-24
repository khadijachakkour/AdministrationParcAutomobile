import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ListMaintenanceComponent } from './maintenance-list.component';

describe('MaintenanceListComponent', () => {
  let component: ListMaintenanceComponent;
  let fixture: ComponentFixture<ListMaintenanceComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ListMaintenanceComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ListMaintenanceComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
