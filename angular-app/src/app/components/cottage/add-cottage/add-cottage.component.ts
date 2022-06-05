import { Router } from '@angular/router';
import { initCottage } from './../../../model/cottage';
import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { IAddress } from 'src/app/model/address';
import { ICottage } from 'src/app/model/cottage';
import { IDateSpan } from 'src/app/model/dateSpan';
import { CottageAdditionalServicesService } from 'src/app/pages/cottage-owner/services/cottage-additional-services.service';
import { UserService } from 'src/app/service/user.service';
import { IAdditionalService } from '../../../model/additionalService';
import { AdditionalServiceService } from '../../../pages/cottage-owner/services/additional-service.service';
import { CottageService } from '../../../pages/cottage-owner/services/cottage.service';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-add-cottage',
  templateUrl: './add-cottage.component.html',
  styleUrls: ['./add-cottage.component.css'],
})
export class AddCottageComponent implements OnInit {
  cottage: ICottage = initCottage;

  startDate!: Date;
  endDate!: Date;
  avaliableDateSpans: IDateSpan[] = [];
  additionalServiceTags: IAdditionalService[] = [];
  minDate!: Date;

  ngOnInit(): void {
    this._userService.currentUser.subscribe((user) => {
      this.cottage.cottageOwner = user;
    });
    this._additionalServiceService
      .getFreeAdditionalServices()
      .subscribe((additionalService) => {
        this.additionalServiceTags = additionalService;
      });
    this.minDate = new Date(Date.now());
  }

  validatingForm: FormGroup;

  constructor(
    private _cottageService: CottageService,
    private _additionalServiceService: AdditionalServiceService,
    private _cottageAdditionalService: CottageAdditionalServicesService,
    private _userService: UserService,
    private _toastr: ToastrService,
    private _router: Router
  ) {
    this.validatingForm = new FormGroup({
      loginFormModalEmail: new FormControl('', Validators.email),
      loginFormModalPassword: new FormControl('', Validators.required),
    });
  }

  addDateSpan() {
    this.cottage.availableReservationDateSpan.push({
      startDate: this.startDate,
      endDate: this.endDate,
    });
  }

  addCottage() {
    this._cottageService.saveCottage(this.cottage).subscribe(
      (data) => {
        this._toastr.success('Cottage was successfully added.');
        this.additionalServiceTags.forEach((element) => {
          this._cottageAdditionalService
            .addAdditionalServiceForCottage(element, data)
            .subscribe((additionalService) => {});
        });
        this._router.navigate(['/cottageOwnerHome'])
      },
      (err) => {
        console.log(err);
        this._toastr.error(
          "Couldn't add the cottage!"
        );
      }
    );
  }

  setAddress(address: IAddress) {
    this.cottage.address = address;
  }

  removeTerm(term: IDateSpan) {
    this.cottage.availableReservationDateSpan =
      this.cottage.availableReservationDateSpan.filter((term) => term != term);
  }

  get loginFormModalEmail() {
    return this.validatingForm.get('loginFormModalEmail');
  }

  get loginFormModalPassword() {
    return this.validatingForm.get('loginFormModalPassword');
  }

  onItemAdded(input: any): void {
    let text = input.display.split(' ');
    this.additionalServiceTags.pop();
    this.additionalServiceTags.push({ id: 0, name: text[0], price: text[1] });
  }
}
