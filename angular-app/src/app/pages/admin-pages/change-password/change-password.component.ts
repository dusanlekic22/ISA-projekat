import { IUser } from './../../registration/registration/user';
import { UserService } from 'src/app/service/user.service';
import { AuthenticationService } from 'src/app/service/authentication.service';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { ModalDirective } from 'angular-bootstrap-md';
import {
  Component,
  OnInit,
  ViewChild,
  Input,
  SimpleChanges,
} from '@angular/core';

@Component({
  selector: 'app-change-password',
  templateUrl: './change-password.component.html',
  styleUrls: ['./change-password.component.css'],
})
export class ChangePasswordComponent implements OnInit {
  @Input('loggedInUser') loggedInUser!: IUser;
  @ViewChild('frame') basicModal!: ModalDirective;

  changePasswordForm: FormGroup;
  changePassword!: string;
  confirmChangePassword!: string;

  errorMessage!: string;

  constructor(
    private userService: UserService,
    private authenticationService: AuthenticationService
  ) {
    this.changePasswordForm = new FormGroup({
      formModalPassword: new FormControl('', Validators.required),
      formModalConfirmPassword: new FormControl('', Validators.required),
    });
  }

  ngOnInit() {
    if (this.isUserAdmin()) {
      this.checkFirstTimeLoginIn();
    }
  }

  get formModalPassword() {
    return this.changePasswordForm.get('formModalPassword');
  }

  get formModalConfirmPassword() {
    return this.changePasswordForm.get('formModalConfirmPassword');
  }

  openModal(): void {
    this.basicModal.show();
    this.basicModal.config.backdrop = 'static';
    this.basicModal.config.keyboard = false;
  }

  submitChangePassword(): void {
    this.userService.changePassword(this.changePassword).subscribe({
      next: () => {
        this.closeModal();
      },
      error: (error) => {
        this.errorMessage = error.message;
        console.error('There was an error!', error);
      },
    });
  }

  checkFirstTimeLoginIn(): void {
    this.authenticationService.isAdminFirstTimeLoggedIn().subscribe({
      next: (isFirstTimeLoggedIn) => {
        if (isFirstTimeLoggedIn) this.openModal();
      },
      error: (error) => {
        this.errorMessage = error.message;
        console.error('There was an error!', error);
      },
    });
  }

  closeModal(): void {
    this.basicModal.hide();
  }

  isUserAdmin(): boolean {
    if (!this.loggedInUser) return false;
    return this.loggedInUser.roles.some((role) => role.name === 'ROLE_ADMIN');
  }

  ngOnChanges(changes: SimpleChanges): void {
    if (this.isUserAdmin()) {
      this.checkFirstTimeLoginIn();
    }
  }
}
