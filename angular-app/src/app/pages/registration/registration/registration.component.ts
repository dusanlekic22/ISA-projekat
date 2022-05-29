import { IAddress } from './../../../model/address';
import { Component, OnInit } from '@angular/core';
import {
  FormGroup,
  FormControl,
  FormBuilder,
  Validators,
} from '@angular/forms';
import { RegistrationService } from '../registration.service';
import { IUser } from './user';
import { Observable } from 'rxjs';
import {} from 'googlemaps';

@Component({
  selector: 'app-registration',
  templateUrl: './registration.component.html',
  styleUrls: ['./registration.component.css'],
})
export class RegistrationComponent implements OnInit {
  firstFormGroup!: FormGroup;
  id!: number;
  firstName!: string;
  lastname!: string;
  username!: string;
  email!: string;
  contact!: string;
  password!: string;
  confirmPassword!: string;
  location!: string;
  user!: IUser;
  errorMessage!: string;
  city!: string;
  country!: string;
  street!: string;
  longitude!: number;
  latitude!: number;

  constructor(
    private _formBuilder: FormBuilder,
    private _registrationService: RegistrationService
  ) {}

  ngOnInit(): void {
    this.firstFormGroup = this._formBuilder.group({
      firstCtrl: ['', Validators.required],
    });
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
        this.latitude = a.latLng.lat().toFixed(4);
        this.longitude = a.latLng.lng().toFixed(4);
        this._registrationService
          .getCityandCountry(
            a.latLng.lat().toFixed(4),
            a.latLng.lng().toFixed(4)
          )
          .subscribe((data) => {
            this.city = data.address.city;
            this.country = data.address.country;
            this.street = data.address.road;
          });
      });
      google.maps.event.addListener(map, 'click', (event) => {
        position: event.latLng,
          (this.location = event.latLng.lat() + ', ' + event.latLng.lng());
        marker.setPosition(new google.maps.LatLng(event.latLng));
        this.latitude = event.latLng.lat().toFixed(4);
        this.longitude = event.latLng.lng().toFixed(4);

        this._registrationService
          .getCityandCountry(
            event.latLng.lat().toFixed(4),
            event.latLng.lng().toFixed(4)
          )
          .subscribe((data) => {
            this.city = data.address.city;
            this.country = data.address.country;
            this.street = data.address.road;
            console.log(data);
          });
      });
    }
  }

  register() {
    this.user = {
      id: 0,
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
        latitude: this.latitude,
        longitude: this.longitude,
      },
      roles: [],
    };

    this._registrationService.submitForm(this.user).subscribe({
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
