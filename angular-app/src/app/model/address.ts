export interface IAddress {
  street: string;
  city: string;
  country: string;
  latitude: number;
  longitude: number;
}

export const emptyAddress = {
  street: '',
  city: '',
  country: '',
  latitude: 45,
  longitude: 20.6,
};
