import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Category } from '../interfaces/category';
import { Observable } from 'rxjs';
import { AttributeEntity } from '../interfaces/attribute';

@Injectable({
  providedIn: 'root',
})
export class CategoryService {
  constructor(private http: HttpClient) {}

  public getAll(): Observable<Category[]> {
    return this.http.get<Category[]>('http://localhost:9000/api/v1/categories');
  }

  public getCategoryById(id: number): Observable<Category> {
    return this.http.get<Category>(
      'http://localhost:9000/api/v1/categories/' + id
    );
  }

  public getAttributesByCategoryId(id: number): Observable<AttributeEntity[]> {
    return this.http.get<AttributeEntity[]>(
      'http://localhost:9000/api/v1/categories/' + id + '/attributes'
    );
  }

  
}
