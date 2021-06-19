import { ComponentFixture, TestBed } from '@angular/core/testing';

import { OperatorOrderSettingsComponent } from './operator-order-settings.component';

describe('OperatorOrderSettingsComponent', () => {
  let component: OperatorOrderSettingsComponent;
  let fixture: ComponentFixture<OperatorOrderSettingsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ OperatorOrderSettingsComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(OperatorOrderSettingsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
