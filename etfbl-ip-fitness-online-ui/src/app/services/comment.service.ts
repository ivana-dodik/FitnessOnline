import { Injectable } from '@angular/core';
import { Observable } from 'rxjs/internal/Observable';
import { HttpClient } from '@angular/common/http';
import { AddCommentDto, Comment } from '../interfaces/comment';

@Injectable({
  providedIn: 'root',
})
export class CommentService {
  constructor(private http: HttpClient) {}

  public getCommentsForProgram(id: number): Observable<Comment[]> {
    return this.http.get<Comment[]>(
      'http://localhost:9000/api/v1/programs/' + id + '/comments'
    );
  }

  public addCommentForProgram(
    id: number,
    newCommentDto: AddCommentDto
  ): Observable<Comment[]> {
    return this.http.post<Comment[]>(
      'http://localhost:9000/api/v1/programs/' + id + '/comments',
      newCommentDto
    );
  }
}
