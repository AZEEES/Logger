import { ComponentFixture, TestBed } from '@angular/core/testing';

import { L1viewComponent } from './l1view.component';

describe('L1viewComponent', () => {
  let component: L1viewComponent;
  let fixture: ComponentFixture<L1viewComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ L1viewComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(L1viewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
