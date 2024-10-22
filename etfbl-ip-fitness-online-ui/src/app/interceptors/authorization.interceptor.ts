import { Injectable } from '@angular/core';
import {
  HttpRequest,
  HttpHandler,
  HttpEvent,
  HttpInterceptor,
} from '@angular/common/http';
import { Observable } from 'rxjs';
import { LoginDto } from '../interfaces/login';

@Injectable()
export class AuthorizationInterceptor implements HttpInterceptor {
  constructor() {}

  intercept(
    request: HttpRequest<unknown>,
    next: HttpHandler
  ): Observable<HttpEvent<unknown>> {
    
    if (request.url.startsWith('https://api.api-ninjas.com/v1/exercises') || request.url.startsWith('https://feeds.feedburner.com/AceFitFacts')) {
      return next.handle(request); // Return the request unchanged
    }


    // Check if localStorage is available (browser environment)
    if (typeof localStorage !== 'undefined') {
      let user: LoginDto | null = null;
      const userString: string | null = localStorage.getItem('user');
      if (userString) {
        user = JSON.parse(userString);
      }

      if ((request.headers.get('Content-Type') == 'multipart/form-data')) {
        request = request.clone({
          headers: request.headers.delete('Content-Type'),
        });
      } else {
        request = request.clone({
          headers: request.headers.append('Content-Type', 'application/json'),
        });
      }

      //if (user?.username && user?.password) {
      if (user && user.username && user.password){
        request = request.clone({
          headers: request.headers.append('X-Auth-Username', user.username),
        });

        request = request.clone({
          headers: request.headers.append('X-Auth-Password', user.password),
        });

        console.log("Interceptor:  " + user.username + "  " + user.password);
        console.log("X-Auth-Username header:", request.headers.get('X-Auth-Username'));
        console.log("X-Auth-Password header:", request.headers.get('X-Auth-Password'));
      }
    }
    // Proceed with the modified request
    return next.handle(request);
    
  }
}
