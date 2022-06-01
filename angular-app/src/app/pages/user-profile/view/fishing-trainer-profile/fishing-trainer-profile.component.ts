import { Component, Input, OnInit } from '@angular/core';
import { IUser } from 'src/app/pages/registration/registration/user';

@Component({
  selector: 'app-fishing-trainer-profile',
  templateUrl: './fishing-trainer-profile.component.html',
  styleUrls: ['../user-profile.component.css'],
})
export class FishingTrainerProfileComponent implements OnInit {
  @Input() userId!: number;
  @Input() user!: IUser;

  constructor() {}

  ngOnInit(): void {}

  createDeleteUserRequest() {}
}
