import { CottageReservationsComponent } from './components/cottage/reservation/cottage-reservations/cottage-reservations.component';
import { AddFishingCourseComponent } from './pages/fishing-trainer-pages/add-fishing-course/add-fishing-course.component';
import { FooterComponent } from './components/footer/footer.component';
import { FishingCourseProfileComponent } from './pages/fishing-trainer-pages/fishing-course-profile/fishing-course-profile.component';
import { FishingTrainerCoursesComponent } from './pages/fishing-trainer-pages/fishing-trainer-courses/fishing-trainer-courses.component';
import { ChangePasswordComponent } from './pages/admin-pages/change-password/change-password.component';
import { DatePipe } from './pages/cottage-owner/pipes/date.pipe';
import { HasRoleDirective } from './directive/hasRole.directive';
import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { FormsModule } from '@angular/forms';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HomepageComponent } from './pages/homepage/homepage.component';
import { HeaderComponent } from './components/header/header.component';
import { NgImageSliderModule } from 'ng-image-slider';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { MatFormFieldModule } from '@angular/material/form-field';
import { RegistrationComponent } from './pages/registration/registration/registration.component';
import { NgpImagePickerModule } from 'ngp-image-picker';
import { MatTabsModule } from '@angular/material/tabs';
import { BaseCottageComponent } from './components/baseCottage/base-cottage.component';
import { ChooseRegistrationComponent } from './pages/registration/choose-registration/choose-registration.component';
import { MatSelectModule } from '@angular/material/select';
import { BusinessOwnerRegitrationComponent } from './pages/registration/business-owner-registration/business-owner-registration.component';
import { JwtInterceptor } from './interceptor/jwt.interceptor';
import { ErrorInterceptor } from './interceptor/error.interceptor';
import { CottageOwnerHomeComponent } from './pages/cottage-owner/cottage-owner-home/cottage-owner-home.component';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatNativeDateModule } from '@angular/material/core';
import {
  MDBBootstrapModule,
  ModalModule,
  TooltipModule,
  PopoverModule,
  ButtonsModule,
} from 'angular-bootstrap-md';
import { ReactiveFormsModule } from '@angular/forms';
import { AddCottageComponent } from './components/cottage/add-cottage/add-cottage.component';
import { TagInputModule } from 'ngx-chips';
import { CarouselModule } from 'ngx-owl-carousel-o';
import { OwlCarouselBaseComponent } from './pages/cottage-profile/view/owl-carousel-base.component';
import { CustomerCottageProfileComponent } from './pages/cottage-profile/view/customer-cottage-profile.component';
import { CustomerProfileComponent } from './pages/customer-profile/view/customer-profile.component';
import { ChangeCustomerInfoComponent } from './pages/customer-profile/edit/change-customer-info.component';
import { ToastrModule } from 'ngx-toastr';
import { CottageProfileComponent } from './pages/cottage-owner/cottage-profile/cottage-profile.component';
import { CottageOwnerProfileComponent } from './pages/cottage-owner/cottage-owner-profile/cottage-owner-profile.component';

import { MatCheckboxModule } from '@angular/material/checkbox';
import { MatChipsModule } from '@angular/material/chips';
import { CottageEditComponent } from './components/cottage/cottage-edit/cottage-edit.component';
import { CottageQuickReservationsComponent } from './components/cottage/reservation/cottage-quick-reservations/cottage-quick-reservations.component';
import { AddReservationComponent } from './components/cottage/reservation/add-reservation/add-reservation.component';
import { AddCottageQuickReservationComponent } from './components/cottage/reservation/add-cottage-quick-reservation/add-cottage-quick-reservation.component';
import { CottageAvailableTermsComponent } from './components/cottage/cottage-available-terms/cottage-available-terms.component';
import { BaseReservationComponent } from './components/base-reservation/base-reservation.component';
import { CottageReservationComponent } from './pages/reservation/cottage-reservation/cottage-reservation.component';

@NgModule({
  declarations: [
    AppComponent,
    HomepageComponent,
    HeaderComponent,
    FooterComponent,
    CottageProfileComponent,
    RegistrationComponent,
    CottageOwnerProfileComponent,
    BaseCottageComponent,
    BusinessOwnerRegitrationComponent,
    ChooseRegistrationComponent,
    HasRoleDirective,
    CottageOwnerHomeComponent,
    AddCottageComponent,
    OwlCarouselBaseComponent,
    CustomerCottageProfileComponent,
    CustomerProfileComponent,
    ChangeCustomerInfoComponent,
    DatePipe,
    CottageEditComponent,
    CottageQuickReservationsComponent,
    AddReservationComponent,
    AddCottageQuickReservationComponent,
    CottageAvailableTermsComponent,
    BaseReservationComponent,
    CottageReservationComponent,
    CottageReservationsComponent,
    ChangePasswordComponent,
    FishingTrainerCoursesComponent,
    FishingCourseProfileComponent,
    AddFishingCourseComponent
  ],
  imports: [
    NgbModule,
    NgImageSliderModule,
    BrowserModule,
    NgpImagePickerModule,
    FormsModule,
    MatButtonModule,
    MatIconModule,
    MatInputModule,
    MatFormFieldModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    HttpClientModule,
    MatTabsModule,
    MatSelectModule,
    MatDatepickerModule,
    MatNativeDateModule,
    ModalModule,
    TooltipModule,
    PopoverModule,
    ButtonsModule,
    ReactiveFormsModule,
    MDBBootstrapModule.forRoot(),
    TagInputModule,
    CarouselModule,
    ToastrModule.forRoot(),
    MatCheckboxModule,
    MatChipsModule,
  ],
  providers: [
    { provide: HTTP_INTERCEPTORS, useClass: JwtInterceptor, multi: true },
    { provide: HTTP_INTERCEPTORS, useClass: ErrorInterceptor, multi: true },
  ],
  bootstrap: [AppComponent],
})
export class AppModule {}
