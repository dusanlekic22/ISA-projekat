import { IFishingCourse } from 'src/app/model/fishingCourse';
import { Component, OnInit } from '@angular/core';
import { emptyCustomer, ICustomer } from 'src/app/model/customer';
import { CustomerService } from '../../customer.service';
import { FishingCourseService } from 'src/app/service/fishingCourse.service';
import { UserService } from 'src/app/service/user.service';
import { AuthenticationService } from 'src/app/service/authentication.service';
import { ActivatedRoute } from '@angular/router';

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

  constructor(
    private customerService: CustomerService,
    private fishingCourseService: FishingCourseService,
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
          if (this.authenticationService.isLoggedIn()) {
            this.userService.currentUser.subscribe((user) => {
              this.customerService
                .getCustomerById(user.id)
                .subscribe((customer) => {
                  this.customer = customer;
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
}
