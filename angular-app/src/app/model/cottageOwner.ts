import { ILoyaltyProgram } from './loyaltyProgram';
import { IUser } from './../pages/registration/registration/user';
import { IDateSpan } from './dateSpan';
import { ICottage } from './cottage';
import { IGrade } from './grade';

export interface ICottageOwner extends IUser {
  cottage: ICottage[];
  unavailableReservationDateSpan: IDateSpan[];
  loyaltyProgram: ILoyaltyProgram;
  grades: IGrade[];
}
