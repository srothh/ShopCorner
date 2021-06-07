export class IPageable<T> {
  page: number;
  pageSize: number;
  totalPages: number;
  collectionSize: number;
  items: T[];
}
