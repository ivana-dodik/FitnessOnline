import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router, RouterModule } from '@angular/router';
import { AddActivityLogDto } from 'src/app/interfaces/activityLog';
import { ActivityLogsService } from 'src/app/services/activityLogs.service';
import StartToastifyInstance from 'toastify-js';

@Component({
  selector: 'app-add-activity-log',
  standalone: true,
  imports: [CommonModule, FormsModule, ReactiveFormsModule, RouterModule],
  templateUrl: './add-activity-log.component.html',
  styleUrl: './add-activity-log.component.css'
})
export class AddActivityLogComponent implements OnInit{

  constructor(private router: Router, private activityLogService: ActivityLogsService, private formBuilder: FormBuilder) {}
  
  public activityLogForm: FormGroup = new FormGroup({});
  addActivityLogDto!: AddActivityLogDto;

  ngOnInit(): void {
    this.activityLogForm = this.formBuilder.group({
      exerciseType: ['', Validators.required],
      durationMinutes: [0, Validators.required],
      intensity: ['', Validators.required],
      results: [0, Validators.required]
    });
  }

  


  submitActivityLog(){
    if (!this.activityLogForm.valid) {
      StartToastifyInstance({
        text: 'Please enter all the information before proceeding',
        offset: {
          x: 25,
          y: '85vh',
        },
      }).showToast();
      return;
    }

    this.addActivityLogDto = this.activityLogForm.value;
    this.activityLogService.addNewActivityLog(this.addActivityLogDto).subscribe({
      next: (res) => {
        StartToastifyInstance({
          text: 'Successfully added activity log.',
          offset: {
            x: 25,
            y: '85vh',
          },
        }).showToast();
        this.router.navigate(['profile', 'activity-logs']);

      }, 
      error: () => {
        StartToastifyInstance({
          text: 'Error while adding activity log.',
          offset: {
            x: 25,
            y: '85vh',
          },
        }).showToast();
      }
    });

  }

}
