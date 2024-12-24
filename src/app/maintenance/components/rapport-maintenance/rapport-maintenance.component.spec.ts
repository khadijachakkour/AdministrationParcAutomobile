import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RapportMaintenanceComponent } from './rapport-maintenance.component';

describe('RapportMaintenanceComponent', () => {
  let component: RapportMaintenanceComponent;
  let fixture: ComponentFixture<RapportMaintenanceComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [RapportMaintenanceComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(RapportMaintenanceComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
