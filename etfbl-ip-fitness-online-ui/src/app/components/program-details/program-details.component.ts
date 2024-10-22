import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Category } from 'src/app/interfaces/category';
import { Program } from 'src/app/interfaces/item';
import { InstructorInfoDto, UserEntity } from 'src/app/interfaces/user';
import { AuthenticationService } from 'src/app/services/authentication.service';
import { CategoryService } from 'src/app/services/category.service';
import { Comment } from 'src/app/interfaces/comment';
import { CommentService } from 'src/app/services/comment.service';
import { ProgramService } from 'src/app/services/program.service';
import { UserService } from 'src/app/services/user.service';
import StartToastifyInstance from 'toastify-js';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { switchMap } from 'rxjs/operators';

@Component({
  selector: 'app-program-details',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './program-details.component.html',
  styleUrl: './program-details.component.css'
})
export class ProgramDetailsComponent implements OnInit {
  now: string = new Date().toLocaleString();
  comment: string = '';
  isModalOpen: boolean = false;
  selectedPaymentMethod: string = '';
  creditCardNumber: string = '';
  deliveryAddress: string = '';
  payPalAccountNumber: string = '';
  creditCardSelected: boolean = false;
  cashSelected: boolean = false;
  payPalTransferSelected: boolean = false;
  canBeBought!: boolean;
  loggedInUser!: UserEntity;


  public comments: Comment[] = [];
  public srcIndex!: number;
  public program!: Program;
  public paymentDetails: string = '';
  public isLoggedIn: boolean = false;
  public category!: Category;
  images: string[] = []
  SERVER_URL: string = 'http://localhost:9000/api/v1/';
  instructor!: InstructorInfoDto;

  
  constructor(
    private programService: ProgramService,
    private route: ActivatedRoute,
    private commentService: CommentService,
    private router: Router,
    private authenticationService: AuthenticationService,
    private categoryService: CategoryService,
    private userService: UserService
  ) {}

  ngOnInit(): void {
    this.srcIndex = 0;

    let id: number = 0;
    this.route.params.subscribe((params) => {
      id = params['id'];
    });

    this.commentService.getCommentsForProgram(id).subscribe({
      next: (c) => (this.comments = c),
      error: (e) => this.router.navigate(['/programs']),
    });

    this.programService.getProgramById(id).subscribe({
      next: (p) => {
        this.program = p;
        this.getInstructor(p.instructorId);
        this.categoryService.getCategoryById(p.categoryId).subscribe({
          next: (res) => (this.category = res),
          error: () =>
            StartToastifyInstance({
              text: 'Error while fetching category',
              offset: {
                x: 25,
                y: '85vh',
              },
            }).showToast(),
        });
      },
      error: (e) => this.router.navigate(['/programs']),
    });

    
    this.programService.getImagesForProgram(id).subscribe({
      next: (res) => { 
        this.images = res.map(i => this.SERVER_URL + i);
        console.log("RUTA1:  " + this.route);
       },
      error: () => {
        console.log("RUTA2:  " + this.route);
        StartToastifyInstance({
          text: 'Error while fetching images',
          offset: {
            x: 25,
            y: '85vh',
          },
        }).showToast();
      }
    })


    this.authenticationService.getLoggedInUser().subscribe({
      next: (res) => {
        if(res) {
          this.authenticationService.isLoggedIn.next(true);
          this.isLoggedIn = true;
          this.loggedInUser = res;
        }
      },
    });
  }

  getInstructor(instructorId: number) {
    this.userService.getInstructorInfo(instructorId).subscribe({
      next: res => {
        this.instructor = res;
      }, 
      error: (err) => {
        console.log(err);
        StartToastifyInstance({
          text: 'Error while fetching instructor info',
          offset: {
            x: 25,
            y: '85vh',
          },
        }).showToast();
      }
    })
  }

  handleNext() {
    this.srcIndex = (this.srcIndex + 1) % this.images.length;
  }

  handlePrevious() {
    if (this.srcIndex == 0) {
      this.srcIndex = this.images.length - 1;
    } else {
      this.srcIndex = (this.srcIndex - 1) % this.images.length;
    }
  }

  leaveComment() {
    if (this.comment != '') {
      this.commentService
        .addCommentForProgram(this.program.id, { content: this.comment })
        .subscribe({
          next: (c) => {
            this.comments = c;
            this.comment = '';
          },
          error: (e) => {
            if (e.status == 401)
              StartToastifyInstance({
                text: 'Please log in before leaving a comment.',
                offset: {
                  x: 25,
                  y: '85vh',
                },
              }).showToast();
          },
        });
    } else {
      StartToastifyInstance({
        text: 'You cannot leave an empty comment!',
        offset: {
          x: 25,
          y: '85vh',
        },
      }).showToast();
    }
  }

  openModal() {
    this.authenticationService.getLoggedInUser().subscribe({
      next: (res) => {
        this.isModalOpen = true;
      },
      error: (err) => {
        this.router.navigate(['/login']);
      },
    });
  }

  closeModal() {
    this.isModalOpen = false;
    this.selectedPaymentMethod = '';
    this.creditCardNumber = '';
    this.deliveryAddress = '';
    this.payPalAccountNumber = '';
  }

  selectPaymentMethod(paymentMethod: string) {
    this.creditCardSelected = false;
    this.cashSelected = false;
    this.payPalTransferSelected = false;

    if (paymentMethod === 'CREDIT_CARD') {
      this.creditCardSelected = true;
    } else if (paymentMethod === 'CASH') {
      this.cashSelected = true;
    } else if (paymentMethod === 'PAYPAL') {
      this.payPalTransferSelected = true;
    }

    this.selectedPaymentMethod = paymentMethod;
  }

  participateInProgram() {
    if (this.paymentDetails.length < 5) {
      StartToastifyInstance({
        text: 'Please enter payment details.',
        offset: {
          x: 25,
          y: '85vh',
        },
      }).showToast();
      return;
    }

    let participationDto = {
      programId: this.program.id,
      paymentMethod: this.selectedPaymentMethod,
      paymentDetails: this.paymentDetails,
    };

    this.programService.getAllParticipated().subscribe({
      next: (programs: Program[]) => {
        let isParticipating: boolean = programs.some(p => p.id === this.program.id);
        
        if (isParticipating) {
          StartToastifyInstance({
            text: 'You are already participating in this program',
            offset: {
              x: 25, 
              y: '85vh',
            },
          }).showToast();
          this.closeModal();
        } else {
          // Ako korisnik nije već učestvovao u programu, tada možete pokrenuti participaciju
          this.programService.participateInProgram(participationDto).subscribe({
            next: (p) => {
              StartToastifyInstance({
                text: 'Participation registered successfully',
                offset: {
                  x: 25, 
                  y: '85vh',
                },
              }).showToast();
              this.closeModal();
              this.canBeBought = false;
            },
            error: (e) =>
              StartToastifyInstance({
                text: 'Please enter valid information.',
                offset: {
                  x: 25, 
                  y: '85vh', 
                },
              }).showToast(),
          });
        }
      }
    });
    
  }

  endProgram(){
    if (confirm('Are you sure you want to end this program?')) {
      this.programService.endProgram(this.program).subscribe(
        () => {
          this.program.available = false;
              
          // Uspešno ažuriranje programa
          StartToastifyInstance({
              text: 'Program successfully ended.',
              offset: {
                  x: 25,
                  y: '85vh',
              },
          }).showToast();
        },
        (error) => {
          StartToastifyInstance({
            text: 'Error occurred while ending the program.',
            offset: {
                x: 25,
                y: '85vh',
            },
          }).showToast();
          console.error(error);
          // Handle error
        }
      );
    }
  }
}
