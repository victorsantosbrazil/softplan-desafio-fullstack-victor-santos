import BFFClient, { HttpRequestConfig } from "./BFFClient";
import axios, { AxiosInstance } from "axios";

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
}
