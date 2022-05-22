import { IFishingCourse } from './../../../model/fishingCourse';
import { FishingCourseService } from './../../../service/fishingCourse.service';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-fishing-trainer-courses',
  templateUrl: './fishing-trainer-courses.component.html',
  styleUrls: ['./fishing-trainer-courses.component.css'],
})
export class FishingTrainerCoursesComponent implements OnInit {
  fishingCourses!: IFishingCourse[];

  constructor(private fishingCourseService: FishingCourseService) {}

  ngOnInit() {
    this.getFishingCourses();
  }

  getFishingCourses() {
    this.fishingCourseService
      .getFishingCourses()
      .subscribe((fishingCourses: IFishingCourse[]) => {
        this.fishingCourses = fishingCourses;
      });
  }
}
