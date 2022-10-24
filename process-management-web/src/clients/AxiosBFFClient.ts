import BFFClient, { HttpRequestConfig } from "./BFFClient";
import axios, { AxiosInstance } from "axios";
import { injectable } from "inversify";

@injectable()
export default class AxiosBFFClient implements BFFClient {
  private httpClient: AxiosInstance;

  constructor() {
    this.httpClient = axios.create({
      baseURL: process.env.REACT_APP_BFF_URL,
    });
  }

  get(url: string, config?: HttpRequestConfig): Promise<any> {
    return this.httpClient.get(url, { params: config?.params });
  }

  post(
    url: string,
    body: any,
    config?: HttpRequestConfig | undefined
  ): Promise<any> {
    return this.httpClient.post(url, body, config);
  }
}
