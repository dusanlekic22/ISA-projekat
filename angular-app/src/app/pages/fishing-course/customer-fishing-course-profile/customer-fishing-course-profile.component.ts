import { IFishingCourse } from 'src/app/model/fishingCourse';
import { Component, OnInit } from '@angular/core';
import { emptyCustomer, ICustomer } from 'src/app/model/customer';
import { CustomerService } from '../../customer.service';
import { FishingCourseService } from 'src/app/service/fishingCourse.service';
import { UserService } from 'src/app/service/user.service';
import { AuthenticationService } from 'src/app/service/authentication.service';
import { ActivatedRoute } from '@angular/router';
import { MatPaginator, PageEvent } from '@angular/material/paginator';
import { IFishingQuickReservation } from 'src/app/model/fishingQuickReservation';
import { FishingQuickReservationService } from 'src/app/service/fishingQuickReservation.service';

@Component({
  selector: 'app-customer-fishing-course-profile',
  templateUrl: './customer-fishing-course-profile.component.html',
  styleUrls: ['./customer-fishing-course-profile.component.css'],
})
export class CustomerFishingCourseProfileComponent implements OnInit {
  customer: ICustomer = emptyCustomer;
  fishingCourse!: IFishingCourse;
  fishingCourseId!: number;
  isSubscribed: boolean = false;

  // fishing quick reservation data
  fishingQuickPage: number = 0;
  fishingQuickTotalElements: number = 30;
  paginatorFishingQuick!: MatPaginator;
  fishingQuickReservations!: IFishingQuickReservation[];
  openFishings: string = 'yes';

  constructor(
    private customerService: CustomerService,
    private fishingCourseService: FishingCourseService,
    private fishingQuickReservationService: FishingQuickReservationService,
    private userService: UserService,
    private authenticationService: AuthenticationService,
    private route: ActivatedRoute
  ) {}

  ngOnInit(): void {
    window.scrollTo(0, 0);
    this.loadFishingCourse();
  }

  loadFishingCourse() {
    this.route.params.subscribe((data) => {
      this.fishingCourseId = data.fishingCourseId;
      this.fishingCourseService
        .getFishingCourseById(this.fishingCourseId)
        .subscribe((fishingCourse) => {
          this.fishingCourse = fishingCourse;
          this.getQuickFishingsReservations();
          if (this.authenticationService.isLoggedIn()) {
            this.userService.currentUser.subscribe((user) => {
              this.customerService
                .getCustomerById(user.id)
                .subscribe((customer) => {
                  this.openFishings = 'no';
                  this.customer = customer;
                  this.getQuickFishingsReservations();
                  this.checkSubscribe();
                });
            });
          }
        });
    });
  }

  checkSubscribe() {
    if (this.fishingCourseId !== undefined && this.customer !== undefined) {
      this.fishingCourse.subscribers.forEach((v) => {
        if (v.id === this.customer.id) {
          this.isSubscribed = true;
          return false;
        }
        return;
      });
    }
    return true;
  }

  subscribe() {
    this.fishingCourseService
      .subscription(this.customer.id, this.fishingCourseId)
      .subscribe(() => {
        this.loadFishingCourse();
      });
  }
  unsubscribe() {
    this.fishingCourseService
      .unsubscription(this.customer.id, this.fishingCourseId)
      .subscribe(() => {
        this.isSubscribed = false;
        this.loadFishingCourse();
      });
  }

  // cottage quick reservation methods

  getQuickFishingsReservations() {
    this.fishingQuickReservationService
      .getFishingsQuickReservationPagination(
        this.fishingQuickPage,
        this.fishingCourse.fishingTrainer.id
      )
      .subscribe((data) => {
        this.fishingQuickReservations = data.content;
        this.fishingQuickTotalElements = data.totalElements;
      });
  }

  onChangeFishingQuickPage(pe: PageEvent) {
    this.fishingQuickPage = pe.pageIndex;
    this.getQuickFishingsReservations();
  }
}
