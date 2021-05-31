import { ComponentFixture, TestBed } from '@angular/core/testing';

import { OperatorAccountComponent } from './operator-account.component';

describe('OperatorAccountsComponent', () => {
  let component: OperatorAccountComponent;
  let fixture: ComponentFixture<OperatorAccountComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ OperatorAccountComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(OperatorAccountComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
