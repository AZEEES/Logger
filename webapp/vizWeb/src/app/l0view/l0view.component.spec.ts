import { ComponentFixture, TestBed } from '@angular/core/testing';

import { L0viewComponent } from './l0view.component';

describe('L0viewComponent', () => {
  let component: L0viewComponent;
  let fixture: ComponentFixture<L0viewComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ L0viewComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(L0viewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
