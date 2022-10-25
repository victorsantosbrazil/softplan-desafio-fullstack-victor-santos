import BFFClient, { HttpRequestConfig } from "./BFFClient";
import axios, { AxiosInstance } from "axios";
import { injectable } from "inversify";
import { inject } from "inversify/lib/annotation/inject";
import { clients } from "../diSymbols";
import BFFClientExceptionHandler from "./BFFClientExceptionHandler";

@injectable()
export default class AxiosBFFClient implements BFFClient {
  private httpClient: AxiosInstance;

  constructor(
    @inject(clients.BFF_CLIENT_EXCEPTION_HANDLER)
    private _exceptionHandler: BFFClientExceptionHandler
  ) {
    this.httpClient = axios.create({
      baseURL: process.env.REACT_APP_BFF_URL,
    });
  }

  get(url: string, config?: HttpRequestConfig): Promise<any> {
    return this.httpClient.get(url, { params: config?.params });
  }

  async post(
    url: string,
    body: any,
    config?: HttpRequestConfig | undefined
  ): Promise<any> {
    try {
      return await this.httpClient.post(url, body, config);
    } catch (err) {
      this._exceptionHandler.handle(err as Error);
    }
  }
}
