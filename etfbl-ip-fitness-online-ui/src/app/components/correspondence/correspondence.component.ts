import { CommonModule, DatePipe } from '@angular/common';
import { Component, Input, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { UserEntity } from 'src/app/interfaces/user';
import { InboxDto, InboxMessageDto, AddUserMessageDto } from 'src/app/interfaces/userMessage';
import { AuthenticationService } from 'src/app/services/authentication.service';
import { UserService } from 'src/app/services/user.service';
import { UserMessageService } from 'src/app/services/userMessage.service';
import StartToastifyInstance from 'toastify-js';


@Component({
  selector: 'app-correspondence',
  standalone: true,
  imports: [CommonModule, DatePipe, FormsModule],
  templateUrl: './correspondence.component.html',
  styleUrl: './correspondence.component.css'
})
export class CorrespondenceComponent implements OnInit {


  senderId!: number;
  messages!: InboxMessageDto[];

  user!: UserEntity;

  
  messageText: string = '';

  constructor(private route: ActivatedRoute, 
    private userMessageService: UserMessageService, 
    private userService: UserService,
    private datePipe: DatePipe,
    private authenticationService: AuthenticationService
  ) { }

  ngOnInit(): void {
    this.authenticationService.getLoggedInUser().subscribe({
      next: (res) => {
        this.authenticationService.isLoggedIn.next(true);
      },
    });

    this.route.params.subscribe(params => {
      this.senderId = +params['senderId'];
      this.userService.getUserById(this.senderId).subscribe(
        (data) => {
          this.user = data;
        },
        error => {
          console.log('Error fetching sender:', error);
          StartToastifyInstance({
            text: "Error fetching sender",
            offset: {
                x: 25, 
                y: '85vh' 
            },
          }).showToast();
        }
      );
      this.loadCorrespondence();
    });
  }

  loadCorrespondence() {
    this.userMessageService.getAllMessagesBetweenTwoUsers(this.senderId).subscribe(
      (data: InboxMessageDto[]) => {
        this.messages = data;
      },
      error => {
        console.log('Error fetching correspondence:', error);
        StartToastifyInstance({
          text: "Error fetching correspondence",
          offset: {
              x: 25, 
              y: '85vh' 
          },
        }).showToast();
      }
    );
  }

  formatDateTime(dateTime: Date): string {
    return this.datePipe.transform(dateTime, 'dd/MM/yyyy HH:mm') || '';
  }
  

  sendMessage(){
    if(this.messageText.trim() != ''){
      var userMessage: AddUserMessageDto = {
        content: this.messageText.trim(),
        receiverId: this.senderId
      };
      this.userMessageService.sendNewUserMessage(userMessage).subscribe({
        next: res => {
          var newMessage : InboxMessageDto = {
            isMe: true,
            content: this.messageText.trim(),
            dateTime: new Date
          }
          this.messages.push(newMessage);
          this.messageText = '';
          StartToastifyInstance({
            text: 'Message successfully sent.',
            offset: {
              x: 25,
              y: '85vh',
            },
          }).showToast(); 
          //location.reload();
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
