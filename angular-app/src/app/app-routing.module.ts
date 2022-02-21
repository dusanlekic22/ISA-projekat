import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { CottageOwnerHomeComponent } from './pages/cottage-owner-home/cottage-owner-home.component';
import { CottageOwnerProfileComponent } from './pages/cottage-owner-profile/cottage-owner-profile.component';
import { CottageProfileComponent } from './pages/cottage-profile/cottage-profile.component';
import { HomepageComponent } from './pages/homepage/homepage.component';
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
    path: 'cottageProfile/:cottageId',
    component: CottageProfileComponent,
  },
  {
    path: 'registration',
    component: RegistrationComponent,
  },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
