import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { LoginDto } from '../interfaces/login';
import { Observable } from 'rxjs';
import { EditUserDto, EditUserPasswordDto, RegisterUserDto, InstructorInfoDto, UserEntity, UserDto } from '../interfaces/user';
import { ImageUrlDto } from '../interfaces/image';
import { Category } from '../interfaces/category';

@Injectable({
  providedIn: 'root',
})
export class UserService {
  constructor(private http: HttpClient) {}

  public getAll(): Observable<UserDto[]> {
    return this.http.get<UserDto[]>('http://localhost:9000/api/v1/users');
  }

  public getUserById(id: number): Observable<UserEntity> {
    return this.http.get<UserEntity>('http://localhost:9000/api/v1/users/details/' + id);
  }

  public checkActivation(username: string): Observable<UserEntity> {
    return this.http.get<UserEntity>(
      `http://localhost:9000/api/v1/users/check-activation/${username}`
    );
  }

  public registerUser(registerUserDto: RegisterUserDto): Observable<LoginDto> {
    return this.http.post<LoginDto>(
      'http://localhost:9000/api/v1/users',
      registerUserDto
    );
  }

  public validate(activationToken: string): Observable<LoginDto> {
    let user: LoginDto = {
      username: '',
      password: ''
    }
    if (typeof localStorage !== 'undefined') {
       user = JSON.parse(localStorage.getItem('user') || '{}');
    }

    console.log("user service:  " + user.username + "  " + user.password);

    let headers = new HttpHeaders({
      'Content-Type': 'application/json',
      'X-Auth-Username': user.username,
      'X-Auth-Password': user.password
    });

    return this.http.post<LoginDto>(
      'http://localhost:9000/api/v1/users/confirm-profile',
      {activationToken},
    );
  }

  public requestNewEmail(): Observable<UserEntity> {
    return this.http.get<UserEntity>('http://localhost:9000/api/v1/users/activation-reset');
  }

  public editUserProfile(editUserDto: EditUserDto): Observable<UserEntity> {
    return this.http.patch<UserEntity>('http://localhost:9000/api/v1/users', editUserDto);
  }

  public changeUserPassword(editUserPasswordDto: EditUserPasswordDto): Observable<LoginDto> {
    return this.http.patch<LoginDto>('http://localhost:9000/api/v1/users/change-password', editUserPasswordDto);
  }

  public getInstructorInfo(id: number): Observable<InstructorInfoDto> {
    return this.http.get<InstructorInfoDto>('http://localhost:9000/api/v1/users/' + id);
  }

  public getUserAvatar(id: number): Observable<ImageUrlDto> {
    return this.http.get<ImageUrlDto>('http://localhost:9000/api/v1/resources/users/' + id + '/avatar');
  }

  public uploadUserAvatar(id: number, file: File): Observable<ImageUrlDto> {
    let headers = new HttpHeaders();
    let formData= new FormData();

    const blob = new Blob([file], { type: file.type })
    formData.append('files', blob, file.name)
    headers = headers.set('Content-Type', 'multipart/form-data');

    return this.http.post<ImageUrlDto>('http://localhost:9000/api/v1/resources/users/' + id, formData, { headers: headers });
  }

  public subscribeToCategory(categoryId: number): Observable<any>{
    return this.http.post<any>('http://localhost:9000/api/v1/categories/subscribe', categoryId);
  }

  public unsubscribeFromCategory(categoryId: number): Observable<any>{
    return this.http.post<any>('http://localhost:9000/api/v1/categories/unsubscribe', categoryId);
  }

  public getSubscribedCategoriesByUserId(): Observable<Category[]> {
    return this.http.get<Category[]>('http://localhost:9000/api/v1/users/user/categories');
  }

  public getUnsubscribedCategoriesByUserId(): Observable<Category[]> {
    return this.http.get<Category[]>('http://localhost:9000/api/v1/users/user/categoriesu');
  }
  
}
