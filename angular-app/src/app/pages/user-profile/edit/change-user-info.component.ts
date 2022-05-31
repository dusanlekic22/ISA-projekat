import { RegistrationService } from '../../registration/registration.service';
import { Component, Input, OnInit } from '@angular/core';
import { UserService } from 'src/app/service/user.service';
import {
  FormGroup,
  FormControl,
  FormBuilder,
  Validators,
} from '@angular/forms';
import { IUser } from '../../registration/registration/user';
import { Observable } from 'rxjs';
import { ActivatedRoute } from '@angular/router';
import { NumberLiteralType } from 'typescript';

@Component({
  selector: 'app-change-user-info',
  templateUrl: './change-user-info.component.html',
  styleUrls: ['./change-user-info.component.css'],
})
export class ChangeUserInfoComponent implements OnInit {
  @Input() user!: IUser;
  firstFormGroup!: FormGroup;
  confirmPassword!: string;
  location!: string;
  errorMessage!: string;
  @Input() userId!: number;

  constructor(
    private _formBuilder: FormBuilder,
    private _userService: UserService,
    private _registrationService: RegistrationService,
    private _route: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.mapInit();
  }

  mapInit() {
    var latlng = new google.maps.LatLng(45.2447, 19.8474); //Set the default location of map
    let element = document.getElementById('map');
    if (element !== null) {
      var map = new google.maps.Map(element, {
        center: latlng,

        zoom: 11, //The zoom value for map

        mapTypeId: google.maps.MapTypeId.ROADMAP,
      });

      var marker = new google.maps.Marker({
        position: latlng,

        map: map,

        title: 'Place the marker for your location!', //The title on hover to display

        draggable: true, //this makes it drag and drop
      });

      let x: string;
      google.maps.event.addListener(marker, 'dragend', (a: any) => {
        console.log(a);

        this.location =
          a.latLng.lat().toFixed(4) + ', ' + a.latLng.lng().toFixed(4); //Place the value in input box
        console.log('lokacija' + this.location);
        this.user.address.latitude = a.latLng.lat().toFixed(4);
        this.user.address.longitude = a.latLng.lng().toFixed(4);
        this._registrationService
          .getCityandCountry(
            a.latLng.lat().toFixed(4),
            a.latLng.lng().toFixed(4)
          )
          .subscribe((data) => {
            this.user.address.city = data.address.city;
            this.user.address.street = data.address.road;
            this.user.address.country = data.address.country;
          });
      });
      google.maps.event.addListener(map, 'click', (event: any) => {
        position: event.latLng,
          (this.location = event.latLng.lat() + ', ' + event.latLng.lng());
        marker.setPosition(new google.maps.LatLng(event.latLng));
        this.user.address.latitude = event.latLng.lat().toFixed(4);
        this.user.address.longitude = event.latLng.lng().toFixed(4);
        console.log('lokacija' + this.location);
        this._registrationService
          .getCityandCountry(
            event.latLng.lat().toFixed(4),
            event.latLng.lng().toFixed(4)
          )
          .subscribe((data) => {
            console.log(data);
            this.user.address.city = data.address.city;
            this.user.address.street = data.address.road;
            this.user.address.country = data.address.country;
          });
      });
    }
  }

  update() {
    this._userService.updateUser(this.user).subscribe({
      next: (data) => {
        this.user = data;
        console.log('pozvan' + data);
      },
      error: (error) => {
        this.errorMessage = error.message;
        console.error('There was an error!', error);
      },
    });
  }
}
