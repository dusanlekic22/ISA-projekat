import { IFishingTrainer } from './../../../model/fishingTrainer';
import { Component, OnInit } from '@angular/core';
import { emptyCustomer, ICustomer } from 'src/app/model/customer';
import { CustomerService } from '../../customer.service';
import { FishingCourseService } from 'src/app/service/fishingCourse.service';
import { UserService } from 'src/app/service/user.service';
import { AuthenticationService } from 'src/app/service/authentication.service';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-customer-fishing-trainer-profile',
  templateUrl: './customer-fishing-trainer-profile.component.html',
  styleUrls: ['./customer-fishing-trainer-profile.component.css'],
})
export class CustomerFishingTrainerProfileComponent implements OnInit {
  customer: ICustomer = emptyCustomer;
  fishingTrainer!: IFishingTrainer;
  fishingTrainerId!: number;
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
    this.loadCottage();
  }

  loadCottage() {
    //   this.route.params.subscribe((data) => {
    //     this.cottageId = data.fishingTrainerId;
    //     this.cottageService
    //       .getCottageById(this.cottageId)
    //       .subscribe((cottage) => {
    //         this.cottage = cottage;
    //         if (this.authenticationService.isLoggedIn()) {
    //           this.userService.currentUser.subscribe((user) => {
    //             this.customerService
    //               .getCustomerById(user.id)
    //               .subscribe((customer) => {
    //                 this.customer = customer;
    //                 this.checkSubscribe();
    //               });
    //           });
    //         }
    //       });
    //   });
  }

  // checkSubscribe() {
  //   if (this.cottage !== undefined && this.customer !== undefined) {
  //     this.cottage.subscribers.forEach((v) => {
  //       if (v.id === this.customer.id) {
  //         this.isSubscribed = true;
  //         return false;
  //       }
  //       return;
  //     });
  //   }
  //   return true;
  // }

  // subscribe() {
  //   this.cottageService
  //     .subscription(this.customer.id, this.cottageId)
  //     .subscribe(() => {
  //       this.loadCottage();
  //     });
  // }
  // unsubscribe() {
  //   this.cottageService
  //     .unsubscription(this.customer.id, this.cottageId)
  //     .subscribe(() => {
  //       this.isSubscribed = false;
  //       this.loadCottage();
  //     });
  // }
}
