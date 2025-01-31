import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PrintStatementComponent } from './print-statement.component';

describe('PrintStatementComponent', () => {
  let component: PrintStatementComponent;
  let fixture: ComponentFixture<PrintStatementComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [PrintStatementComponent]
    });
    fixture = TestBed.createComponent(PrintStatementComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
