import { ComplaintService } from 'src/app/service/complaint.service';
import { IComplaint } from 'src/app/model/complaint';
import { Component, OnInit } from '@angular/core';
import { RequestsService } from 'src/app/service/requests.service';

@Component({
  selector: 'app-complaints',
  templateUrl: './complaints.component.html',
  styleUrls: ['./complaints.component.css'],
})
export class ComplaintsComponent implements OnInit {
  requests: IComplaint[] = [];

  constructor(
    private requestsService: RequestsService,
    private complaintService: ComplaintService
  ) {
    this.requestsService.submitedDialogComplaint$.subscribe(() => {
      this.getRequests();
    });
  }

  ngOnInit(): void {
    this.getRequests();
  }

  getRequests() {
    this.complaintService
      .getAllComplaints()
      .subscribe((request: IComplaint[]) => {
        this.requests = request;
      });
  }

  getOwner(request: any) {
    return ""
  }

  getCustomer(request: any) {
    return ""
  }

  answer(request: IComplaint) {
    this.requestsService.openDialogComplaint(request);
  }

}
