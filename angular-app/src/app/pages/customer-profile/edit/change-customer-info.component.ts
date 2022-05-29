import { RegistrationService } from './../../registration/registration.service';
import { Component, OnInit } from '@angular/core';
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
  selector: 'app-change-customer-info',
  templateUrl: './change-customer-info.component.html',
  styleUrls: ['./change-customer-info.component.css'],
})
export class ChangeCustomerInfoComponent implements OnInit {
  firstFormGroup!: FormGroup;
  firstName!: string;
  lastname!: string;
  username!: string;
  email!: string;
  contact!: string;
  password!: string;
  confirmPassword!: string;
  location!: string;
  longitude!: number;
  latitude!: number;
  user!: IUser;
  errorMessage!: string;
  city!: string;
  country!: string;
  street!: string;
  customerId!: number;

  constructor(
    private _formBuilder: FormBuilder,
    private _userService: UserService,
    private _registrationService: RegistrationService,
    private _route: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.mapInit();
    this._route.params.subscribe((data) => {
      this.customerId = data.id;
    });
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
        this.latitude = a.latLng.lat().toFixed(4);
        this.longitude = a.latLng.lng().toFixed(4);
        this._registrationService
          .getCityandCountry(
            a.latLng.lat().toFixed(4),
            a.latLng.lng().toFixed(4)
          )
          .subscribe((data) => {
            this.city = data.address.city;
            this.street = data.address.road;
            this.country = data.address.country;
          });
      });
      google.maps.event.addListener(map, 'click', (event: any) => {
        position: event.latLng,
          (this.location = event.latLng.lat() + ', ' + event.latLng.lng());
        marker.setPosition(new google.maps.LatLng(event.latLng));
        this.latitude = event.latLng.lat().toFixed(4);
        this.longitude = event.latLng.lng().toFixed(4);
        console.log('lokacija' + this.location);
        this._registrationService
          .getCityandCountry(
            event.latLng.lat().toFixed(4),
            event.latLng.lng().toFixed(4)
          )
          .subscribe((data) => {
            console.log(data);
            this.city = data.address.city;
            this.street = data.address.road;
            this.country = data.address.country;
          });
      });
    }
  }
  
  update() {
    this.user = {
      id: this.customerId,
      username: this.username,
      password: this.password,
      firstName: this.firstName,
      lastName: this.lastname,
      email: this.email,
      phoneNumber: this.contact,
      address: {
        street: this.street,
        city: this.city,
        country: this.country,
        longitude: this.longitude,
        latitude: this.latitude,
      },
      roles: [],
    };

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
