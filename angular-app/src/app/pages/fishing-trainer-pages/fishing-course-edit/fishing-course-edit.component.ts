import { ActivatedRoute } from '@angular/router';
import { IFishingTrainer } from './../../../model/fishingTrainer';
import { ToastrService } from 'ngx-toastr';
import { AdditionalServiceService } from 'src/app/pages/cottage-owner/services/additional-service.service';
import { FishingCourseService } from './../../../service/fishingCourse.service';
import { IAdditionalService } from 'src/app/model/additionalService';
import { IFishingCourse } from 'src/app/model/fishingCourse';
import { Component, OnInit, Input } from '@angular/core';
import { IAddress } from 'src/app/model/address';

@Component({
  selector: 'app-fishing-course-edit',
  templateUrl: './fishing-course-edit.component.html',
  styleUrls: ['./fishing-course-edit.component.css'],
})
export class FishingCourseEditComponent implements OnInit {
  @Input() fishingCourse!: IFishingCourse;
  additionalServiceTags: IAdditionalService[] = [];

  constructor(
    private fishingCourseService: FishingCourseService,
    private additionalServiceService: AdditionalServiceService,
    private toastr: ToastrService,
    private route: ActivatedRoute
  ) {}

  ngOnInit(): void {
    let id = +this.route.snapshot.paramMap.get('id')!;
    this.additionalServiceService
      .getAdditionalServicesByFishingCourseId(id)
      .subscribe((additionalService) => {
        this.additionalServiceTags = additionalService;
      });
  }

  setAddress(address:IAddress){
    this.fishingCourse.address = address;
  }

  edit(): void {
    this.fishingCourseService.updateFishingCourse(this.fishingCourse).subscribe(
      (fishingCourse: IFishingCourse) => {
        this.toastr.success('FishingCourse information successfully changed.');
        this.fishingCourse = fishingCourse;
        this.additionalServiceTags.forEach((element) => {
          this.additionalServiceService
            .addAdditionalServiceForFishingCourse(element, fishingCourse)
            .subscribe((additionalService) => {});
        });
      },
      (error) => {
        this.toastr.error(
          "You can't edit a fishingCourse that has active reservations!"
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
