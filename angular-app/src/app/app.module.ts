import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { FormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HomepageComponent } from './pages/homepage/homepage.component';
import { HeaderComponent } from './components/header/header.component';
import { CottageProfileComponent } from './pages/cottage-profile/cottage-profile.component';
import { NgImageSliderModule } from 'ng-image-slider';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { MatFormFieldModule } from '@angular/material/form-field';
import { RegistrationComponent } from './pages/registration/registration/registration.component';
import { CottageOwnerProfileComponent } from './pages/cottage-owner-profile/cottage-owner-profile.component';
import { NgpImagePickerModule } from 'ngp-image-picker';
import { CottageOwnerHomeComponent } from './pages/cottage-owner-home/cottage-owner-home.component';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatNativeDateModule, MAT_DATE_LOCALE } from '@angular/material/core';
import { MDBBootstrapModule, ModalModule,TooltipModule,PopoverModule,ButtonsModule} from 'angular-bootstrap-md';
import { ReactiveFormsModule } from '@angular/forms';
import { AddCottageComponent } from './pages/add-cottage/add-cottage.component';

@NgModule({
  declarations: [
    AppComponent,
    HomepageComponent,
    HeaderComponent,
    CottageProfileComponent,
    RegistrationComponent,
    CottageOwnerProfileComponent,
    CottageOwnerHomeComponent,
    AddCottageComponent,
  ],
  imports: [
    NgbModule,
    BrowserAnimationsModule,
    NgImageSliderModule,
    BrowserModule,
    NgpImagePickerModule,
    FormsModule,
    MatButtonModule,
    MatIconModule,
    MatInputModule,
    MatFormFieldModule,
    AppRoutingModule,
    HttpClientModule,
    MatDatepickerModule,
    MatNativeDateModule,
    ModalModule,
    TooltipModule,
    PopoverModule,
    ButtonsModule,
    ReactiveFormsModule,
    MDBBootstrapModule.forRoot()
  ],
  providers: [{ provide: MAT_DATE_LOCALE, useValue: 'en-GB' }],
  bootstrap: [AppComponent],
})
export class AppModule {}
