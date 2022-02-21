import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { CottageService } from '../cottage.service';
import { ICottage } from './cottage';


@Component({
  selector: 'app-cottage-profile',
  templateUrl: './cottage-profile.component.html',
  styleUrls: ['../cotage-style.css'],
})
export class CottageProfileComponent implements OnInit {
  cottage: ICottage = {  id : 0,
    name : '',
    address : '',
    promoDescription : '',
    bedCount : 0,
    roomCount : 0,
    cottageRules : '',
    cottageImage : '' };
  cottageId!: number;
  initialImage = 'https://havanatursa.com/assets/images/carousel/Hoteles.webp';
  imagePickerConf: object = {
    borderRadius: '4px',
    language: 'en',
    width: '320px',
    height: '240px',
  };
  imageObject: Array<object> = [
    {
      image: '../assets/img/theme/team-3-800x800.jpg',
      thumbImage: 'assets/img/theme/profile-cover.jpg',
      alt: 'alt of image',
    },
    {
      image: '../assets/img/theme/team-3-800x800.jpg',
      thumbImage: 'assets/img/theme/profile-cover.jpg',
      alt: 'alt of image',
    },
    {
      image: '../assets/img/theme/team-3-800x800.jpg',
      thumbImage: 'assets/img/theme/profile-cover.jpg',
      alt: 'alt of image',
    },
    {
      image: '../assets/img/theme/team-3-800x800.jpg',
      thumbImage: 'assets/img/theme/profile-cover.jpg',
      alt: 'alt of image',
    },
    {
      image: '../assets/img/theme/team-3-800x800.jpg',
      thumbImage: 'assets/img/theme/profile-cover.jpg',
      alt: 'alt of image',
    },
    {
      image: '../assets/img/theme/team-3-800x800.jpg',
      thumbImage: 'assets/img/theme/profile-cover.jpg',
      alt: 'alt of image',
    },

    {
      image: '../assets/img/theme/team-3-800x800.jpg',
      thumbImage: 'assets/img/theme/profile-cover.jpg',
      alt: 'alt of image',
    },
   /* {
      image: '.../iOe/xHHf4nf8AE75h3j1x64ZmZ//Z==', // Support base64 image
      thumbImage: '.../iOe/xHHf4nf8AE75h3j1x64ZmZ//Z==', // Support base64 image
      alt: 'Image alt', //Optional: You can use this key if want to show image with alt
      order: 1, //Optional: if you pass this key then slider images will be arrange according @input: slideOrderType
    }*/
  ];

  constructor(private _route: ActivatedRoute,
    private _cottageService: CottageService) {}

  ngOnInit(): void {
    this.cottageId = +this._route.snapshot.paramMap.get('cottageId')!;
    this._cottageService.getCottageById(this.cottageId).subscribe(
      cottage => this.cottage = cottage
    );
  }

  onImageChange(event: string): void {}
}
