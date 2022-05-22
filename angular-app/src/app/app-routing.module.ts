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
    path: 'cottageOwnerHome/:id',
    component: CottageOwnerHomeComponent
  },
  {
    path: 'cottageProfile/:cottageId',
    component: CottageProfileComponent,
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
  { path: '**', redirectTo: '' },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
