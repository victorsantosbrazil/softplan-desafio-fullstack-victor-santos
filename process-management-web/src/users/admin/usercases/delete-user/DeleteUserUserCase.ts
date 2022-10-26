export default interface GetUserUserCase {
  run(id: string): Promise<void>;
}
