import { ICustomer } from './../../../../model/customer';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { CustomerService } from 'src/app/pages/customer.service';
import { IUser } from 'src/app/pages/registration/registration/user';
import { UserService } from 'src/app/service/user.service';

@Component({
  selector: 'app-bussines-owner-customer-info',
  templateUrl: './bussines-owner-customer-info.component.html',
  styleUrls: ['./bussines-owner-customer-info.component.css'],
})
export class BussinesOwnerCustomerInfoComponent implements OnInit {
  customer!: ICustomer;
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
    let userId = +this._route.snapshot.paramMap.get('userId')!;
    this._customerService
      .getCustomerByIdForBusinessOwner(userId)
      .subscribe((user) => {
        this.customer = user;
      });
  }
}
