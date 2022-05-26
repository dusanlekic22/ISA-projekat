import { ModalDirective } from 'angular-bootstrap-md';
import { emptyFishingCourse } from './../../../model/fishingCourse';
import { IAdditionalService } from './../../../model/additionalService';
import { IFishingTrainer } from './../../../model/fishingTrainer';
import { FishingCourseService } from './../../../service/fishingCourse.service';
import { AdditionalServiceService } from './../../cottage-owner/services/additional-service.service';
import { Component, OnInit, Output, EventEmitter, ViewChild } from '@angular/core';
import { IFishingCourse } from 'src/app/model/fishingCourse';
import { FishingTrainerService } from 'src/app/service/fishingTrainer.service';

@Component({
  selector: 'app-add-fishing-course',
  templateUrl: './add-fishing-course.component.html',
  styleUrls: ['./add-fishing-course.component.css'],
})
export class AddFishingCourseComponent implements OnInit {
  fishingCourse: IFishingCourse = emptyFishingCourse;
  @ViewChild('frame') addModal!: ModalDirective;
  @Output() submitted = new EventEmitter<boolean>();
  additionalServiceTags: IAdditionalService[] = [];

  ngOnInit(): void {
    this.fishingTrainerService
      .getFishingTrainer()
      .subscribe((user: IFishingTrainer) => {
        if (user.id != undefined) {
          this.fishingCourse.fishingTrainer = user;
        }
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
        this.addModal.hide();
      });
  }

  onItemAdded(input: any): void {
    let text = input.display.split(' ');
    this.additionalServiceTags.pop();
    this.additionalServiceTags.push({ id: 0, name: text[0], price: text[1] });
  }
}
