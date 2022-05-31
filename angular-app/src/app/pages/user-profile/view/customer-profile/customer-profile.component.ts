import { IUser } from 'src/app/pages/registration/registration/user';
import { ICustomer } from 'src/app/model/customer';
import { CustomerService } from './../../../customer.service';
import { Component, Input, OnInit } from '@angular/core';

@Component({
  selector: 'app-customer-profile',
  templateUrl: './customer-profile.component.html',
  styleUrls: ['../user-profile.component.css'],
})
export class CustomerProfileComponent implements OnInit {
  @Input() userId!: number;
  @Input() user!: IUser;
  customer!: ICustomer;
  constructor(private _customerService: CustomerService) {}

  ngOnInit(): void {
    this._customerService
      .getCustomerById(this.userId)
      .subscribe((data) => (this.customer = data));
  }
}
