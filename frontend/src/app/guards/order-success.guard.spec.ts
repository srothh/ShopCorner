import { TestBed } from '@angular/core/testing';

import { OrderSuccessGuard } from './order-success.guard';

describe('OrderSuccessGuard', () => {
  let guard: OrderSuccessGuard;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    guard = TestBed.inject(OrderSuccessGuard);
  });

  it('should be created', () => {
    expect(guard).toBeTruthy();
  });
});
