import { emptyFishingReservation } from './../../../model/fishingReservation';
import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { ICustomer } from 'src/app/model/customer';
import { IFishingCourse } from 'src/app/model/fishingCourse';
import { IFishingReservation } from 'src/app/model/fishingReservation';
import { FishingCourseService } from 'src/app/service/fishingCourse.service';
import { UserService } from 'src/app/service/user.service';
import { FishingReservationService } from 'src/app/service/fishingReservation.service';

@Component({
  selector: 'app-add-fishing-reservation',
  templateUrl: './add-fishing-reservation.component.html',
  styleUrls: ['./add-fishing-reservation.component.css'],
})
export class AddFishingReservationComponent implements OnInit {
  @Input() minDate!: string;
  @Output() submitted = new EventEmitter<boolean>();
  fishingCourse!: IFishingCourse;
  eligibleCustomers!: ICustomer[];
  @Input() customer!: ICustomer;
  fishingCourses!: IFishingCourse[];

  fishingCourseReservation: IFishingReservation = emptyFishingReservation;

  constructor(
    private _fishingCourseReservationService: FishingReservationService,
    private _toastr: ToastrService,
    private _userService: UserService,
    private _fishingCourseService: FishingCourseService,
    private _route: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this._fishingCourseReservationService
      .getCustomerHasReservationNow()
      .subscribe((customers) => {
        this.eligibleCustomers = customers;
      });
    let fishingCourseId = this._route.snapshot.paramMap.get('fishingCourseId');
    this._userService.currentUser.subscribe((user) => {
      this._fishingCourseService
        .getFishingTrainerCourses(user.id)
        .subscribe((fishingCourses) => {
          this.fishingCourses = fishingCourses;
          this.fishingCourse = this.fishingCourses.filter(
            (c) => c.id == parseInt(fishingCourseId!)
          )[0];
          this.fishingCourseReservation.fishingCourse = this.fishingCourse;
        });
    });
  }

  setCustomer(id: number) {
    this.customer = this.eligibleCustomers.filter((c) => c.id == id)[0];
  }

  addReservation(): void {
    this.fishingCourseReservation.customer = this.customer;
    this._fishingCourseReservationService
      .addFishingReservation(this.fishingCourseReservation)
      .subscribe(
        (reservation) => {
          this._toastr.success('Reservation was successfully added.');

          this.submitted.emit();
        },
        (err) => {
          this._toastr.error(
            'Reservation term overlaps with another.',
            'Try a different date!'
          );
        }
      );
  }
}
