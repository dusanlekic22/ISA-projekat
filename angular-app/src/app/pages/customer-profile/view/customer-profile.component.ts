import { IUser } from './../../registration/registration/user';
import { CustomerService } from '../../customer.service';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { UserService } from 'src/app/service/user.service';

@Component({
  selector: 'app-customer-profile',
  templateUrl: './customer-profile.component.html',
  styleUrls: ['./customer-profile.component.css'],
})
export class CustomerProfileComponent implements OnInit {
  customer!: IUser;
  customerId!: number;
  roles: string[] = ['ROLE_CUSTOMER', 'ROLE_COTTAGE_OWNER'];
  constructor(
    private _customerService: CustomerService,
    private _userService: UserService,
    private _route: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this._userService.getCurrentUser().subscribe((user) => {
      user.roles.some((role) => {
        this._route.params.subscribe((data) => {
          this.customerId = data.id;
          if (role.name != 'ROLE_CUSTOMER') {
            this._customerService
              .getCustomerByIdForBusinessOwner(this.customerId)
              .subscribe((data) => (this.customer = data));
          } else {
            this._customerService
              .getCustomerById(this.customerId)
              .subscribe((data) => (this.customer = data));
          }
        });
        
      });
    });
  }
}
