import { Injectable } from '@angular/core';
import { CanActivate, Router, UrlTree } from '@angular/router';
import { Observable } from 'rxjs';
import { AuthenticationService } from '../services/authentication.service';
import { UserService } from '../services/user.service';
import StartToastifyInstance from 'toastify-js';

@Injectable({
  providedIn: 'root'
})
export class AuthenticationGuard implements CanActivate {
  constructor(
    private authService: AuthenticationService,
    private userService: UserService,
    private router: Router
  ) {}

  canActivate(): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {
    return new Observable<boolean>((observer) => {
      this.authService.getLoggedInUser().subscribe({
        next: (user) => {
          if (!user.activated) {
            this.userService.requestNewEmail().subscribe({
              next: () => {
                StartToastifyInstance({
                  text: "Check your email.",
                  offset: {
                    x: 25, 
                    y: '85vh' 
                  },
                }).showToast()
                observer.next(false);
              }
            });
          } else {
            observer.next(true);
          }
        },
        error: () => {
          this.router.navigate(['/login']);
          observer.next(false);
        },
      });
    });
  }
}






/*import { inject } from '@angular/core';
import { CanActivateFn, Router, UrlTree } from '@angular/router';
import { AuthenticationService } from '../services/authentication.service';
import { UserService } from '../services/user.service';
import StartToastifyInstance from 'toastify-js';

const isAuthenticated = (): boolean => {
  const authService = inject(AuthenticationService);
  const userService = inject(UserService);
  const router = inject(Router);

  authService.getLoggedInUser().subscribe({
    next: (user) => {
      if (!user.activated) {
        userService.requestNewEmail().subscribe({
          //next: () => router.navigate(['/login/activation']),
         next: () => {
          StartToastifyInstance({
            text: "Check your email.",
            offset: {
              x: 25, 
              y: '85vh' 
            },
          }).showToast()
         }
        });
        return false;
      }
      else {
        return true;
      }
    },
    error: () => {
      router.navigate(['/login']);
    },
  });

  return true;
};

export const AuthenticationGuard: CanActivateFn = isAuthenticated;
*/
