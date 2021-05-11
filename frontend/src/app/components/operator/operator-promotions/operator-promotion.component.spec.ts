import { ComponentFixture, TestBed } from '@angular/core/testing';

import { OperatorPromotionComponent } from './operator-promotion.component';

describe('OperatorPromotionsComponent', () => {
  let component: OperatorPromotionComponent;
  let fixture: ComponentFixture<OperatorPromotionComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ OperatorPromotionComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(OperatorPromotionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
