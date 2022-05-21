import { IUser } from './../../registration/registration/user';
import { CustomerService } from '../../customer.service';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-customer-profile',
  templateUrl: './customer-profile.component.html',
  styleUrls: ['./customer-profile.component.css'],
})
export class CustomerProfileComponent implements OnInit {
  customer!: IUser;
  customerId!: number;
  role: string = 'ROLE_CUSTOMER';
  constructor(
    private _customerService: CustomerService,
    private _route: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this._route.params.subscribe((data) => {
      this.customerId = data.id;
      this._customerService
        .getCustomerById(this.customerId)
        .subscribe((data) => (this.customer = data));
    });
  }
}
