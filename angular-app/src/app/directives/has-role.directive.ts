import {
  Directive,
  Input,
  OnDestroy,
  OnInit,
  OnChanges,
  TemplateRef,
  ViewContainerRef,
  SimpleChanges,
} from '@angular/core';
import { UserService } from '../service/user.service';
import { takeUntil } from 'rxjs/operators';

import { Subject } from 'rxjs';
import { IUser } from '../pages/registration/registration/user';

@Directive({
  selector: '[appHasRole]',
})
export class HasRoleDirective implements OnInit, OnChanges {
  @Input() appHasRole!: Array<string>;

  loggedUser!: IUser;

  stop$ = new Subject();

  isVisible = false;

  constructor(
    private viewContainerRef: ViewContainerRef,
    private templateRef: TemplateRef<any>,
    private userService: UserService
  ) { }
  
  
  ngOnChanges(changes: SimpleChanges): void {
     this.userService
      .getCurrentUser()
      // .pipe(takeUntil(this.stop$))
      .subscribe((user) => {
        // If he doesn't have any roles, we clear the viewContainerRef
        this.loggedUser = user;
        let roles = user.roles;
        if (!roles) {
          this.viewContainerRef.clear();
        }
        // If the user has the role needed to
        // render this component we can add it
        if (roles.some((r) => this.appHasRole.includes(r.name))) {
          // If it is already visible (which can happen if
          // his roles changed) we do not need to add it a second time
          if (!this.isVisible) {
            // We update the `isVisible` property and add the
            // templateRef to the view using the
            // 'createEmbeddedView' method of the viewContainerRef
            this.isVisible = true;
            this.viewContainerRef.createEmbeddedView(this.templateRef);
          
          } else {
            // If the user does not have the role,
            // we update the `isVisible` property and clear
            // the contents of the viewContainerRef
            this.isVisible = false;
            this.viewContainerRef.clear();
          }
        }
      });
  }

  ngOnInit() {
    this.userService
      .getCurrentUser()
      // .pipe(takeUntil(this.stop$))
      .subscribe((user) => {
        // If he doesn't have any roles, we clear the viewContainerRef
        this.loggedUser = user;
        let roles = user.roles;
        if (!roles) {
          this.viewContainerRef.clear();
        }
        // If the user has the role needed to
        // render this component we can add it
        if (roles.some((r) => this.appHasRole.includes(r.name))) {
          // If it is already visible (which can happen if
          // his roles changed) we do not need to add it a second time
          if (!this.isVisible) {
            // We update the `isVisible` property and add the
            // templateRef to the view using the
            // 'createEmbeddedView' method of the viewContainerRef
            this.isVisible = true;
            this.viewContainerRef.createEmbeddedView(this.templateRef);
          
          } else {
            // If the user does not have the role,
            // we update the `isVisible` property and clear
            // the contents of the viewContainerRef
            this.isVisible = false;
            this.viewContainerRef.clear();
          }
        }
      });
  }


  // Clear the subscription on destroy
  ngOnDestroy() {
    this.stop$.next();
  }
}
