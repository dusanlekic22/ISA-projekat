import { FishingTrainerService } from './../../../../service/fishingTrainer.service';
import { Component, Input, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { DeleteDialogComponent } from 'src/app/components/delete-dialog/delete-dialog.component';
import { IFishingTrainer } from 'src/app/model/fishingTrainer';
import { IUser } from 'src/app/pages/registration/registration/user';

@Component({
  selector: 'app-fishing-trainer-profile',
  templateUrl: './fishing-trainer-profile.component.html',
  styleUrls: ['../user-profile.component.css'],
})
export class FishingTrainerProfileComponent implements OnInit {
  @Input() userId!: number;
  @Input() user!: IUser;
  fishingTrainer!: IFishingTrainer;

  constructor(
    public dialog: MatDialog,
    private _fishingTrainerService: FishingTrainerService
  ) {}

  ngOnInit(): void {
    this._fishingTrainerService
      .getFishingTrainer()
      .subscribe((trainer) => (this.fishingTrainer = trainer));
  }

  openDialog(): void {
    const dialogRef = this.dialog.open(DeleteDialogComponent, {
      width: '250px',
      data: { email: this.user.email },
    });

    dialogRef.afterClosed().subscribe((result) => {
      console.log('The dialog was closed');
    });
  }

  updateBio(): void {
    this._fishingTrainerService
      .updateBio(this.fishingTrainer.id, this.fishingTrainer.biography)
      .subscribe((fishingTrainer) => {
        this.fishingTrainer = fishingTrainer;
      });
  }
}
