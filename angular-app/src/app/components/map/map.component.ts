import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { IAddress } from 'src/app/model/address';
import { RegistrationService } from 'src/app/pages/registration/registration.service';
import {} from 'googlemaps';

@Component({
  selector: 'app-map',
  templateUrl: './map.component.html',
  styleUrls: ['./map.component.css'],
})
export class MapComponent implements OnInit {
  @Output() addressEvent = new EventEmitter<IAddress>();
  @Input() edit: boolean = false;
  @Input() lat: number = 45.25;
  @Input() long: number = 19.82;
  address: IAddress = {
    street: '',
    city: '',
    country: '',
    latitude: 0,
    longitude: 0,
  };

  constructor(private _registrationService: RegistrationService) {}

  ngOnInit(): void {
    if(this.lat != 0)
    this.mapInit();
  }

  mapInit() {
    var latlng = new google.maps.LatLng(this.lat, this.long); //Set the default location of map
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
      if (this.edit) {
        let x: string;
        google.maps.event.addListener(marker, 'dragend', (a: any) => {
          console.log(a);
          this.address.latitude = a.latLng.lat().toFixed(4);
          this.address.longitude = a.latLng.lng().toFixed(4);
          this._registrationService
            .getCityandCountry(
              a.latLng.lat().toFixed(4),
              a.latLng.lng().toFixed(4)
            )
            .subscribe((data) => {
              this.address.city = data.address.city;
              this.address.street = data.address.road;
              this.address.country = data.address.country;
              this.addressEvent.emit(this.address);
            });
        });
        google.maps.event.addListener(map, 'click', (event) => {
          position: event.latLng,
            marker.setPosition(new google.maps.LatLng(event.latLng));
          this.address.latitude = event.latLng.lat().toFixed(4);
          this.address.longitude = event.latLng.lng().toFixed(4);
          this._registrationService
            .getCityandCountry(
              event.latLng.lat().toFixed(4),
              event.latLng.lng().toFixed(4)
            )
            .subscribe((data) => {
              console.log(data);
              this.address.city = data.address.city;
              this.address.street = data.address.road;
              this.address.country = data.address.country;
              this.addressEvent.emit(this.address);
            });
        });
      }
    }
  }
}
