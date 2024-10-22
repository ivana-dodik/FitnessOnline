import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { EditUserDto, EditUserPasswordDto, UserEntity } from 'src/app/interfaces/user';
import { AuthenticationService } from 'src/app/services/authentication.service';
import { UserService } from 'src/app/services/user.service';
import StartToastifyInstance from 'toastify-js';

@Component({
  selector: 'app-user-account-edit',
  standalone: true,
  imports: [FormsModule, ReactiveFormsModule],
  templateUrl: './user-account-edit.component.html',
  styleUrl: './user-account-edit.component.css'
})
export class UserAccountEditComponent implements OnInit {
  public editProfileForm: FormGroup = new FormGroup({});
  public changePasswordForm: FormGroup = new FormGroup({});

  constructor(private formBuilder: FormBuilder, private authenticationService: AuthenticationService, private userService: UserService) {}

  private avatar!: File;
  isModalOpen: boolean = false;
  loggedInUser!: UserEntity;
  SERVER_URL: string = 'http://localhost:9000/api/v1';
  avatarUrl!: string;

  ngOnInit(): void {
    this.authenticationService.getLoggedInUser().subscribe({
      next: (res) => {
        this.loggedInUser = res;
        this.editProfileForm.reset(this.loggedInUser);
        this.authenticationService.isLoggedIn.next(true);
        this.avatarUrl = this.SERVER_URL + res.avatarUrl;
      },
    });

    this.editProfileForm = this.formBuilder.group({
      firstName: [null, Validators.required],
      lastName: [null, Validators.required],
      city: [null, Validators.required],
      email: [
        null,
        [Validators.required,
        Validators.pattern('^[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,4}$')]
      ],
    });

    this.changePasswordForm = this.formBuilder.group({
      currentPassword: [null, Validators.required],
      newPassword: [null, Validators.required],
      repeatPassword: [null, Validators.required],
    });
  }

  public edit(form: any) {
    if (!this.editProfileForm.valid) {
      StartToastifyInstance({
        text: 'Please fill out all fields',
        offset: {
          x: 25,
          y: '85vh',
        },
      }).showToast();
      return;
    }

    let editUserDto: EditUserDto = this.editProfileForm.value;
    this.userService.editUserProfile(editUserDto).subscribe({
      next: res => {
        this.loggedInUser = res;
        this.editProfileForm.reset(this.loggedInUser);
        StartToastifyInstance({
          text: 'Successfully edited user',
          offset: {
            x: 25,
            y: '85vh',
          },
        }).showToast();
      },
      error: () => {
        StartToastifyInstance({
          text: 'Error while edting user',
          offset: {
            x: 25,
            y: '85vh',
          },
        }).showToast();
      }
    })

    if (this.avatar) {
      this.userService.uploadUserAvatar(this.loggedInUser.id, this.avatar).subscribe({
        next: (res) => this.avatarUrl = this.SERVER_URL + res.url
      })
    }
  }

  public onFileSelected(event: any) {
    this.avatar = <File>event.target.files[0];
  }

  openModal() {
    this.isModalOpen = true;
  }

  closeModal() {
    this.isModalOpen = false;
  }

  changePassword() {
    if (this.changePasswordForm.value.newPassword != this.changePasswordForm.value.repeatPassword) {
      StartToastifyInstance({
        text: 'Entered passwords do not match',
        offset: {
          x: 25,
          y: '85vh',
        },
      }).showToast();

      return;
    }

    let editUserPassword: EditUserPasswordDto = {
      currentPassword: this.changePasswordForm.value.currentPassword,
      newPassword: this.changePasswordForm.value.newPassword
    }

    this.userService.changeUserPassword(editUserPassword).subscribe({
      next: res => {
        localStorage.removeItem("user");
        localStorage.setItem("user", JSON.stringify(res));
        this.changePasswordForm.reset();
        this.isModalOpen = false;
        StartToastifyInstance({
          text: 'Successfully changed password',
          offset: {
            x: 25,
            y: '85vh',
          },
        }).showToast();
      },
      error: () => {
        StartToastifyInstance({
          text: 'Error while changing passwrod',
          offset: {
            x: 25,
            y: '85vh',
          },
        }).showToast();
      }
    })
  }

  openImage() {
    window.open(
      this.avatarUrl, "_blank");
  }
}
