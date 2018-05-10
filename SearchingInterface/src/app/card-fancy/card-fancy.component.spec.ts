import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CardFancyComponent } from './card-fancy.component';

describe('CardFancyComponent', () => {
  let component: CardFancyComponent;
  let fixture: ComponentFixture<CardFancyComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CardFancyComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CardFancyComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
