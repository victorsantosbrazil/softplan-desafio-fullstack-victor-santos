export type HttpRequestConfig = {
  params: any;
};

export default interface BFFClient {
  get(url: string, config?: HttpRequestConfig): Promise<any>;
  post(url: string, body: any, config?: HttpRequestConfig): Promise<any>;
}
