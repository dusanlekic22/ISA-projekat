import { IDateSpan } from "./dateSpan";

export interface ICottageReservation{
    id:number;
    dateSpan: IDateSpan;
    guestCapacity: string;
    price: number;
}