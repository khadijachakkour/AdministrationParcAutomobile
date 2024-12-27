import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ReservationsfrontComponent } from './reservationsfront.component';

describe('ReservationsfrontComponent', () => {
  let component: ReservationsfrontComponent;
  let fixture: ComponentFixture<ReservationsfrontComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ReservationsfrontComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(ReservationsfrontComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
