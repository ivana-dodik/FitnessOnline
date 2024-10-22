import { Provider } from '@angular/core';

import { HTTP_INTERCEPTORS } from '@angular/common/http';
import { AuthorizationInterceptor } from '../interceptors/authorization.interceptor';


export const authorizationInterceptorProvider: Provider =
  { provide: HTTP_INTERCEPTORS, useClass: AuthorizationInterceptor, multi: true };