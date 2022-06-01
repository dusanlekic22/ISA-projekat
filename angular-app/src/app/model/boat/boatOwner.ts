import { IBoat } from 'src/app/model/boat/boat';
import { IUser } from 'src/app/pages/registration/registration/user';
import { IDateSpan } from '../dateSpan';

export interface IBoatOwner extends IUser {
  boat: IBoat[];
  unavailableReservationDateSpan: IDateSpan[];
}
