import { IFishingCourse } from 'src/app/model/fishingCourse';
import { Component, Input, OnInit } from '@angular/core';
import { ToastrService } from 'ngx-toastr';
import { FishingQuickReservationService } from 'src/app/service/fishingQuickReservation.service';
import { FishingCourseService } from 'src/app/service/fishingCourse.service';
import { ActivatedRoute } from '@angular/router';
import { IAdditionalService } from 'src/app/model/additionalService';
import { AdditionalServiceService } from '../../cottage-owner/services/additional-service.service';

@Component({
  selector: 'app-fishing-quick-reservations',
  templateUrl: './fishing-quick-reservations.component.html',
  styleUrls: ['./fishing-quick-reservations.component.css']
})
export class FishingQuickReservationsComponent implements OnInit {
  @Input() fishingCourse!: IFishingCourse;
  @Input() imageObject!: Array<object>;
  services!: IAdditionalService[];

  constructor(
    private fishingCourseQuickReservationService: FishingQuickReservationService,
    private toastr: ToastrService,
    private fishingCourseService: FishingCourseService,
    private additionalService: AdditionalServiceService,
    private route: ActivatedRoute,
  ) {}

  ngOnInit(): void {
    let fishingId = this.route.snapshot.paramMap.get('id')!;
    this.additionalService
      .getAdditionalServicesByFishingQuickReservationId(parseInt(fishingId))
      .subscribe((services) => {
        this.services = services;
      });
  }

  deleteQuickReservation(id: number): void {
    this.fishingCourseQuickReservationService
      .deleteFishingQuickReservations(id)
      .subscribe(
        (quickReservation) => {
          this.toastr.success('Reservation was successfully removed.');
          this.getFishingCourse();
          this.fishingCourseQuickReservationService
            .getFishingQuickReservations()
            .subscribe((fishingCourseQuickReservation) => {
              this.fishingCourse.fishingQuickReservation = fishingCourseQuickReservation;
            });
        },
        (err) => {
          this.toastr.error('Reservation removal failed');
        }
      );
    this.fishingCourseService.getFishingTrainerCourses;
  }

  private getFishingCourse() {
    this.fishingCourseService
      .getFishingCourseById(this.fishingCourse.id)
      .subscribe((fishingCourse) => {
        this.fishingCourse = fishingCourse;
        this.fishingCourse.fishingImage.forEach((element) => {
          this.imageObject.push({
            image: element.image,
            thumbImage: element.image,
            alt: 'alt of image',
          });
        });
      });
  }

  openQuickReservationForm() {
    // this.addReservationFormOpened = true;
    // this.quickReservationFormElement.nativeElement.scrollIntoView();
  }
}
