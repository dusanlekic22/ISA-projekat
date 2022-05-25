import { IFishingTrainer } from './../../../model/fishingTrainer';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { FishingCourseService } from './../../../service/fishingCourse.service';
import { AdditionalServiceService } from './../../cottage-owner/services/additional-service.service';
import { IAdditionalService } from './../../cottage-owner/cottage-profile/additionalService';
import { IDateSpan } from './../../cottage-owner/cottage-profile/dateSpan';
import { Component, OnInit, Output, EventEmitter } from '@angular/core';
import { IFishingCourse } from 'src/app/model/fishingCourse';
import { FishingTrainerService } from 'src/app/service/fishingTrainer.service';

@Component({
  selector: 'app-add-fishing-course',
  templateUrl: './add-fishing-course.component.html',
  styleUrls: ['./add-fishing-course.component.css'],
})
export class AddFishingCourseComponent implements OnInit {
  fishingCourse: IFishingCourse = {
    id: 0,
    name: '',
    address: {
      city: '',
      country: '',
      latitude: '0',
      longitude: '0',
      street: '',
    },
    promoDescription: '',
    fishingImage: [],
    capacity: 0,
    fishingReservation: [],
    fishingQuickReservation: [],
    fishingRules: '',
    fishingEquipment: '',
    cancellationPercentageKeep: 0,
    additionalService: [],
    price: 0,
    fishingTrainer: {
      id: 0,
      username: '',
      password: '',
      firstName: '',
      lastName: '',
      email: '',
      phoneNumber: '',
      address: {
        street: '',
        city: '',
        country: '',
        latitude: '',
        longitude: '',
      },
      roles: [],
      biography: '',
      fishingCourse: [],
    },
  };

  @Output() submitted = new EventEmitter<boolean>();
  additionalServiceTags: IAdditionalService[] = [];

  ngOnInit(): void {
    this.fishingTrainerService.getFishingTrainer().subscribe((user: IFishingTrainer) => {
      if (user.id != undefined) {
        this.fishingCourse.fishingTrainer = user;
      }
    });
    this.additionalServiceService
      .getFreeAdditionalServices()
      .subscribe((additionalService: IAdditionalService[]) => {
        this.additionalServiceTags = additionalService;
      });
  }

  constructor(
    private fishingCourseService: FishingCourseService,
    private additionalServiceService: AdditionalServiceService,
    private fishingTrainerService: FishingTrainerService
  ) {}

  addFishingCourse() {
    this.fishingCourseService
      .saveFishingCourse(this.fishingCourse)
      .subscribe((fishingCourse) => {
        this.additionalServiceTags.forEach((element) => {
          this.additionalServiceService
            .addAdditionalServiceForFishingCourse(element, fishingCourse)
            .subscribe((additionalService) => {});
        });

        this.submitted.emit(true);
      });
  }

  onItemAdded(input: any): void {
    let text = input.display.split(' ');
    this.additionalServiceTags.pop();
    this.additionalServiceTags.push({ id: 0, name: text[0], price: text[1] });
  }
}
