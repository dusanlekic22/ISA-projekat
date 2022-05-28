import { Component, Input, OnInit } from '@angular/core';
import { AdditionalServiceService } from 'src/app/pages/cottage-owner/services/additional-service.service';
import { CottageService } from 'src/app/pages/cottage-owner/services/cottage.service';
import { IAdditionalService } from 'src/app/model/additionalService';
import { ToastrService } from 'ngx-toastr';
import { ICottage } from 'src/app/model/cottage';
import { ActivatedRoute } from '@angular/router';
import { IAddress } from 'src/app/model/address';

@Component({
  selector: 'app-cottage-edit',
  templateUrl: './cottage-edit.component.html',
  styleUrls: ['./cottage-edit.component.css'],
})
export class CottageEditComponent implements OnInit {
  @Input() cottage!: ICottage;
  additionalServiceTags: IAdditionalService[] = [];

  constructor(
    private _cottageService: CottageService,
    private _additionalServiceService: AdditionalServiceService,
    private _toastr: ToastrService,
    private _route: ActivatedRoute
  ) {}

  ngOnInit(): void {
    let cottageId = +this._route.snapshot.paramMap.get('cottageId')!;
    this._additionalServiceService
      .getAdditionalServicesByCottageId(cottageId)
      .subscribe((additionalService) => {
        this.additionalServiceTags = additionalService.filter(
          (additionalService) => additionalService.name != null
        );
      });
  }

  setAddress(address:IAddress){
    this.cottage.address = address;
  }

  edit(): void {
    this._cottageService.editCottageInfo(this.cottage).subscribe(
      (cottage) => {
        this._toastr.success('Cottage information successfully changed.');
        this.cottage = cottage;
        this.additionalServiceTags.forEach((element) => {
          this._additionalServiceService
            .addAdditionalServiceForCottage(element, this.cottage)
            .subscribe((additionalService) => {});
        });
      },
      (error) => {
        this._toastr.error(
          "You can't edit a cottage that has active reservations!"
        );
      }
    );
  }

  onItemAdded(input: any): void {
    let text = input.display.split(' ');
    this.additionalServiceTags.pop();
    this.additionalServiceTags.push({ id: 0, name: text[0], price: text[1] });
  }
}
