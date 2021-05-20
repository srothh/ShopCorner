import {TestBed} from '@angular/core/testing';

import {PreventCustomerLoginGuard} from './prevent-customer-login-guard.service';

describe('PreventLoginGuard', () => {
  let guard: PreventCustomerLoginGuard;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    guard = TestBed.inject(PreventCustomerLoginGuard);
  });

  it('should be created', () => {
    expect(guard).toBeTruthy();
  });
});
