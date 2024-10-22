import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule, ReactiveFormsModule } from '@angular/forms'; 
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { RegisterUserDto } from 'src/app/interfaces/user';
import { AuthenticationService } from 'src/app/services/authentication.service';
import { UserService } from 'src/app/services/user.service';
import StartToastifyInstance from 'toastify-js';
import { CommonModule } from '@angular/common';
import { LOADIPHLPAPI } from 'dns';
import { log } from 'console';


@Component({
  selector: 'app-registration',
  standalone: true,
  imports: [CommonModule,ReactiveFormsModule, FormsModule],
  templateUrl: './registration.component.html',
  styleUrl: './registration.component.css'
})
export class RegistrationComponent implements OnInit {
  public form: FormGroup = new FormGroup({});
  private avatar!: File;

  firstNumber!: number;
  secondNumber!: number;
  correctResult!: number;

  captchaResponse!: string;

  captchaInput!: string;

  constructor(private formBuilder: FormBuilder, private userService: UserService, private router: Router, private authenticationService: AuthenticationService) {}


  ngOnInit(): void {
    localStorage.removeItem("user");
    this.form = this.formBuilder.group({
      firstName: [null, Validators.required],
      lastName: [null, Validators.required],
      city: [null, Validators.required],
      username: [null, Validators.required],
      password: [null, Validators.required],
      repeatedPassword: [null, Validators.required],
      email: [ null, [Validators.required, Validators.pattern('^[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,4}$')]],
      captchaInput: [null, Validators.required]
    });
    this.generateRandomNumbers();
  }

  generateRandomNumbers(): void {
    this.firstNumber = Math.floor(Math.random() * 50);
    this.secondNumber = Math.floor(Math.random() * 50);
    this.correctResult = this.firstNumber + this.secondNumber;
  }

  public register(){
    if(this.form.value.password != this.form.value.repeatedPassword) {
      StartToastifyInstance({
        text: "Please enter same passwords!",
        offset: {
          x: 25,
          y: '85vh'
        }
      }).showToast();
      return;
    }

    let registerUserDto: RegisterUserDto = this.form.value;
    registerUserDto.email = this.form.controls['email'].value;
    registerUserDto.answer = this.correctResult;

    // Check if captchaInput control exists and is not null
    const captchaInputControl = this.form.get('captchaInput');
    if (captchaInputControl) {
      registerUserDto.captchaResponse = captchaInputControl.value;
    } else {
      console.error("Captcha input control not found!");
      return;
    }

    //console.log(registerUserDto.answer);
    //console.log(registerUserDto.captchaResponse);

    this.userService.registerUser(registerUserDto).subscribe({
      next: (res) => {
        let loggedIn = {username: registerUserDto.username, password: registerUserDto.password};
        localStorage.removeItem("user");
        localStorage.setItem("user", JSON.stringify(loggedIn));

        console.log("Registracija: " + loggedIn.username + "  " + loggedIn.password);

        if(this.avatar){
          this.authenticationService.getLoggedInUser().subscribe({
            next: res => {
              this.userService.uploadUserAvatar(res.id, this.avatar).subscribe({
                next: () => {}
              });
              this.message();
            }
          });
        }

        else {
          this.message();
        }
      },


      error: (err) => StartToastifyInstance({
        text: "Error while registering, please try again",
        offset: {
          x: 25, 
          y: '85vh' 
        },
      }).showToast()
    })

  }

  public message(){
    //poruka: pogledaj mejl
    StartToastifyInstance({
      text: "Check your email.",
      offset: {
        x: 25, 
        y: '85vh' 
      },
    }).showToast()
  }

  public onFileSelected(event: any) {
    this.avatar = <File>event.target.files[0];
  }


}
