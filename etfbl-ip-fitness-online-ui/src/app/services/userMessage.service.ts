import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { AddUserMessageDto, InboxDto, InboxMessageDto, UserMessageDetailsDto } from "../interfaces/userMessage";
import { Observable } from "rxjs";

@Injectable ({
    providedIn: 'root',
})

export class UserMessageService{

    constructor(private http: HttpClient) { }

    public sendNewUserMessage(newMessageDto: AddUserMessageDto): Observable<AddUserMessageDto> {
        return this.http.post<AddUserMessageDto>(
        'http://localhost:9000/api/v1/user-messages',
        newMessageDto
        );
    }

    public getAllReceivedMessages(): Observable<UserMessageDetailsDto[]> {
        return this.http.get<UserMessageDetailsDto[]>('http://localhost:9000/api/v1/user-messages/received');
    }
    
    public getInbox(): Observable<InboxDto[]> {
    return this.http.get<InboxDto[]>('http://localhost:9000/api/v1/user-messages/inbox');
    }
    
    public getAllMessagesBetweenTwoUsers(senderId: number): Observable<InboxMessageDto[]> {
    return this.http.get<InboxMessageDto[]>(`http://localhost:9000/api/v1/user-messages/inbox/${senderId}`);
    }
}


