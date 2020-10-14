import { ComponentFixture, TestBed } from '@angular/core/testing';

import { L2viewComponent } from './l2view.component';

describe('L2viewComponent', () => {
  let component: L2viewComponent;
  let fixture: ComponentFixture<L2viewComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ L2viewComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(L2viewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
