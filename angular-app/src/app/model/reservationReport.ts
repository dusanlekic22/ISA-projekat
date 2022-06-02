import { IBoatReservation } from 'src/app/model/boat/boatReservation';
import { ICottageReservation } from 'src/app/model/cottageReservation';
import { IFishingReservation } from 'src/app/model/fishingReservation';

export interface IReservationReport {
  id: number;
  userPenalized: boolean;
  comment: string;
  reservationReportStatus: ReservationReportStatus;
  fishingReservation: IFishingReservation;
  cottageReservation: ICottageReservation;
  boatReservation: IBoatReservation;
  answerOwner: string;
  answerCustomer: string;
}

export enum ReservationReportStatus {
  Positive,
  Negative,
  NoCustomer,
}
