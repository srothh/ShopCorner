import { ComponentFixture, TestBed } from '@angular/core/testing';

import { OperatorEditAccountComponent } from './operator-edit-account.component';

describe('OperatorEditAccountComponent', () => {
  let component: OperatorEditAccountComponent;
  let fixture: ComponentFixture<OperatorEditAccountComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ OperatorEditAccountComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(OperatorEditAccountComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
