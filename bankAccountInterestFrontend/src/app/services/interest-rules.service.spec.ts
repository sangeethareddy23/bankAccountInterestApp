import { TestBed } from '@angular/core/testing';

import { InterestRulesService } from './interest-rules.service';

describe('InterestRulesService', () => {
  let service: InterestRulesService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(InterestRulesService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
