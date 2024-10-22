import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router, RouterModule } from '@angular/router';
import { LoginDto } from 'src/app/interfaces/login';
import { UserService } from 'src/app/services/user.service';
import StartToastifyInstance from 'toastify-js';

@Component({
  selector: 'app-confirm-registration',
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: './confirm-registration.component.html',
  styleUrl: './confirm-registration.component.css'
})
export class ConfirmRegistrationComponent implements OnInit {
  
  activated: boolean = false;
  token: string | null = null; 

  notActivated: boolean= false;

  constructor(private route: ActivatedRoute, private router: Router, private userService: UserService) {} 
  
  ngOnInit(): void {
    this.route.queryParams.subscribe(params => {
      const token = params['token']; // Dobij token iz URL-a
      console.log(this.token);
      if (token) {
        /*let user: LoginDto= {
          username: '',
          password: ''
        };
        if (typeof localStorage !== 'undefined') {
           user = JSON.parse(localStorage.getItem('user') || '{}');
        }*/
        // Pozovi servis za potvrdu registracije sa tokenom
        this.userService.validate(token).subscribe(
          () => {this.activated = true;},
          error => {
            this.notActivated = true;
            console.error(error);
            //this.showErrorPopup();
          }

         /* {
          next: () => {
            StartToastifyInstance({
              text: "Your profile has been successfully confirmed and activated.",
              offset: {
                x: 25,
                y: '85vh'
              }
            }).showToast();
             //Prikaži dugme Continue i omogući preusmeravanje na /programs
            this.showContinueButton();
          },
          error: () => {
            // Prikaži iskačući prozor sa porukom i preusmeri na /login nakon 4 sekunde
            this.showErrorPopup();
          }
        }*/
      );
      } else {
        // Ukoliko nema tokena u URL-u, obavesti korisnika da je link nevažeći
        StartToastifyInstance({
          text: "Invalid registration confirmation link.",
          offset: {
            x: 25,
            y: '85vh'
          }
        }).showToast();
        // Možeš ga rutirati na početnu ili neku drugu odgovarajuću stranicu
        this.router.navigate(['/news']);
      }
    });
  }

  navigateToPrograms() {
    this.router.navigate(['/programs']);
  }

  navigateToLogin() {
    this.router.navigate(['/login']);
  }
  
  showContinueButton() {
    // Ovo može biti funkcija koja prikaže dugme i omogući preusmeravanje na /programs
  }
  
  showErrorPopup() {
    StartToastifyInstance({
      text: "Your profile hasn't been activated, please try again.",
      offset: {
        x: 25,
        y: '85vh'
      }
    }).showToast();
    // Nakon 4 sekunde preusmeri korisnika na /login stranicu
    setTimeout(() => {
      this.router.navigate(['/login']);
    }, 4000);
  }

  /*ngOnInit(): void {
    this.token = this.route.snapshot.queryParamMap.get('token');
    if(this.token){
      this.activateUser(this.token);
    }
    else {
      this.router.navigate(['/programs']);
    }
  }

  public activateUser(token: string) {
    this.userService.validate(token).subscribe(
      () => {this.activated = true;},
      error => {
        console.error(error);
        this.router.navigate(['/login']);
      }
    )
  }
  
  navigateToPrograms() {
    this.router.navigate(['/programs']);
  }

  navigateToLogin() {
    this.router.navigate(['/login']);
  }*/
}
/*
  ngOnInit(): void {
    this.route.queryParams.subscribe(params => {
      const token = params['token']; // Dobij token iz URL-a
      if (token) {
        // Pozovi servis za potvrdu registracije sa tokenom
        this.userService.validate(token).subscribe({
          next: () => {
            StartToastifyInstance({
              text: "Registration confirmed successfully!",
              offset: {
                x: 25,
                y: '85vh'
              }
            }).showToast();
            // Ovde možeš rutirati korisnika na željenu stranicu nakon potvrde registracije
            this.router.navigate(['/login']);
          },
          error: () => {
            StartToastifyInstance({
              text: "Error confirming registration. Please try again later.",
              offset: {
                x: 25,
                y: '85vh'
              }
            }).showToast();
          }
        });
      } else {
        // Ukoliko nema tokena u URL-u, obavesti korisnika da je link nevažeći
        StartToastifyInstance({
          text: "Invalid registration confirmation link.",
          offset: {
            x: 25,
            y: '85vh'
          }
        }).showToast();
        // Možeš ga rutirati na početnu ili neku drugu odgovarajuću stranicu
        this.router.navigate(['/']);
      }
    });
  }
  */