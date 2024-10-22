import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AddActivityLogComponent } from './add-activity-log.component';

describe('AddActivityLogComponent', () => {
  let component: AddActivityLogComponent;
  let fixture: ComponentFixture<AddActivityLogComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AddActivityLogComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(AddActivityLogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
