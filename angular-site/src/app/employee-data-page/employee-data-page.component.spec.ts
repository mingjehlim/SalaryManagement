import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EmployeeDataPageComponent } from './employee-data-page.component';

describe('EmployeeDataPageComponent', () => {
  let component: EmployeeDataPageComponent;
  let fixture: ComponentFixture<EmployeeDataPageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ EmployeeDataPageComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(EmployeeDataPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
