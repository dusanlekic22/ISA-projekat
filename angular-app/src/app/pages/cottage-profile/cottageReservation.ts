import { IDateSpan } from "./dateSpan";

export interface ICottageReservation{
    id:number;
    duration: IDateSpan;
    guestCapacity: string;
    price: number;
}