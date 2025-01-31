import { ComponentFixture, TestBed } from '@angular/core/testing';

import { LaunchMenuComponent } from './launch-menu.component';

describe('LaunchMenuComponent', () => {
  let component: LaunchMenuComponent;
  let fixture: ComponentFixture<LaunchMenuComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [LaunchMenuComponent]
    });
    fixture = TestBed.createComponent(LaunchMenuComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
