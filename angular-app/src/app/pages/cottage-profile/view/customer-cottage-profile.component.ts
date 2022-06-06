import { ICottageQuickReservation } from 'src/app/model/cottageQuickReservation';
import { ICottage } from './../../../model/cottage';
import { AuthenticationService } from './../../../service/authentication.service';
import { CustomerService } from './../../customer.service';
import { ICustomer, emptyCustomer } from './../../../model/customer';
import { Component, OnInit } from '@angular/core';
import '../../../../assets/jquery/jquery.min';
import '../../../../assets/owlcarousel/owl.carousel.js';
import { UserService } from 'src/app/service/user.service';
import { CottageService } from '../../cottage-owner/services/cottage.service';
import { ActivatedRoute } from '@angular/router';
import { MatPaginator, PageEvent } from '@angular/material/paginator';
import { CottageQuickReservationService } from '../../cottage-owner/services/cottage-quick-reservation.service';

@Component({
  selector: 'app-customer-cottage-profile',
  templateUrl: './customer-cottage-profile.component.html',
  styleUrls: ['./customer-cottage-profile.component.css'],
})
export class CustomerCottageProfileComponent implements OnInit {
  customer: ICustomer = emptyCustomer;
  cottage!: ICottage;
  cottageId!: number;
  isSubscribed: boolean = false;

  // cottage quick reservation data
  cottageQuickPage: number = 0;
  cottageQuickTotalElements: number = 30;
  paginatorCottageQuick!: MatPaginator;
  cottageQuickReservations!: ICottageQuickReservation[];
  openCottages: string = 'yes';

  constructor(
    private customerService: CustomerService,
    private cottageService: CottageService,
    private cottageQuickReservationService: CottageQuickReservationService,
    private userService: UserService,
    private authenticationService: AuthenticationService,
    private route: ActivatedRoute
  ) {}

  ngOnInit(): void {
    window.scrollTo(0, 0);
    this.loadCottage();
  }

  loadCottage() {
    this.route.params.subscribe((data) => {
      this.cottageId = data.cottageId;
      this.cottageService
        .getCottageById(this.cottageId)
        .subscribe((cottage) => {
          this.cottage = cottage;
          this.getQuickCottagesReservations();
          if (this.authenticationService.isLoggedIn()) {
            this.userService.currentUser.subscribe((user) => {
              this.customerService
                .getCustomerById(user.id)
                .subscribe((customer) => {
                  this.openCottages = 'no';
                  this.customer = customer;
                  this.checkSubscribe();
                });
            });
          }
        });
    });
  }

  checkSubscribe() {
    if (this.cottage !== undefined && this.customer !== undefined) {
      this.cottage.subscribers.forEach((v) => {
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
    this.cottageService
      .subscription(this.customer.id, this.cottageId)
      .subscribe(() => {
        this.loadCottage();
      });
  }
  unsubscribe() {
    this.cottageService
      .unsubscription(this.customer.id, this.cottageId)
      .subscribe(() => {
        this.isSubscribed = false;
        this.loadCottage();
      });
  }
  // cottage quick reservation methods

  getQuickCottagesReservations() {
    this.cottageQuickReservationService
      .getCottagesQuickReservationPagination(
        this.cottageQuickPage,
        this.cottage.cottageOwner.id
      )
      .subscribe((data) => {
        this.cottageQuickReservations = data.content;
        this.cottageQuickTotalElements = data.totalElements;
      });
  }

  onChangeCottageQuickPage(pe: PageEvent) {
    this.cottageQuickPage = pe.pageIndex;
    this.getQuickCottagesReservations();
  }
}
