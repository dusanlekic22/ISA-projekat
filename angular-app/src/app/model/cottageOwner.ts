import { ILoyaltyProgram } from './loyaltyProgram';
import { IUser } from './../pages/registration/registration/user';
import { IDateSpan } from './dateSpan';
import { ICottage } from './cottage';

export interface ICottageOwner extends IUser {
  cottage: ICottage[];
  unavailableReservationDateSpan: IDateSpan[];
  loyaltyProgram: ILoyaltyProgram;
}
