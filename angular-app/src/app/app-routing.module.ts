import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { CottageOwnerHomeComponent } from './pages/cottage-owner-home/cottage-owner-home.component';
import { CottageOwnerProfileComponent } from './pages/cottage-owner-profile/cottage-owner-profile.component';
import { CottageProfileComponent } from './pages/cottage-profile/cottage-profile.component';
import { HomepageComponent } from './pages/homepage/homepage.component';
import { BusinessOwnerRegitrationComponent } from './pages/registration/business-owner-regitration/business-owner-registration.component';
import { ChooseRegistrationComponent } from './pages/registration/choose-registration/choose-registration.component';
import { RegistrationComponent } from './pages/registration/registration/registration.component';

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
    path: 'cottageOwnerHome',
    component: CottageOwnerHomeComponent,
  },
  {
    path: 'cottageProfile',
    component: CottageProfileComponent,
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
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule { }
