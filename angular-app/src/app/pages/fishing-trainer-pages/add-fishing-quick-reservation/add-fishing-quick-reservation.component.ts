import { emptyFishingQuickReservation } from './../../../model/fishingQuickReservation';
import { IFishingCourse } from 'src/app/model/fishingCourse';
import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { IFishingQuickReservation } from 'src/app/model/fishingQuickReservation';
import { FishingQuickReservationService } from 'src/app/service/fishingQuickReservation.service';
import { UserService } from 'src/app/service/user.service';
import { FishingCourseService } from 'src/app/service/fishingCourse.service';

@Component({
  selector: 'app-add-fishing-quick-reservation',
  templateUrl: './add-fishing-quick-reservation.component.html',
  styleUrls: ['./add-fishing-quick-reservation.component.css'],
})
export class AddFishingQuickReservationComponent implements OnInit {
  fishingCourse!: IFishingCourse;
  @Input() fishingQuickReservation: IFishingQuickReservation =
    emptyFishingQuickReservation;
  minDate!: string;
  @Output() submitted = new EventEmitter<boolean>();
  fishingCourses!: IFishingCourse[];

  constructor(
    private fishingQuickReservationService: FishingQuickReservationService,
    private toastr: ToastrService,
    private fishingService: FishingCourseService,
    private userService: UserService,
    private route: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.minDate = this.date();
    let fishingCourseId = this.route.snapshot.paramMap.get('id');
    this.userService.currentUser.subscribe((user) => {
      if (user.id != undefined) {
        this.fishingService
          .getFishingTrainerCourses(user.id)
          .subscribe((fishingCourses) => {
            this.fishingCourses = fishingCourses;
            this.fishingCourse = this.fishingCourses.filter(
              (c) => c.id === parseInt(fishingCourseId!)
            )[0];
            this.fishingQuickReservation.fishingCourse = this.fishingCourse;
            this.fishingQuickReservation.location = this.fishingCourse.address;
          });
      }
    });
  }

  addQuickReservation(): void {
    this.fishingQuickReservationService
      .addFishingQuickReservation(this.fishingQuickReservation)
      .subscribe(
        (quickReservation) => {
          //this.addReservationFormOpened = false;
          this.toastr.success('Reservation was successfully added.');
          this.submitted.emit();
        },
        (err) => {
          this.toastr.error(
            'Reservation term overlaps with another.',
            'Try a different date!'
          );
        }
      );
  }

  date() {
    let min = new Date();
    let month = '';
    let day = '';
    if (min.getMonth() < 10) {
      month = '0' + (min.getMonth() + 1).toString();
    } else {
      month = (min.getMonth() + 1).toString();
    }
    if (min.getDate() < 10) {
      day = '0' + min.getDate().toString();
    } else {
      day = min.getDate().toString();
    }
    let x = min.getFullYear().toString() + '-' + month + '-' + day + 'T00:00';
    console.log(x);
    return x;
  }
}
