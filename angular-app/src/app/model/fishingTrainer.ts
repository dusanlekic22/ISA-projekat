import { IFishingCourse } from './fishingCourse';
import { IUser } from './../pages/registration/registration/user';
import { IDateSpan } from './dateSpan';

export interface IFishingTrainer extends IUser {
  biography: string;
  fishingCourse: IFishingCourse[];
  availableReservationDateSpan: IDateSpan[];
  unavailableReservationDateSpan: IDateSpan[];
}
