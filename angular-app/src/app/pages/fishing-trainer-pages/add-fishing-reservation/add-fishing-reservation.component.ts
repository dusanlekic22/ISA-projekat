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
import { MatChip } from '@angular/material/chips';
import { IAdditionalService } from 'src/app/model/additionalService';
import { AdditionalServiceService } from '../../cottage-owner/services/additional-service.service';

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
  chips!: MatChip;
  fishingChips: string[] = [];
  fishingServices: IAdditionalService[] = [];
  reservationServices: IAdditionalService[] = [];

  fishingCourseReservation: IFishingReservation = emptyFishingReservation;

  constructor(
    private fishingCourseReservationService: FishingReservationService,
    private toastr: ToastrService,
    private userService: UserService,
    private fishingCourseService: FishingCourseService,
    private route: ActivatedRoute,
    private additionalServiceService: AdditionalServiceService
  ) {}

  ngOnInit(): void {
    let fishingCourseId = +this.route.snapshot.paramMap.get('id')!;
    this.fishingCourseReservationService
      .getCustomerHasReservationNow(fishingCourseId)
      .subscribe((customers) => {
        this.eligibleCustomers = customers;
      });
    this.userService.currentUser.subscribe((user) => {
      if (user.id != undefined) {
        this.fishingCourseService
          .getFishingTrainerCourses(user.id)
          .subscribe((fishingCourses) => {
            this.fishingCourses = fishingCourses;
            this.fishingCourse = this.fishingCourses.filter(
              (c) => c.id == fishingCourseId
            )[0];
            this.fishingCourseReservation.fishingCourse = this.fishingCourse;
            this.fishingCourseReservation.location = this.fishingCourse.address;
          });
      }
    });
    if(fishingCourseId!=undefined){
      this.getChips(fishingCourseId);
    }
  }

  getChips(id:number) {
    this.additionalServiceService
      .getAdditionalServicesByFishingCourseId(id)
      .subscribe((tags) => {
        tags.forEach((t) => {
          if (this.fishingServices.length < 1) {
            this.fishingServices.push(t);
          } else if (this.fishingServices.some((e) => e.name !== t.name)) {
            this.fishingServices.push(t);
          }
        });
      });
  }

  toggleSelectionFishing(chip: MatChip, option: IAdditionalService) {
    if (chip.toggleSelected()) {
      this.reservationServices.push({id:0,name:option.name,price:option.price});
    } else {
      this.reservationServices = this.reservationServices.filter(
        (e) => e !== option
      );
    }
  }

  setCustomer(id: number) {
    this.customer = this.eligibleCustomers.filter((c) => c.id == id)[0];
    this.fishingCourseReservationService
    .getCustomerHasReservationNow(id)
    .subscribe((customers) => {
      this.eligibleCustomers = customers;
    });
  }

  addReservation(): void {
    this.fishingCourseReservation.customer = this.customer;
    this.fishingCourseReservationService
      .addFishingReservation(this.fishingCourseReservation)
      .subscribe(
        (reservation) => {
          this.toastr.success('Reservation was successfully added.');
          this.reservationServices.forEach((tag) => {
            this.additionalServiceService
              .addAdditionalServiceForFishingReservation(
                tag,
                reservation
              )
              .subscribe((service) => {});
          });
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
}
