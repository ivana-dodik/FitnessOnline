// exercise.service.ts
import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ExerciseService {

  private apiKey = 'E7buzwOL7j4aeX33EF8BrQ==GkOVZIefQqgvXYCZ'; 

  constructor(private http: HttpClient) { }

  getExercises(muscle: string): Observable<any[]> {
    const headers = new HttpHeaders().set('x-api-key', this.apiKey);
    return this.http.get<any[]>(`https://api.api-ninjas.com/v1/exercises?muscle=${muscle}`, { headers });
  }
}
