import { Component, Input, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { DeleteDialogComponent } from 'src/app/components/delete-dialog/delete-dialog.component';
import { IUser } from 'src/app/pages/registration/registration/user';

@Component({
  selector: 'app-owner-boat-profile',
  templateUrl: './owner-boat-profile.component.html',
  styleUrls: ['./owner-boat-profile.component.css']
})
export class OwnerBoatProfileComponent implements OnInit {

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
