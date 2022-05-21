import { Injectable } from '@angular/core';
import {
  HttpRequest,
  HttpHandler,
  HttpEvent,
  HttpInterceptor,
} from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { AuthenticationService } from '../service/authentication.service';

@Injectable()
export class JwtInterceptor implements HttpInterceptor {
  constructor(private authenticationService: AuthenticationService) {}

  intercept(
    request: HttpRequest<any>,
    next: HttpHandler
  ): Observable<HttpEvent<any>> {
    // add auth header with jwt if user is logged in and request is to api url
    const accesToken = this.authenticationService.getToken();
    const isLoggedIn = this.authenticationService.isLoggedIn();
    const isApiUrl = request.url.startsWith(environment.apiUrl);
    if (isLoggedIn && isApiUrl) {
      console.log('token' + `Bearer ${accesToken}`);
      request = request.clone({
        setHeaders: {
          Authorization: `Bearer ${accesToken}`,
        },
      });
    }

    return next.handle(request);
  }
}
