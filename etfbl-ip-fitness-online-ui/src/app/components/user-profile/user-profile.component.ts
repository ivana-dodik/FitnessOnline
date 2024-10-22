import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { Program } from 'src/app/interfaces/item';
import { ProgramService } from 'src/app/services/program.service';
import StartToastifyInstance from 'toastify-js';
import { ItemsComponent } from '../items/items.component';
import { RouterModule } from '@angular/router';

@Component({
  selector: 'app-user-profile',
  standalone: true,
  imports: [CommonModule, ItemsComponent, RouterModule],
  templateUrl: './user-profile.component.html',
  styleUrl: './user-profile.component.css'
})
export class UserProfileComponent implements OnInit {
  constructor(private programService: ProgramService) {}

  ngOnInit(): void {
    this.getActivePrograms();
  }

  public activePrograms: Program[] = [];
  public participatedPrograms: Program[] = []; //bought
  public instructedPrograms: Program[] = []; //sold

  showingActivePrograms = true;
  showingParticipatedPrograms = false;
  showingInstructedPrograms = false;

  public getActivePrograms() {
    this.participatedPrograms = [];
    this.instructedPrograms = [];

    this.showingActivePrograms = true;
    this.showingParticipatedPrograms = false;
    this.showingInstructedPrograms = false;

    this.programService.getAllCurrentlyInstructing().subscribe({
      next: (p) => (this.activePrograms = p),
      error: (e) =>
        StartToastifyInstance({
          text: 'Error while getting active programs',
          offset: {
            x: 25, 
            y: '85vh',
          },
        }).showToast(),
    });
  }

  public getParticipatedPrograms() {
    this.activePrograms = [];
    this.instructedPrograms = [];

    this.showingActivePrograms = false;
    this.showingParticipatedPrograms = true;
    this.showingInstructedPrograms = false;

    this.programService.getAllParticipated().subscribe({
      next: (p) => (this.participatedPrograms = p),
      error: (e) =>
        StartToastifyInstance({
          text: 'Error while getting bought programs',
          offset: {
            x: 25, 
            y: '85vh',
          },
        }).showToast(),
    });
  }

  public getInstructedPrograms() {
    this.activePrograms = [];
    this.participatedPrograms = [];

    this.showingActivePrograms = false;
    this.showingParticipatedPrograms = false;
    this.showingInstructedPrograms = true;

    this.programService.getAllFinished().subscribe({
      next: (p) => (this.instructedPrograms = p),
      error: (e) =>
        StartToastifyInstance({
          text: 'Error while getting sold programs',
          offset: {
            x: 25, 
            y: '85vh',
          },
        }).showToast(),
    });
  }
}

