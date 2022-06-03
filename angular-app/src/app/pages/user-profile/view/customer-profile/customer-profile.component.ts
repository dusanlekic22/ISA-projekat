import { UserService } from './../../../../service/user.service';
import { IUser } from 'src/app/pages/registration/registration/user';
import { ICustomer } from 'src/app/model/customer';
import { CustomerService } from './../../../customer.service';
import { Component, Input, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { DeleteDialogComponent } from 'src/app/components/delete-dialog/delete-dialog.component';

@Component({
  selector: 'app-customer-profile',
  templateUrl: './customer-profile.component.html',
  styleUrls: ['../user-profile.component.css'],
})
export class CustomerProfileComponent implements OnInit {
  @Input() userId!: number;
  @Input() user!: IUser;
  customer!: ICustomer;
  constructor(
    private _customerService: CustomerService,
    public dialog: MatDialog
  ) {}

  ngOnInit(): void {
    this._customerService
      .getCustomerById(this.userId)
      .subscribe((data) => (this.customer = data));
  }
  scroll(el: HTMLElement) {
    el.scrollIntoView();
  }
  openDialog(): void {
    const dialogRef = this.dialog.open(DeleteDialogComponent, {
      width: '250px',
      data: { email: this.user.email },
    });

    dialogRef.afterClosed().subscribe((result) => {
      console.log('The dialog was closed');
    });
  }

  createDeleteUserRequest() {}
}
