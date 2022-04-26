import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { MatDatepickerInputEvent } from '@angular/material/datepicker';
import { tap } from 'rxjs';
import { IAdditionalService } from '../cottage-profile/additionalService';
import { ICottage } from '../cottage-profile/cottage';
import { IDateSpan } from '../cottage-profile/dateSpan';
import { CottageService } from '../cottage.service';

@Component({
  selector: 'app-add-cottage',
  templateUrl: './add-cottage.component.html',
  styleUrls: ['./add-cottage.component.css'],
})
export class AddCottageComponent implements OnInit {
  cottage: ICottage = {
    id: 0,
    name: '',
    address: {
      id: 0,
      city: 'Kraljevo',
      country: 'Srbija',
      latitude: '73',
      longitude: '89',
      street: 'Zmajevacka',
    },
    promoDescription: '',
    bedCount: 0,
    roomCount: 0,
    cottageRules: '',
    cottageImage: [],
    cottageReservation: [],
    cottageQuickReservation: [],
    availableReservationDateSpan: [],
  };

  @Output() submitted = new EventEmitter<boolean>();
  startDate!: Date;
  endDate!: Date;
  avaliableDateSpans: IDateSpan[] = [];
  additionalServiceTags: IAdditionalService[] = [];
  minDate!: Date;

  ngOnInit(): void {
    this._cottageService
      .getFreeAdditionalServices()
      .subscribe((additionalService) => {
        this.additionalServiceTags = additionalService;
      });
    this.minDate = new Date(Date.now());
  }

  validatingForm: FormGroup;

  constructor(private _cottageService: CottageService) {
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

  addCottage(submit: boolean) {
    this._cottageService.saveCottage(this.cottage).subscribe((data) => {
      this.additionalServiceTags.forEach((element) => {
        this._cottageService
          .addAdditionalService(element, this.cottage)
          .subscribe((additionalService) => {});
      });

      this.submitted.emit(submit);
    });
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
    console.log(input);
    this.additionalServiceTags.pop();
    this.additionalServiceTags.push({ id: 0, name: text[0], price: text[1] });
  }
}
