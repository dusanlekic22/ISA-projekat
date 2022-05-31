import { IUser } from './../../registration/registration/user';
import { CustomerService } from '../../customer.service';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { UserService } from 'src/app/service/user.service';

@Component({
  selector: 'app-user-profile',
  templateUrl: './user-profile.component.html',
  styleUrls: ['./user-profile.component.css'],
})
export class UserProfileComponent implements OnInit {
  user!: IUser;
  userId!: number;
  roles: string[] = [
    'ROLE_CUSTOMER',
    'ROLE_COTTAGE_OWNER',
    'ROLE_FISHING_TRAINER',
    'ROLE_ADMIN',
  ];
  constructor(
    private _customerService: CustomerService,
    private _userService: UserService,
    private _route: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this._userService.getCurrentUser().subscribe((user) => {
      user.roles.some((role) => {
        this._route.params.subscribe((data) => {
          this.userId = data.id;
          this._userService
            .getCurrentUser()
            .subscribe((data) => (this.user = data));
        });
      });
    });
  }
}
