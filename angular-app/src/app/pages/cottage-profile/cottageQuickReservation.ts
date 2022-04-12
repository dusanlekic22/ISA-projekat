import { IDateSpan } from "./dateSpan";

export interface ICottageQuickReservation{
    id:number;
    dateSpan : IDateSpan;
    guestCapacity: string;
    price: number;
}