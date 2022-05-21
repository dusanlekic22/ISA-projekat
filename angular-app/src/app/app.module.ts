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
import { CottageProfileComponent } from './pages/cottage-owner/cottage-profile/cottage-profile.component';
import { NgImageSliderModule } from 'ng-image-slider';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { MatFormFieldModule } from '@angular/material/form-field';
import { RegistrationComponent } from './pages/registration/registration/registration.component';
import { CottageOwnerProfileComponent } from './pages/cottage-owner/cottage-owner-profile/cottage-owner-profile.component';
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
import { MatNativeDateModule, MAT_DATE_LOCALE } from '@angular/material/core';
import { MDBBootstrapModule, ModalModule,TooltipModule,PopoverModule,ButtonsModule} from 'angular-bootstrap-md';
import { ReactiveFormsModule } from '@angular/forms';
import { AddCottageComponent } from './pages/cottage-owner/add-cottage/add-cottage.component';
import { TagInputModule } from 'ngx-chips';
import { ToastrModule} from 'ngx-toastr';
import { DatePipe } from './pages/cottage-owner/pipes/date.pipe';


@NgModule({
  declarations: [
    AppComponent,
    HomepageComponent,
    HeaderComponent,
    CottageProfileComponent,
    RegistrationComponent,
    CottageOwnerProfileComponent,
    BaseCottageComponent,
    BusinessOwnerRegitrationComponent,
    ChooseRegistrationComponent,
    HasRoleDirective,
    CottageOwnerHomeComponent,
    AddCottageComponent,
    DatePipe,
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
    ToastrModule.forRoot(),
  ],
  providers: [{ provide: MAT_DATE_LOCALE, useValue: 'en-GB' },
  { provide: HTTP_INTERCEPTORS, useClass: JwtInterceptor, multi: true },
  { provide: HTTP_INTERCEPTORS, useClass: ErrorInterceptor, multi: true },],
  bootstrap: [AppComponent],
})
export class AppModule { }
