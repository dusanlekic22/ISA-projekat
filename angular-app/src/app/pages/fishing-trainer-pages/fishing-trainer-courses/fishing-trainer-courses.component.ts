import { ModalDirective } from 'angular-bootstrap-md';
import { Router } from '@angular/router';
import { IUser } from './../../registration/registration/user';
import { map } from 'rxjs/operators';
import { UserService } from 'src/app/service/user.service';
import { IFishingCourse } from './../../../model/fishingCourse';
import { FishingCourseService } from './../../../service/fishingCourse.service';
import { Component, OnInit, ViewChild } from '@angular/core';

@Component({
  selector: 'app-fishing-trainer-courses',
  templateUrl: './fishing-trainer-courses.component.html',
  styleUrls: ['./fishing-trainer-courses.component.css'],
})
export class FishingTrainerCoursesComponent implements OnInit {
  fishingCourses!: IFishingCourse[];
  filteredCourses!: IFishingCourse[];
  filter: string = '';
  fishingTrainer!: IUser;

  constructor(
    private router: Router,
    private fishingCourseService: FishingCourseService,
    private userService: UserService
  ) {}

  ngOnInit() {
    this.userService.currentUser.subscribe((user) => {
      if (user.id != undefined) {
        this.fishingTrainer = user;
        this.getFishingCourses(user.id);
      }
    });
  }

  getFishingCourses(id: number) {
    this.fishingCourseService
      .getFishingTrainerCourses(id)
      .subscribe((fishingCourses: IFishingCourse[]) => {
        this.fishingCourses = fishingCourses;
        this.filteredCourses = fishingCourses;
      });
  }

  searchFishingCourses(filter: string) {
    if (filter != '')
      this.filteredCourses = this.fishingCourses.filter((fc) =>
        fc.name.toLowerCase().includes(filter.toLowerCase())
      );
    else this.filteredCourses = this.fishingCourses;
  }

  deleteFishingCourses(id: number) {
    this.fishingCourseService.deleteFishingCourse(id).subscribe(() => {
      this.getFishingCourses(this.fishingTrainer.id);
    });
  }

  fishingCourseProfile(id: number) {
    this.router.navigateByUrl(`fishingCourseProfile/${id}`);
  }

  added(submitted: boolean) {
    if (submitted) {
      this.getFishingCourses(this.fishingTrainer.id);
    }
  }
}
