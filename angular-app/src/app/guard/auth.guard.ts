import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot, UrlTree } from '@angular/router';
import { Observable } from 'rxjs';
import { Role } from '../model/role';
import { IUser } from '../pages/registration/registration/user';
import { AuthenticationService } from '../service/authentication.service';
import { UserService } from '../service/user.service';

@Injectable({
  providedIn: 'root'
})
export class AuthGuard implements CanActivate {

  constructor(
    private router: Router,
    private userService: UserService
  ) { }

  canActivate(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {
    const user = this.userService.userValue;
    if (user) {
      // check if route is restricted by role
      if (route.data.roles && this.notAuthorized(user, route)) {
        // role not authorised so redirect to home page
        this.router.navigate(['']);
        return false;
      }

      // authorised so return true
      return true;
    }

    // not logged in so redirect to login page with the return url
    this.router.navigate([''], { queryParams: { returnUrl: state.url } });
    return false;
  }

  notAuthorized(user: IUser, route: ActivatedRouteSnapshot): boolean {
    var notAuthorized = true;
    user.roles.forEach(role => {
      if (route.data.roles.includes(role.name)) notAuthorized = false;
    });
    return notAuthorized;
  }

}
