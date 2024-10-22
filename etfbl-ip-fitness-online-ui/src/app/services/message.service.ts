import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { AddMessageDto } from '../interfaces/message';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class MessageService {

  constructor(private http: HttpClient) { }

  public sendNewMessage(newMessageDto: AddMessageDto): Observable<AddMessageDto> {
    return this.http.post<AddMessageDto>(
      'http://localhost:9000/api/v1/messages',
      newMessageDto
    );
  }
}
