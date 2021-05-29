import { TestBed } from '@angular/core/testing';

import { OperatorAuthService } from './operator-auth.service';

describe('OperatorAuthService', () => {
  let service: OperatorAuthService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(OperatorAuthService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
