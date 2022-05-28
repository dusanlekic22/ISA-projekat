import { ModalDirective } from 'angular-bootstrap-md';
import { IBusinessOwnerRegistrationRequest } from './../../../model/business-owner-registration-request';
import {
  Component,
  OnInit,
  Pipe,
  ViewChild,
  EventEmitter,
  Output,
} from '@angular/core';
import { RequestsService } from 'src/app/service/requests.service';

@Component({
  selector: 'app-registration-requests',
  templateUrl: './registration-requests.component.html',
  styleUrls: ['./registration-requests.component.css'],
})
export class RegistrationRequestsComponent implements OnInit {
  requests!: IBusinessOwnerRegistrationRequest[];

  constructor(private requestsService: RequestsService) {
    this.requestsService.submitedRequest$.subscribe(() => {
      this.getRequests();
    });
  }

  ngOnInit() {
    this.getRequests();
  }

  getRequests() {
    this.requestsService
      .getBusinessOwnerRegistrationRequests()
      .subscribe((registrationRequests) => {
        this.requests = registrationRequests;
      });
  }

  getStatus(request: IBusinessOwnerRegistrationRequest): string {
    if (request.accepted == null) return 'Waitting';
    else if (request.accepted) return 'Accepted';
    return 'Declined';
  }

  answer(requset: IBusinessOwnerRegistrationRequest) {
    this.requestsService.openDialog(requset);
  }
}
