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
import { CottageProfileComponent } from './pages/cottage-profile/edit/cottage-profile.component';
import { NgImageSliderModule } from 'ng-image-slider';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { MatFormFieldModule } from '@angular/material/form-field';
import { RegistrationComponent } from './pages/registration/registration/registration.component';
import { CottageOwnerProfileComponent } from './pages/cottage-owner-profile/cottage-owner-profile.component';
import { NgpImagePickerModule } from 'ngp-image-picker';
import { MatTabsModule } from '@angular/material/tabs';
import { BaseCottageComponent } from './components/baseCottage/base-cottage.component';
import { ChooseRegistrationComponent } from './pages/registration/choose-registration/choose-registration.component';
import { MatSelectModule } from '@angular/material/select';
import { BusinessOwnerRegitrationComponent } from './pages/registration/business-owner-registration/business-owner-registration.component';
import { JwtInterceptor } from './interceptor/jwt.interceptor';
import { ErrorInterceptor } from './interceptor/error.interceptor';
import { CarouselModule } from 'ngx-owl-carousel-o';
import { OwlCarouselBaseComponent } from './pages/cottage-profile/view/owl-carousel-base.component';
import { CustomerCottageProfileComponent } from './pages/cottage-profile/view/customer-cottage-profile.component';

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
    OwlCarouselBaseComponent,
    CustomerCottageProfileComponent
  ],
  imports: [
    NgImageSliderModule,
    NgpImagePickerModule,
    FormsModule,
    MatButtonModule,
    MatIconModule,
    MatInputModule,
    MatFormFieldModule,
    BrowserModule,
    AppRoutingModule,
    NgbModule,
    BrowserAnimationsModule,
    HttpClientModule,
    MatTabsModule,
    MatSelectModule,
    CarouselModule 
  ],
  providers: [
    { provide: HTTP_INTERCEPTORS, useClass: JwtInterceptor, multi: true },
    { provide: HTTP_INTERCEPTORS, useClass: ErrorInterceptor, multi: true },
  ],
  bootstrap: [AppComponent],
})
export class AppModule {}
