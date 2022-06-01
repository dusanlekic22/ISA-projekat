import { Component, Input, OnInit } from '@angular/core';
import { IUser } from 'src/app/pages/registration/registration/user';

@Component({
  selector: 'app-owner-cottage-profile',
  templateUrl: './owner-cottage-profile.component.html',
  styleUrls: ['../user-profile.component.css'],
})
export class OwnerCottageProfileComponent implements OnInit {
  @Input() userId!: number;
  @Input() user!: IUser;

  constructor() {}

  ngOnInit(): void {}

  createDeleteUserRequest() {}
}
