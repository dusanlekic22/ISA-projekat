export interface ISortType {
  text: string;
  field: string;
  direction: string;
}

export const sortTypes: ISortType[] = [
  {
    text: 'Price ascending',
    field: 'price_per_hour',
    direction: 'asc',
  },
  {
    text: 'Price descending',
    field: 'price_per_hour',
    direction: 'desc',
  },
  {
    text: 'Grade ascending',
    field: 'average_grade',
    direction: 'asc',
  },
  {
    text: 'Grade descending',
    field: 'average_grade',
    direction: 'desc',
  },
  {
    text: 'Name ascending',
    field: 'name',
    direction: 'asc',
  },
  {
    text: 'Name descending',
    field: 'name',
    direction: 'desc',
  },
  {
    text: 'Location descending',
    field: 'latitude',
    direction: 'desc',
  },
  {
    text: 'Location asscending',
    field: 'latitude',
    direction: 'asc',
  },
];

export const initSortType: ISortType = {
  text: '',
  direction: '',
  field: '',
};

export const emptySortType: ISortType = {
  text: '',
  field: 'name',
  direction: 'asc',
};

export const priceSortType: ISortType = {
  text: 'Price ascending',
  field: 'price_per_hour',
  direction: 'desc',
};

export const gradeSortType: ISortType = {
  text: 'Grade ascending',
  field: 'average_grade',
  direction: 'desc',
};

export const sortReservationTypes: ISortType[] = [
  {
    text: 'Start date ascending',
    field: 'start_date',
    direction: 'asc',
  },
  {
    text: 'Start date descending',
    field: 'start_date',
    direction: 'desc',
  },
  {
    text: 'Price ascending',
    field: 'price',
    direction: 'asc',
  },
  {
    text: 'Price descending',
    field: 'price',
    direction: 'desc',
  },
  {
    text: 'Duration ascending',
    field: 'duration',
    direction: 'asc',
  },
  {
    text: 'Duration descending',
    field: 'duration',
    direction: 'desc',
  },
];
