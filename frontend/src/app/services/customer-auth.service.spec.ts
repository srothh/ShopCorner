import {TestBed} from '@angular/core/testing';

import {CustomerAuthService} from './customer-auth.service';

describe('AuthService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: CustomerAuthService = TestBed.inject(CustomerAuthService);
    expect(service).toBeTruthy();
  });
});
