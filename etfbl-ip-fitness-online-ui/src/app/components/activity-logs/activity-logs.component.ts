import { CommonModule, DatePipe, DatePipeConfig } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { Router, RouterModule } from '@angular/router';
import { ActivityLog, ActivityLogDto } from 'src/app/interfaces/activityLog';
import { ActivityLogsService } from 'src/app/services/activityLogs.service';
import { AuthenticationService } from 'src/app/services/authentication.service';
import { UserService } from 'src/app/services/user.service';
import StartToastifyInstance from 'toastify-js';
import { NgxPaginationModule } from 'ngx-pagination';
import * as pdfMake from 'pdfmake/build/pdfmake';
import * as pdfFonts from 'pdfmake/build/vfs_fonts';
import {jsPDF} from 'jspdf';

@Component({
  selector: 'app-activity-logs',
  standalone: true,
  imports: [RouterModule, CommonModule, NgxPaginationModule],
  templateUrl: './activity-logs.component.html',
  styleUrl: './activity-logs.component.css'
})
export class ActivityLogsComponent implements OnInit{
  activityLogs!: ActivityLogDto[];

  p: number = 1;
  isAtBottom: boolean = false;
  howClose: number = 0;

  constructor(private activityLogService: ActivityLogsService, 
    private userService: UserService,
    private authenticationService: AuthenticationService,
    private datePipe: DatePipe,
    private router: Router
  ) {}

  ngOnInit(): void {
    window.addEventListener('scroll', this.scroll, true);
    /*this.authenticationService.getLoggedInUser().subscribe({
      next: (res) => {
        this.authenticationService.isLoggedIn.next(true);
      },
    });*/

    this.activityLogService.getActivityLogs().subscribe({
      next: (res) => {
        this.activityLogs = res;
      },
      error: (e) =>
        StartToastifyInstance({
          text: 'Error while fetching activity logs',
          offset: {
            x: 25,
            y: '85vh',
          },
        }).showToast()
    });
  }

  ngOnDestroy() {
    window.removeEventListener('scroll', this.scroll, true);
  }

  scroll = (event: any): void => {
    this.isAtBottom = false;
    this.howClose =
      (document.body.offsetHeight + window.scrollY) /
      document.body.scrollHeight;
    if (this.howClose > 0.935) {
      this.isAtBottom = true;
    }
  };

  formatDateTime(dateTime: string): string {
    return this.datePipe.transform(dateTime, 'dd/MM/yyyy HH:mm') || '';
  }

  /*
  generatePDF(): void {
    // Create a new jsPDF instance
    const doc = new jsPDF();
  
    // Add username as title
    const title = this.activityLogs[0].username + "'s activity logs:";
    doc.setFontSize(20);
    const titleWidth = doc.getStringUnitWidth(title) * 20 / doc.internal.scaleFactor;
    const titleX = (doc.internal.pageSize.width - titleWidth) / 2;
    const titleY = 20;
    doc.text(title, titleX, titleY);
    const titleUnderlineY = titleY + 2; // Adjust the position for the underline
    doc.setLineWidth(0.5); // Adjust the thickness of the underline
    doc.line(titleX, titleUnderlineY, titleX + titleWidth, titleUnderlineY);
  
    // Set initial y position for the content
    let yOffset = 40;
  
    // Iterate over each activity log starting from the second one
    for (let index = 1; index < this.activityLogs.length; index++) {
      const log = this.activityLogs[index];
      
      // Add a new line between logs
      yOffset += 10;
  
      // Format and add log details
      const formattedDateTime = this.formatDateTime(log.dateTime);
      const formattedDuration = `${log.durationMinutes} minutes`;
      const formattedIntensity = `Intensity: ${log.intensity}`;
      const formattedResults = `Results: ${log.results}`;
  
      // Add formatted details to the PDF
      doc.setFontSize(12);
      doc.text(`Date & Time: ${formattedDateTime}`, 10, yOffset);
      doc.text(`Exercise Type: ${log.exerciseType}`, 10, yOffset + 10);
      doc.text(`Duration: ${formattedDuration}`, 10, yOffset + 20);
      doc.text(formattedIntensity, 10, yOffset + 30);
      doc.text(formattedResults, 10, yOffset + 40);
  
      // Draw a separating line
      const lineY = yOffset + 45; // Adjust the position for the line
      doc.setLineWidth(0.1); // Adjust the thickness of the line
      doc.line(10, lineY, doc.internal.pageSize.width - 10, lineY);
  
      // Increase yOffset for the next log
      yOffset += 50; // Adjust this value as needed
    }
  
    // Save or open the PDF
    doc.save('activity_logs.pdf');
  }*/

  generatePDF(): void {
    // Create a new jsPDF instance
    const doc = new jsPDF();
  
    // Add username as title
    const title = this.activityLogs[0].username + "'s activity logs:";
    doc.setFontSize(20);
    const titleWidth = doc.getStringUnitWidth(title) * 20 / doc.internal.scaleFactor;
    const titleX = (doc.internal.pageSize.width - titleWidth) / 2;
    const titleY = 20;
    doc.text(title, titleX, titleY);
    const titleUnderlineY = titleY + 2; // Adjust the position for the underline
    doc.setLineWidth(0.5); // Adjust the thickness of the underline
    doc.line(titleX, titleUnderlineY, titleX + titleWidth, titleUnderlineY);
  
    // Set initial y position for the content
    let yOffset = 40;
  
    // Iterate over each activity log
    this.activityLogs.forEach((log, index) => {
      // Add a new line between logs
      if(index > 0) { // Skip adding a new line before the first log
        yOffset += 10;
      }
  
      // Format and add log details
      const formattedDateTime = this.formatDateTime(log.dateTime);
      const formattedDuration = `${log.durationMinutes} minutes`;
      const formattedIntensity = `Intensity: ${log.intensity}`;
      const formattedResults = `Results: ${log.results}`;
  
      // Add formatted details to the PDF
      doc.setFontSize(12);
      doc.text(`Date & Time: ${formattedDateTime}`, 10, yOffset);
      doc.text(`Exercise Type: ${log.exerciseType}`, 10, yOffset + 10);
      doc.text(`Duration: ${formattedDuration}`, 10, yOffset + 20);
      doc.text(formattedIntensity, 10, yOffset + 30);
      doc.text(formattedResults, 10, yOffset + 40);
  
      // Draw a separating line
      const lineY = yOffset + 45; // Adjust the position for the line
      doc.setLineWidth(0.1); // Adjust the thickness of the line
      doc.line(10, lineY, doc.internal.pageSize.width - 10, lineY);
  
      // Increase yOffset for the next log
      yOffset += 50; // Adjust this value as needed
    });
  
    // Save or open the PDF
    doc.save('activity_logs.pdf');
  }

  
  goToGraph(){
    this.router.navigate(['/profile/activity-logs/graph']);
  }
  

  
}
