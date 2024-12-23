import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ParcDashboardComponent } from './parc-dashboard.component';

describe('ParcDashboardComponent', () => {
  let component: ParcDashboardComponent;
  let fixture: ComponentFixture<ParcDashboardComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ParcDashboardComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(ParcDashboardComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
