import { TestBed } from '@angular/core/testing';

import { CustomInterceptor } from './custom-interceptor.service';

describe('CustomInterceptor', () => {
  let service: CustomInterceptor;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(CustomInterceptor);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
