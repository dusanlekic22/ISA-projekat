import { emptyAddress } from './../../../model/address';
import { Component, OnInit } from '@angular/core';
import { AdminService } from 'src/app/service/admin.service';
import { IUser } from '../../registration/registration/user';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-register-admin',
  templateUrl: './register-admin.component.html',
  styleUrls: ['./register-admin.component.css'],
})
export class RegisterAdminComponent implements OnInit {
  admin: IUser = {
    id: 0,
    username: '',
    password: '',
    firstName: '',
    lastName: '',
    email: '',
    phoneNumber: '',
    address: emptyAddress,
    roles: [],
    deleted: false,
  };

  confirmPassword: string = '';

  constructor(
    private adminService: AdminService,
    private toastr: ToastrService
  ) {}

  ngOnInit(): void {}

  register() {
    this.adminService.addAdmin(this.admin).subscribe(
      () => {
        this.toastr.success('Added new admin');
      },
      (error) => this.toastr.error('Failed')
    );
  }
}
