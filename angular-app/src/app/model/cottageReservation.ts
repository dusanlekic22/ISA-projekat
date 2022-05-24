import { ICustomer } from "./customer";
import { IDateSpan } from "./dateSpan";

export interface ICottageReservation{
    id:number;
    duration: IDateSpan;
    guestCapacity: number;
    price: number;
    customer: ICustomer;
}