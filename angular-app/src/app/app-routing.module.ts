import { CottageReservationComponent } from './pages/reservation/cottage-reservation/cottage-reservation.component';
import { FishingCourseProfileComponent } from './pages/fishing-trainer-pages/fishing-course-profile/fishing-course-profile.component';
import { FishingTrainerCoursesComponent } from './pages/fishing-trainer-pages/fishing-trainer-courses/fishing-trainer-courses.component';
import { CustomerProfileComponent } from './pages/customer-profile/view/customer-profile.component';
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AuthGuard } from './guard/auth.guard';
import { Role } from './model/role.enum';
import { CustomerCottageProfileComponent } from './pages/cottage-profile/view/customer-cottage-profile.component';
import { CottageOwnerHomeComponent } from './pages/cottage-owner/cottage-owner-home/cottage-owner-home.component';
import { CottageOwnerProfileComponent } from './pages/cottage-owner/cottage-owner-profile/cottage-owner-profile.component';
import { CottageProfileComponent } from './pages/cottage-owner/cottage-profile/cottage-profile.component';
import { HomepageComponent } from './pages/homepage/homepage.component';
import { BusinessOwnerRegitrationComponent } from './pages/registration/business-owner-registration/business-owner-registration.component';
import { ChooseRegistrationComponent } from './pages/registration/choose-registration/choose-registration.component';
import { RegistrationComponent } from './pages/registration/registration/registration.component';
import { AddReservationComponent } from './components/cottage/reservation/add-reservation/add-reservation.component';
import { AddCottageQuickReservationComponent } from './components/cottage/reservation/add-cottage-quick-reservation/add-cottage-quick-reservation.component';
import { AddBoatQuickReservationComponent } from './components/boat/reservation/add-boat-quick-reservation/add-boat-quick-reservation.component';
import { AddBoatReservationComponent } from './components/boat/reservation/add-boat-reservation/add-boat-reservation.component';
import { BoatOwnerHomeComponent } from './pages/boat-owner/boat-owner-home/boat-owner-home.component';
import { BoatOwnerProfileComponent } from './pages/boat-owner/boat-owner-profile/boat-owner-profile.component';
import { BoatProfileComponent } from './pages/boat-owner/boat-profile/boat-profile.component';
import { RequestsComponent } from './pages/admin-pages/requests/requests.component';

const routes: Routes = [
  {
    path: '',
    component: HomepageComponent,
  },
  {
    path: 'cottageOwnerProfile',
    component: CottageOwnerProfileComponent,
  },
  {
    path: 'newCottageReservation',
    component: AddReservationComponent,
  },
  {
    path: 'newCottageQuickReservation',
    component: AddCottageQuickReservationComponent,
  },
  {
    path: 'cottageOwnerHome',
    component: CottageOwnerHomeComponent,
  },
  {
    path: 'cottageProfile/:cottageId',
    component: CottageProfileComponent,
  },
  {
    path: 'boatOwnerProfile',
    component: BoatOwnerProfileComponent,
  },
  {
    path: 'newBoatReservation',
    component: AddBoatReservationComponent,
  },
  {
    path: 'newBoatQuickReservation',
    component: AddBoatQuickReservationComponent,
  },
  {
    path: 'boatOwnerHome',
    component: BoatOwnerHomeComponent
  },
  {
    path: 'boatProfile/:boatId',
    component: BoatProfileComponent,
  },
  {
    path: 'cottage/:cottageId',
    component: CustomerCottageProfileComponent,
  },
  {
    path: 'registration',
    component: RegistrationComponent,
  },
  {
    path: 'businessOwnerRegistration',
    component: BusinessOwnerRegitrationComponent,
  },
  {
    path: 'chooseRegistration',
    component: ChooseRegistrationComponent,
  },
  {
    path: 'customer/:id',
    component: CustomerProfileComponent,
  },
  {
    path: 'cottage/:id/:startDate/:endDate',
    component: CottageReservationComponent,
  },
  { path: 'fishingTrainerCourses',
    component: FishingTrainerCoursesComponent
  },
  {
    path: 'fishingCourseProfile/:id',
    component: FishingCourseProfileComponent,
  },
  {
    path: 'requests',
    component: RequestsComponent
  },
  { path: '**', redirectTo: '' },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
