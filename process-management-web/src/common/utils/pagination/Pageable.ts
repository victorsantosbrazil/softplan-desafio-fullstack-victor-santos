export default class Pageable {
  pageNumber: number;
  isFirst: boolean;
  isLast: boolean;

  constructor(attrs: {
    pageNumber: number;
    isFirst: boolean;
    isLast: boolean;
  }) {
    this.pageNumber = attrs.pageNumber;
    this.isFirst = attrs.isFirst;
    this.isLast = attrs.isLast;
  }
}
