import { RegisterAdminComponent } from './pages/admin-pages/register-admin/register-admin.component';
import { ReservationsComponent } from './pages/reservations/reservations.component';
import { AplicationIncomeComponent } from './pages/admin-pages/aplication-income/aplication-income.component';
import { LoyaltySettingsComponent } from './pages/admin-pages/loyalty-settings/loyalty-settings.component';
import { UserProfileComponent } from './pages/user-profile/view/user-profile.component';
import { CottageReservationComponent } from './pages/reservation/cottage-reservation/cottage-reservation.component';
import { FishingCourseProfileComponent } from './pages/fishing-trainer-pages/fishing-course-profile/fishing-course-profile.component';
import { FishingTrainerCoursesComponent } from './pages/fishing-trainer-pages/fishing-trainer-courses/fishing-trainer-courses.component';
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { CustomerCottageProfileComponent } from './pages/cottage-profile/view/customer-cottage-profile.component';
import { CottageOwnerHomeComponent } from './pages/cottage-owner/cottage-owner-home/cottage-owner-home.component';
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
import { AddCottageComponent } from './components/cottage/add-cottage/add-cottage.component';
import { AddBoatComponent } from './components/boat/add-boat/add-boat.component';
import { CustomerBoatProfileComponent } from './pages/boat-profile/customer-boat-profile/customer-boat-profile.component';
import { CustomerFishingTrainerProfileComponent } from './pages/fishing-trainer-profile/customer-fishing-trainer-profile/customer-fishing-trainer-profile.component';
import { BoatReservationComponent } from './pages/reservation/boat-reservation/boat-reservation.component';
import { FishingCourseReservationComponent } from './pages/reservation/fishing-course-reservation/fishing-course-reservation.component';
import { BussinesOwnerCustomerInfoComponent } from './pages/user-profile/view/bussines-owner-customer-info/bussines-owner-customer-info.component';

const routes: Routes = [
  {
    path: '',
    component: HomepageComponent,
  },
  {
    path: 'newCottage',
    component: AddCottageComponent,
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
    path: 'newBoat',
    component: AddBoatComponent,
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
    component: BoatOwnerHomeComponent,
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
    path: 'boat/:boatId',
    component: CustomerBoatProfileComponent,
  },
  {
    path: 'customerInfo/:userId',
    component: BussinesOwnerCustomerInfoComponent,
  },
  {
    path: 'fishingTrainer/:fishingTrainerId',
    component: CustomerFishingTrainerProfileComponent,
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
    path: 'user/:id',
    component: UserProfileComponent,
  },
  {
    path: 'cottage/:id/:startDate/:endDate',
    component: CottageReservationComponent,
  },
  {
    path: 'boat/:id/:startDate/:endDate',
    component: BoatReservationComponent,
  },
  {
    path: 'fishingCourse/:id/:startDate/:endDate',
    component: FishingCourseReservationComponent,
  },
  { path: 'fishingTrainerCourses', component: FishingTrainerCoursesComponent },
  {
    path: 'fishingCourseProfile/:id',
    component: FishingCourseProfileComponent,
  },
  {
    path: 'reservationHistory/:id',
    component: ReservationsComponent,
  },
  {
    path: 'requests',
    component: RequestsComponent,
  },
  {
    path: 'loyaltySettings',
    component: LoyaltySettingsComponent,
  },
  {
    path: 'aplicationIncome',
    component: AplicationIncomeComponent,
  },
  {
    path: 'registerAdmin',
    component: RegisterAdminComponent,
  },
  { path: '**', redirectTo: '' },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
