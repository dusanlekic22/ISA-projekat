import { IDateSpan } from "./dateSpan";

export interface ICottageQuickReservation{
    id:number;
    duration : IDateSpan;
    guestCapacity: number;
    price: number;
}