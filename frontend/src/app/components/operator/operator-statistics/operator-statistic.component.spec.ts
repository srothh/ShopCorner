import { ComponentFixture, TestBed } from '@angular/core/testing';

import { OperatorStatisticComponent } from './operator-statistic.component';

describe('OperatorStatisticsComponent', () => {
  let component: OperatorStatisticComponent;
  let fixture: ComponentFixture<OperatorStatisticComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ OperatorStatisticComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(OperatorStatisticComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
