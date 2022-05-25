import { IFishingCourse } from './fishingCourse';
import { IUser } from './../pages/registration/registration/user';

export interface IFishingTrainer extends IUser {
  biography: string;
  fishingCourse: IFishingCourse[];
}
