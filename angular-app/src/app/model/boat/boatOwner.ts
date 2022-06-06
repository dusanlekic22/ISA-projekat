import { IBoat } from 'src/app/model/boat/boat';
import { IUser } from 'src/app/pages/registration/registration/user';
import { IDateSpan } from '../dateSpan';
import { IGrade } from '../grade';
import { ILoyaltyProgram } from '../loyaltyProgram';

export interface IBoatOwner extends IUser {
  boat: IBoat[];
  unavailableReservationDateSpan: IDateSpan[];
  loyaltyProgram: ILoyaltyProgram;
  grades: IGrade[];
}
