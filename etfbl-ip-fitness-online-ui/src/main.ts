import { bootstrapApplication } from '@angular/platform-browser';
import { appConfig } from './app/app.config';
import { AppComponent } from './app/app.component';

import { AuthorizationInterceptor } from './app/interceptors/authorization.interceptor';
import { HTTP_INTERCEPTORS } from '@angular/common/http';
import { platformBrowserDynamic } from '@angular/platform-browser-dynamic';


// Dodavanje interceptor-a
const providers = [
  { provide: HTTP_INTERCEPTORS, useClass: AuthorizationInterceptor, multi: true }
];

bootstrapApplication(AppComponent, appConfig)
  .catch((err) => console.error(err));
