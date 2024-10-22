import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DailySuggestionsComponent } from './daily-suggestions.component';

describe('DailySuggestionsComponent', () => {
  let component: DailySuggestionsComponent;
  let fixture: ComponentFixture<DailySuggestionsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [DailySuggestionsComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(DailySuggestionsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
