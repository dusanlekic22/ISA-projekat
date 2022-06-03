import { IUser } from 'src/app/pages/registration/registration/user';
export interface IGrade{
    value:number;
    user:IUser;
    isAccepted:boolean;
}