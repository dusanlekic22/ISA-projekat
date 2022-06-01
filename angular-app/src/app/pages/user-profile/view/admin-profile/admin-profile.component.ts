import { IUser } from './../../../registration/registration/user';
import { Component, Input, OnInit } from '@angular/core';

@Component({
  selector: 'app-admin-profile',
  templateUrl: './admin-profile.component.html',
  styleUrls: ['../user-profile.component.css'],
})
export class AdminProfileComponent implements OnInit {
  @Input() userId!: number;
  @Input() user!: IUser;

  constructor() {}

  ngOnInit(): void {}

  createDeleteUserRequest() {}
}
