import { TestBed, inject } from '@angular/core/testing';

import { TermBankService } from './term-bank.service';

describe('TermBankService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [TermBankService]
    });
  });

  it('should be created', inject([TermBankService], (service: TermBankService) => {
    expect(service).toBeTruthy();
  }));
});
