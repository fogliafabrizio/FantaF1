import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TabellaPilotiComponent } from './tabella-piloti.component';

describe('TabellaPilotiComponent', () => {
  let component: TabellaPilotiComponent;
  let fixture: ComponentFixture<TabellaPilotiComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [TabellaPilotiComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(TabellaPilotiComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
