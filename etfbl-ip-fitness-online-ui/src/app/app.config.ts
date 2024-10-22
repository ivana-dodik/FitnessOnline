import { ApplicationConfig, importProvidersFrom } from '@angular/core';
import { provideRouter } from '@angular/router';

import { routes } from './app.routes';
import { provideClientHydration } from '@angular/platform-browser';
import { HttpClientModule, provideHttpClient, withFetch } from '@angular/common/http';
import { authorizationInterceptorProvider } from './provider/authorization-interceptor.provider';
import { AuthenticationGuard } from './guards/authentication.guard';
import { NgxPaginationModule } from 'ngx-pagination';
import { DatePipe } from '@angular/common';
import { provideAnimationsAsync } from '@angular/platform-browser/animations/async';


export const appConfig: ApplicationConfig = {
  providers: [
    provideRouter(routes), 
    provideClientHydration(), 
    provideHttpClient(withFetch()),
    importProvidersFrom(HttpClientModule),
    authorizationInterceptorProvider,
    AuthenticationGuard,
    NgxPaginationModule,
    DatePipe, provideAnimationsAsync()
  ]
};