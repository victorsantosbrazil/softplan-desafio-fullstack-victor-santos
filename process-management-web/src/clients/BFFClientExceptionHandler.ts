export default interface BFFClientExceptionHandler {
  handle(error: Error): void;
}
