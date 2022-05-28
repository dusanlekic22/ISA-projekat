export interface IReservationCount {
  yearlySum: number[];
  monthlySum: number[];
  weeklySum: number[];
}

export const monthsLabels: string[] = [
  'January',
  'February',
  'March',
  'April',
  'May',
  'June',
  'July',
  'August',
  'September',
  'October',
  'November',
  'December',
];

export const yearLabels: string[] = [
  (new Date().getFullYear() - 2).toString(),
  (new Date().getFullYear() - 1).toString(),
  new Date().getFullYear().toString(),
  (new Date().getFullYear() + 1).toString(),
];

export const initialData : number[] = [
  10, 20, 30, 40, 50, 60, 70, 80, 90, 100, 110, 120, 130, 140, 150, 160, 170,
  180, 190, 200, 210, 220, 230, 240, 250, 260, 270, 280, 290, 300, 310,
];
