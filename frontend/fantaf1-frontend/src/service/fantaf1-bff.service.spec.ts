import { TestBed } from '@angular/core/testing';

import { Fantaf1BffService } from './fantaf1-bff.service';

describe('Fantaf1BffService', () => {
  let service: Fantaf1BffService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(Fantaf1BffService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
