import { UserService } from 'src/app/service/user.service';
import { Component, Input, OnInit } from '@angular/core';
import { IUser } from 'src/app/pages/registration/registration/user';
import { MatDialog } from '@angular/material/dialog';
import { DeleteDialogComponent } from 'src/app/components/delete-dialog/delete-dialog.component';

@Component({
  selector: 'app-owner-cottage-profile',
  templateUrl: './owner-cottage-profile.component.html',
  styleUrls: ['../user-profile.component.css'],
})
export class OwnerCottageProfileComponent implements OnInit {
  @Input() userId!: number;
  @Input() user!: IUser;

  constructor(public dialog: MatDialog) {}

  ngOnInit(): void {}

  openDialog(): void {
    const dialogRef = this.dialog.open(DeleteDialogComponent, {
      width: '250px',
      data: {email: this.user.email},
    });

    dialogRef.afterClosed().subscribe(result => {
      console.log('The dialog was closed');
    });
  }

}
