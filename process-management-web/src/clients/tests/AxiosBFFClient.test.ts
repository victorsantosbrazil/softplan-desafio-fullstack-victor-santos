import axios from "axios";
import AxiosBFFClient from "../AxiosBFFClient";

const REACT_APP_BFF_URL = "http://134.33.43.23:8080/api";
process.env.REACT_APP_BFF_URL = REACT_APP_BFF_URL;

const mockAxiosInstance = {
  get: jest.fn(),
};

jest.mock("axios", () => {
  return { create: jest.fn(() => mockAxiosInstance) };
});

describe("BFFClient tests", () => {
  const bffClient = new AxiosBFFClient();

  it("should create axios instance with baseUrl as BFF url", () => {
    new AxiosBFFClient();

    const mockAxios = jest.mocked(axios);

    expect(mockAxios.create).toHaveBeenCalledWith(
      expect.objectContaining({ baseURL: REACT_APP_BFF_URL })
    );
  });

  it("should get", async () => {
    const url = "/users";

    const mockResponse = {
      msg: "Hello",
    };

    mockAxiosInstance.get.mockResolvedValue(mockResponse);

    const params = {
      msg: "Hi",
    };

    const response = await bffClient.get(url, { params });

    expect(mockAxiosInstance.get).toBeCalledWith(url, { params });
    expect(mockResponse).toBe(response);
  });
});
