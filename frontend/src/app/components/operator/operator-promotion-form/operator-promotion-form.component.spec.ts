import { ComponentFixture, TestBed } from '@angular/core/testing';

import { OperatorPromotionFormComponent } from './operator-promotion-form.component';

describe('OperatorPromotionFormComponent', () => {
  let component: OperatorPromotionFormComponent;
  let fixture: ComponentFixture<OperatorPromotionFormComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ OperatorPromotionFormComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(OperatorPromotionFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
