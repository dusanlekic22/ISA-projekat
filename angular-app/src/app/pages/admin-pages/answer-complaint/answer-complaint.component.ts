import { Component, OnInit, ViewChild } from '@angular/core';
import { ModalDirective } from 'angular-bootstrap-md';
import { emptyBoatReservation } from 'src/app/model/boat/boatReservation';
import { emptyCottageReservation } from 'src/app/model/cottageReservation';
import { emptyFishingReservation } from 'src/app/model/fishingReservation';

@Component({
  selector: 'app-answer-complaint',
  templateUrl: './answer-complaint.component.html',
  styleUrls: ['./answer-complaint.component.css'],
})
export class AnswerComplaintComponent implements OnInit {
  @ViewChild('complaint') complaintModal!: ModalDirective;
  request: any = {
    id: 0,
    comment: '',
    answerOwner: '',
    answerCustomer: '',
    fishingReservation: emptyFishingReservation,
    cottageReservation: emptyCottageReservation,
    boatReservation: emptyBoatReservation,
  };

  constructor() {}

  ngOnInit(): void {}

  show() {
    this.complaintModal.show();
  }

  close() {
    this.complaintModal.hide();
  }

  getOwner(request: any) {

  }

  getCustomer(request: any) {

  }

  answer() {}
}
