import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { TermBankComponent } from './term-bank.component';

describe('TermBankComponent', () => {
  let component: TermBankComponent;
  let fixture: ComponentFixture<TermBankComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ TermBankComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(TermBankComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
