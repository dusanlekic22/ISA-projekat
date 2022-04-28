import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-cottage-profile',
  templateUrl: './cottage-profile.component.html',
  styleUrls: ['../../cotage-style.css'],
})
export class CottageProfileComponent implements OnInit {
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
      title: 'title of image',
    },
    {
      image: '../assets/img/theme/team-3-800x800.jpg',
      thumbImage: 'assets/img/theme/profile-cover.jpg',
      alt: 'alt of image',
      title: 'title of image',
    },
    {
      image: '../assets/img/theme/team-3-800x800.jpg',
      thumbImage: 'assets/img/theme/profile-cover.jpg',
      alt: 'alt of image',
      title: 'title of image',
    },
    {
      image: '../assets/img/theme/team-3-800x800.jpg',
      thumbImage: 'assets/img/theme/profile-cover.jpg',
      alt: 'alt of image',
      title: 'title of image',
    },
    {
      image: '../assets/img/theme/team-3-800x800.jpg',
      thumbImage: 'assets/img/theme/profile-cover.jpg',
      alt: 'alt of image',
      title: 'title of image',
    },
    {
      image: '../assets/img/theme/team-3-800x800.jpg',
      thumbImage: 'assets/img/theme/profile-cover.jpg',
      alt: 'alt of image',
      title: 'title of image',
    },

    {
      image: '../assets/img/theme/team-3-800x800.jpg',
      thumbImage: 'assets/img/theme/profile-cover.jpg',
      alt: 'alt of image',
      title: 'title of image',
    },
    {
      image: '.../iOe/xHHf4nf8AE75h3j1x64ZmZ//Z==', // Support base64 image
      thumbImage: '.../iOe/xHHf4nf8AE75h3j1x64ZmZ//Z==', // Support base64 image
      title: 'Image title', //Optional: You can use this key if want to show image with title
      alt: 'Image alt', //Optional: You can use this key if want to show image with alt
      order: 1, //Optional: if you pass this key then slider images will be arrange according @input: slideOrderType
    },
  ];

  constructor() {}

  ngOnInit(): void {}

  onImageChange(event: string): void {}
}
