import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { switchMap } from 'rxjs/operators';
import { LoginDto } from 'src/app/interfaces/login';
import { UserEntity } from 'src/app/interfaces/user';
import { AuthenticationService } from 'src/app/services/authentication.service';
import { UserService } from 'src/app/services/user.service';
import StartToastifyInstance from 'toastify-js';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [ReactiveFormsModule],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent implements OnInit {
  public form: FormGroup = new FormGroup({});

  constructor(
    private formBuilder: FormBuilder,
    private authService: AuthenticationService,
    private router: Router,
    private userService: UserService
  ) {}

  ngOnInit() {
    this.form = this.formBuilder.group({
      username: [null, Validators.required],
      password: [null, Validators.required],
    });
  }

  public login() {
    localStorage.removeItem('user');
    let loginDto: LoginDto = this.form.value;

    this.authService.login(loginDto).subscribe({
      next: (loggedInUser) => {
        if(loggedInUser){
          localStorage.setItem('user', JSON.stringify(loggedInUser));
          //console.log("LOGIN COMPONENT: " + loggedInUser.username + "  " + loggedInUser.password);
          this.userService.checkActivation(loggedInUser.username).subscribe({
            next: (user) => {
              if(!user.activated) {
                this.userService.requestNewEmail().subscribe({
                  next: () => {
                    StartToastifyInstance({
                      text: "Check your email.",
                      offset: {
                        x: 25, 
                        y: '85vh' 
                      },
                    }).showToast()
                  }
                });
              } else {
                this.router.navigate(['/programs']);
              }
            
            },
            error: (e) =>
              StartToastifyInstance({
                text: 'Error while logging in. Invalid credentials.',
                offset: {
                  x: 25,
                  y: '85vh',
                },
              }).showToast()
          });
        } else {
          StartToastifyInstance({
            text: 'Error while logging in. Invalid credentials.',
            offset: {
              x: 25,
              y: '85vh',
            },
          }).showToast()
        }
      },
      error: (e) =>
        StartToastifyInstance({
          text: 'Error while logging in. Invalid credentials.',
          offset: {
            x: 25,
            y: '85vh',
          },
        }).showToast()
    });
    

  }
}
  

  /*public login() {
    localStorage.removeItem('user');
    let loginDto: LoginDto = this.form.value;

    this.authService.login(loginDto).subscribe({
      next: (loggedInUser) => {
        localStorage.setItem('user', JSON.stringify(loggedInUser));
        this.router.navigate(['/programs']);

        console.log("LOGIN COMPONENT: " + loggedInUser.username + "  " + loggedInUser.password);
      },
      error: (e) =>
        StartToastifyInstance({
          text: 'Error while logging in. Invalid credentials.',
          offset: {
            x: 25,
            y: '85vh',
          },
        }).showToast()
    });
  }*/
