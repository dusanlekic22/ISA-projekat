import {
  Directive,
  Input,
  OnChanges,
  OnDestroy,
  OnInit,
  SimpleChanges,
  TemplateRef,
  ViewContainerRef,
} from '@angular/core';
import { UserService } from '../service/user.service';
import { takeUntil } from 'rxjs/operators';

import { Observable, Subject } from 'rxjs';
import { IDirective, IUser } from '../pages/registration/registration/user';

@Directive({
  selector: '[appHasRoles]',
})
export class HasRolesDirective implements OnInit, OnDestroy, OnChanges {
  @Input() appHasRoles!: IDirective;

  stop$ = new Subject();

  isVisible = false;
  constructor(
    private viewContainerRef: ViewContainerRef,
    private templateRef: TemplateRef<any>,
    private userService: UserService
  ) { }
  


  ngOnChanges(changes: SimpleChanges): void {
    console.log(changes.appHasRoles);
    let roles = this.appHasRoles.activeRoles;
        if (!this.appHasRoles.roles) {
          this.viewContainerRef.clear();
        }
        // If the user has the role needed to
        // render this component we can add it
    if (this.appHasRoles.roles !== undefined) {
      if (this.appHasRoles.roles.some((r) => roles.includes(r.name))) {
        // If it is already visible (which can happen if
        // his roles changed) we do not need to add it a second time
        if (!this.isVisible) {
          // We update the `isVisible` property and add the
          // templateRef to the view using the
          // 'createEmbeddedView' method of the viewContainerRef
          this.isVisible = true;
          this.viewContainerRef.createEmbeddedView(this.templateRef);
        }
      }
    } else {
          // If the user does not have the role,
          // we update the `isVisible` property and clear
          // the contents of the viewContainerRef
          this.isVisible = false;
          this.viewContainerRef.clear();
        }
  }

  ngOnInit() {
    // this.userService
    //   .getCurrentUser()
    //   .pipe(takeUntil(this.stop$))
    //   .subscribe((user) => {
        // If he doesn't have any roles, we clear the viewContainerRef
    let roles = this.appHasRoles.activeRoles;
        if (!this.appHasRoles.roles) {
          this.viewContainerRef.clear();
        }
        // If the user has the role needed to
        // render this component we can add it
    if (this.appHasRoles.roles !== undefined) {
      if (this.appHasRoles.roles.some((r) => roles.includes(r.name))) {
        // If it is already visible (which can happen if
        // his roles changed) we do not need to add it a second time
        if (!this.isVisible) {
          // We update the `isVisible` property and add the
          // templateRef to the view using the
          // 'createEmbeddedView' method of the viewContainerRef
          this.isVisible = true;
          this.viewContainerRef.createEmbeddedView(this.templateRef);
        }
      }
    } else {
          // If the user does not have the role,
          // we update the `isVisible` property and clear
          // the contents of the viewContainerRef
          this.isVisible = false;
          this.viewContainerRef.clear();
        }
  }

  

  // Clear the subscription on destroy
  ngOnDestroy() {
    this.stop$.next();
  }
}
