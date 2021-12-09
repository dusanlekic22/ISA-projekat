import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-registration',
  templateUrl: './registration.component.html',
  styleUrls: ['./registration.component.css'],
})
export class RegistrationComponent implements OnInit {
  firstName!: string;
  lastname!: string;
  username!: string;

  constructor() {}

  ngOnInit(): void {}
}
