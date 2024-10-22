import { Component, OnInit } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { AuthenticationService } from 'src/app/services/authentication.service';
import { MessageService } from 'src/app/services/message.service';
import StartToastifyInstance from 'toastify-js';

@Component({
  selector: 'app-contact-form',
  standalone: true,
  imports: [ReactiveFormsModule, FormsModule],
  templateUrl: './contact-form.component.html',
  styleUrl: './contact-form.component.css'
})
export class ContactFormComponent implements OnInit {

  constructor(private messageService: MessageService, private authenticationService: AuthenticationService) {}

  ngOnInit(): void {
    this.authenticationService.getLoggedInUser().subscribe({
      next: (res) => {
        this.authenticationService.isLoggedIn.next(true);
      },
    });
  }

  message: string = '';
  sendMessage() {

    if (this.message.trim() != '') {
      this.messageService.sendNewMessage({ content: this.message.trim() }).subscribe({
        next: res => { 
          this.message = '';
          StartToastifyInstance({
            text: 'Message successfully sent.',
            offset: {
              x: 25,
              y: '85vh',
            },
          }).showToast();  
        }
      })
    } else {
      StartToastifyInstance({
        text: 'You cannot send an empty message!',
        offset: {
          x: 25,
          y: '85vh',
        },
      }).showToast();
    }
  }
}
