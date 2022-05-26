import { IDateSpan } from "../dateSpan";

export interface IBoatQuickReservation{
    id:number;
    duration : IDateSpan;
    guestCapacity: number;
    price: number;
}