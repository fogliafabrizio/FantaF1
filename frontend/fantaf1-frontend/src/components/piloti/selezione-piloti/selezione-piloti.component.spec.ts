import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SelezionePilotiComponent } from './selezione-piloti.component';

describe('SelezionePilotiComponent', () => {
  let component: SelezionePilotiComponent;
  let fixture: ComponentFixture<SelezionePilotiComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [SelezionePilotiComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(SelezionePilotiComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
