import Pageable from "./Pageable";

export default class Page<T> {
  content: T[];
  pageable: Pageable;

  constructor(content: T[], pageable: Pageable) {
    this.content = content;
    this.pageable = pageable;
  }
}
