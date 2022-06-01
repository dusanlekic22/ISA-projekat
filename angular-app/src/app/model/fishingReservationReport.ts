export interface IFishingReservationReport {
  id: number;
  userPenalized: boolean;
  comment: string;
  reservationReportStatus: ReservationReportStatus;
  answerOwner: string;
  answerCustomer: string;
}

export enum ReservationReportStatus {
  Positive,
  Negative,
  NoCustomer,
}
