import { TestBed, inject } from '@angular/core/testing';

import { DomainExpertService } from './domain-expert.service';

describe('DomainExpertService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [DomainExpertService]
    });
  });

  it('should be created', inject([DomainExpertService], (service: DomainExpertService) => {
    expect(service).toBeTruthy();
  }));
});
