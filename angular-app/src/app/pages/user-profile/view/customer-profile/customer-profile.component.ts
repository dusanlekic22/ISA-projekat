import { IFishingCourse } from 'src/app/model/fishingCourse';
import { FishingCourseService } from './../../../../service/fishingCourse.service';
import { BoatService } from 'src/app/pages/boat-owner/services/boat.service';
import { CottageService } from 'src/app/pages/cottage-owner/services/cottage.service';
import { ICottage } from 'src/app/model/cottage';
import { UserService } from './../../../../service/user.service';
import { IUser } from 'src/app/pages/registration/registration/user';
import { ICustomer } from 'src/app/model/customer';
import { CustomerService } from './../../../customer.service';
import { Component, Input, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { DeleteDialogComponent } from 'src/app/components/delete-dialog/delete-dialog.component';
import {
  ModalDismissReasons,
  NgbModal,
  NgbModalRef,
} from '@ng-bootstrap/ng-bootstrap';
import { MatPaginator, PageEvent } from '@angular/material/paginator';
import { IBoat } from 'src/app/model/boat/boat';

@Component({
  selector: 'app-customer-profile',
  templateUrl: './customer-profile.component.html',
  styleUrls: ['../user-profile.component.css'],
})
export class CustomerProfileComponent implements OnInit {
  @Input() userId!: number;
  @Input() user!: IUser;
  customer!: ICustomer;
  modalReference!: NgbModalRef;
  modalReferenceComplaint!: NgbModalRef;

  subscriptionsCottage!: ICottage[];
  subscriptionsCottagePage: number = 0;
  subscriptionsCottageTotalElements: number = 30;
  paginatorSubscriptionsCottage!: MatPaginator;

  subscriptionsBoat!: IBoat[];
  subscriptionsBoatPage: number = 0;
  subscriptionsBoatTotalElements: number = 30;
  paginatorSubscriptionsBoat!: MatPaginator;

  subscriptionsFishing!: IFishingCourse[];
  subscriptionsFishingPage: number = 0;
  subscriptionsFishingTotalElements: number = 30;
  paginatorSubscriptionsFishing!: MatPaginator;

  constructor(
    private _customerService: CustomerService,
    public dialog: MatDialog,
    private modalService: NgbModal,
    private cottageService: CottageService,
    private boatService: BoatService,
    private fishingService: FishingCourseService
  ) {}

  ngOnInit(): void {
    this._customerService.getCustomerById(this.userId).subscribe((data) => {
      this.customer = data;
      this.availableSubscriptionCottage(0);
      this.availableSubscriptionBoat(0);
      this.availableSubscriptionFishing(0);
    });
  }
  scroll(el: HTMLElement) {
    el.scrollIntoView();
  }
  openDialog(): void {
    const dialogRef = this.dialog.open(DeleteDialogComponent, {
      width: '250px',
      data: { email: this.user.email },
    });

    dialogRef.afterClosed().subscribe((result) => {
      console.log('The dialog was closed');
    });
  }

  closeResult = '';

  open(content: any) {
    this.modalReference = this.modalService.open(content, {
      ariaLabelledBy: 'modal-basic-title review',
    });
    this.modalReference.result.then(
      (result) => {
        this.closeResult = `Closed with: ${result}`;
      },
      (reason) => {
        this.closeResult = `Dismissed ${this.getDismissReason(reason)}`;
      }
    );
  }

  sendComplaint() {
    this.modalReferenceComplaint.close();
  }

  private getDismissReason(reason: any): string {
    if (reason === ModalDismissReasons.ESC) {
      return 'by pressing ESC';
    } else if (reason === ModalDismissReasons.BACKDROP_CLICK) {
      return 'by clicking on a backdrop';
    } else {
      return `with: ${reason}`;
    }
  }

  availableSubscriptionCottage(page: number) {
    this.cottageService
      .getAvailableCottagesSubscription(this.customer.id, page)
      .subscribe((data: any) => {
        this.subscriptionsCottage = data.content;
        this.subscriptionsCottagePage = page;
        this.subscriptionsCottageTotalElements = data.totalElements;
      });
  }

  onChangeSubscriptionsCottagePage(pe: PageEvent) {
    this.subscriptionsCottagePage = pe.pageIndex;
    this.availableSubscriptionCottage(this.subscriptionsCottagePage);
  }
  // cottage end
  availableSubscriptionBoat(page: number) {
    this.boatService
      .getAvailableBoatsSubscription(this.customer.id, page)
      .subscribe((data: any) => {
        this.subscriptionsBoat = data.content;
        this.subscriptionsBoatPage = page;
        this.subscriptionsBoatTotalElements = data.totalElements;
      });
  }

  onChangeSubscriptionsBoatPage(pe: PageEvent) {
    this.subscriptionsBoatPage = pe.pageIndex;
    this.availableSubscriptionBoat(this.subscriptionsBoatPage);
  }
  //boat end

  availableSubscriptionFishing(page: number) {
    this.boatService
      .getAvailableBoatsSubscription(this.customer.id, page)
      .subscribe((data: any) => {
        this.subscriptionsBoat = data.content;
        this.subscriptionsBoatPage = page;
        this.subscriptionsBoatTotalElements = data.totalElements;
      });
  }

  onChangeSubscriptionsFishingPage(pe: PageEvent) {
    this.subscriptionsFishingPage = pe.pageIndex;
    this.availableSubscriptionFishing(this.subscriptionsFishingPage);
  }
  //fishing end

  createDeleteUserRequest() {}
}
