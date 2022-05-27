import { IFishingCourse } from 'src/app/model/fishingCourse';
import { Component, Input, OnInit } from '@angular/core';
import { ToastrService } from 'ngx-toastr';
import { FishingQuickReservationService } from 'src/app/service/fishingQuickReservation.service';
import { FishingCourseService } from 'src/app/service/fishingCourse.service';

@Component({
  selector: 'app-fishing-quick-reservations',
  templateUrl: './fishing-quick-reservations.component.html',
  styleUrls: ['./fishing-quick-reservations.component.css']
})
export class FishingQuickReservationsComponent implements OnInit {
  @Input() fishingCourse!: IFishingCourse;
  @Input() imageObject!: Array<object>;

  constructor(
    private fishingCourseQuickReservationService: FishingQuickReservationService,
    private toastr: ToastrService,
    private fishingCourseService: FishingCourseService,
  ) {}

  ngOnInit(): void {
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
