import { CommonModule } from '@angular/common';
import { Component, ElementRef, Input, OnInit, Renderer2, ViewChild } from '@angular/core';
import { Router, RouterModule } from '@angular/router';
import { Program } from 'src/app/interfaces/item';
import { ProgramService } from 'src/app/services/program.service';
import { UserService } from 'src/app/services/user.service';
import StartToastifyInstance from 'toastify-js';

@Component({
  selector: 'app-program-card',
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: './program-card.component.html',
  styleUrl: './program-card.component.css'
})
export class ProgramCardComponent implements OnInit {
  private id!: number;
  public thumbnail !: string;
  SERVER_URL: string = 'http://localhost:9000/api/v1/';

  @ViewChild('deleteConfirmationModal')
  deleteConfirmationModal!: ElementRef;
  constructor(
    private renderer: Renderer2,
    private programService: ProgramService,
    private router: Router,
    private userService: UserService
  ) {}


  @Input()
  removeItemCallback!: Function;
  @Input()
  program!: Program;
  @Input()
  isProgramDeleteable: boolean = false;
  isModalOpen: boolean = false;

  ngOnInit(): void {
    this.programService.getThumbnailForProgram(this.program.id).subscribe({
      next: res => {
        this.thumbnail = this.SERVER_URL + res.url;
      }, 
      error: (err) => {
        console.log(err);
        StartToastifyInstance({
          text: 'Error while fetching thumbnail',
          offset: {
            x: 25,
            y: '85vh',
          },
        }).showToast();
      }
    });
  } 

  public onClick(event: any): void {
    this.id = this.program.id;
  }

  public confirmDeleteProgram() {
    this.showDeleteConfirmationModal();
  }

  /*
  showDeleteConfirmationModal(): void {
    this.isModalOpen = true;
    this.renderer.addClass(this.deleteConfirmationModal.nativeElement, 'show');
    this.renderer.setStyle(
      this.deleteConfirmationModal.nativeElement,
      'display',
      'block'
    ); // Show the modal backdrop
  }
  */

  showDeleteConfirmationModal(): void {
    this.isModalOpen = true;
    if (this.deleteConfirmationModal) {
      this.renderer.addClass(this.deleteConfirmationModal.nativeElement, 'show');
      this.renderer.setStyle(
        this.deleteConfirmationModal.nativeElement,
        'display',
        'block'
      ); // Show the modal backdrop
    } else {
      console.error('Delete confirmation modal is not available.');
    }
  }
  

  deleteProgram(): void {
    this.programService.deleteItemById(this.program.id).subscribe({
      next: () => {
        StartToastifyInstance({
          text: 'Successfully deleted program',
          offset: {
            x: 25,
            y: '85vh',
          },
        }).showToast();
        this.removeItemCallback(this.program);
        this.closeModal();
        return;
      },
      error: () => {
        StartToastifyInstance({
          text: 'Error while deleting program',
          offset: {
            x: 25,
            y: '85vh',
          },
        });
      },
    });
  }
  /*
  closeModal() {
    this.isModalOpen = false;
    this.renderer.removeClass(
      this.deleteConfirmationModal.nativeElement,
      'show'
    );
    this.renderer.setStyle(
      this.deleteConfirmationModal.nativeElement,
      'display',
      'none'
    );
  }
  */
  
  closeModal() {
    this.isModalOpen = false;
    if (this.deleteConfirmationModal) {
      this.renderer.removeClass(
        this.deleteConfirmationModal.nativeElement,
        'show'
      );
      this.renderer.setStyle(
        this.deleteConfirmationModal.nativeElement,
        'display',
        'none'
      );
    } else {
      console.error('Delete confirmation modal is not available.');
    }
  }

  setCanBeBought() {
    localStorage.setItem(
      'canBeBought',
      JSON.stringify(!this.isProgramDeleteable)
    );
  }
}