import { IDateSpan } from "./dateSpan";

export interface ICottageQuickReservation{
    id:number;
    duration : IDateSpan;
    validSpan : IDateSpan;
    guestCapacity: string;
    price: number;
}