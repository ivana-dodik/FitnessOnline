import { CommonModule } from '@angular/common';
import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { Router, RouterModule } from '@angular/router';
import { UserDto } from 'src/app/interfaces/user';
import { InboxDto } from 'src/app/interfaces/userMessage';
import { UserService } from 'src/app/services/user.service';
import { UserMessageService } from 'src/app/services/userMessage.service';
import StartToastifyInstance from 'toastify-js';

import { FormsModule, ReactiveFormsModule } from '@angular/forms';

@Component({
  selector: 'app-user-messages',
  standalone: true,
  imports: [RouterModule, CommonModule, FormsModule],
  templateUrl: './user-messages.component.html',
  styleUrl: './user-messages.component.css'
})
export class UserMessagesComponent implements OnInit {
  @ViewChild('dropdown', {static: false}) dropdown?: ElementRef;

  selectedValue: number = 0;

  userUsernameId: UserDto[] = [];

  inboxMessages!: InboxDto[];

  selectedUserId: number = 0;

  constructor(private userMessageService: UserMessageService, private router: Router, private userService: UserService) { }

  ngOnInit(): void {
    this.loadInboxMessages();
    this.loadAllUsers();
  }

  getSelectedValue(){
    this.selectedValue = this.dropdown?.nativeElement.value;
    if (this.selectedValue != 0){
      this.viewCorrespondence(this.selectedValue);
    }
  }

  loadInboxMessages() {
    this.userMessageService.getInbox().subscribe(
      (data: InboxDto[]) => {
       
        this.inboxMessages = data;
      },
      error => {
        StartToastifyInstance({
          text: "Error fetching inbox messages",
          offset: {
              x: 25, 
              y: '85vh' 
          },
        }).showToast();
      }
    );
  }


  viewCorrespondence(senderId: number) {
    this.router.navigate(['/messages', senderId]);
  }



  loadAllUsers(){
    this.userService.getAll().subscribe(
      (data: UserDto[]) => {
        this.userUsernameId = data;
      },
      error => {
        StartToastifyInstance({
          text: "Error fetching all users",
          offset: {
              x: 25, 
              y: '85vh' 
          },
        }).showToast();
      }
    );
    
  }

}
