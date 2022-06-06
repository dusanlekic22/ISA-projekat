import { Component, OnInit } from '@angular/core';
import { MatPaginator, PageEvent } from '@angular/material/paginator';
import { ActivatedRoute } from '@angular/router';
import { IBoat } from 'src/app/model/boat/boat';
import { IBoatQuickReservation } from 'src/app/model/boat/boatQuickReservation';
import { emptyCustomer, ICustomer } from 'src/app/model/customer';
import { AuthenticationService } from 'src/app/service/authentication.service';
import { UserService } from 'src/app/service/user.service';
import '../../../../assets/jquery/jquery.min';
import '../../../../assets/owlcarousel/owl.carousel.js';
import { BoatQuickReservationService } from '../../boat-owner/services/boat-quick-reservation.service';
import { BoatService } from '../../boat-owner/services/boat.service';
import { CustomerService } from '../../customer.service';

@Component({
  selector: 'app-customer-boat-profile',
  templateUrl: './customer-boat-profile.component.html',
  styleUrls: ['./customer-boat-profile.component.css'],
})
export class CustomerBoatProfileComponent implements OnInit {
  customer: ICustomer = emptyCustomer;
  boat!: IBoat;
  boatId!: number;
  isSubscribed: boolean = false;

  // boat quick reservation data
  boatQuickPage: number = 0;
  boatQuickTotalElements: number = 30;
  paginatorBoatQuick!: MatPaginator;
  boatQuickReservations!: IBoatQuickReservation[];
  openBoats: string = 'yes';

  constructor(
    private customerService: CustomerService,
    private boatService: BoatService,
    private boatQuickReservationService: BoatQuickReservationService,
    private userService: UserService,
    private authenticationService: AuthenticationService,
    private route: ActivatedRoute
  ) {}
  ngOnInit(): void {
    window.scrollTo(0, 0);
    this.loadBoat();
  }

  loadBoat() {
    this.route.params.subscribe((data) => {
      this.boatId = data.boatId;
      this.boatService.getBoatById(this.boatId).subscribe((boat) => {
        this.boat = boat;
        this.getQuickBoatsReservations();
        if (this.authenticationService.isLoggedIn()) {
          this.userService.currentUser.subscribe((user) => {
            this.customerService
              .getCustomerById(user.id)
              .subscribe((customer) => {
                this.openBoats = 'no';
                this.customer = customer;
                this.checkSubscribe();
              });
          });
        }
      });
    });
  }

  checkSubscribe() {
    if (this.boat !== undefined && this.customer !== undefined) {
      this.boat.subscribers.forEach((v) => {
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
    this.boatService
      .subscription(this.customer.id, this.boatId)
      .subscribe(() => {
        this.loadBoat();
      });
  }
  unsubscribe() {
    this.boatService
      .unsubscription(this.customer.id, this.boatId)
      .subscribe(() => {
        this.isSubscribed = false;
        this.loadBoat();
      });
  }

  // boat quick reservation methods
  getQuickBoatsReservations() {
    this.boatQuickReservationService
      .getBoatsQuickReservationPagination(
        this.boatQuickPage,
        this.boat.boatOwner.id
      )
      .subscribe((data) => {
        this.boatQuickReservations = data.content;
        this.boatQuickTotalElements = data.totalElements;
      });
  }

  onChangeBoatsQuickPage(pe: PageEvent) {
    this.boatQuickPage = pe.pageIndex;
    this.getQuickBoatsReservations();
  }
}
