import axios from "axios";
import AxiosBFFClient from "../AxiosBFFClient";

const REACT_APP_BFF_URL = "http://134.33.43.23:8080/api";
process.env.REACT_APP_BFF_URL = REACT_APP_BFF_URL;

const mockAxiosInstance = {
  get: jest.fn(),
  post: jest.fn(),
};

jest.mock("axios", () => {
  return {
    create: jest.fn(() => mockAxiosInstance),
  };
});

describe("BFFClient tests", () => {
  const bffClientExceptionHandler = {
    handle: jest.fn(),
  };

  const bffClient = new AxiosBFFClient(bffClientExceptionHandler);

  it("should create axios instance with baseUrl as BFF url", () => {
    new AxiosBFFClient(bffClientExceptionHandler);

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

  it("should post", async () => {
    const url = "/users";

    const mockRequestBody = {
      msg: "Hi",
    };

    const mockResponse = {
      data: {
        msg: "Hello",
      },
    };

    mockAxiosInstance.post.mockResolvedValue(mockResponse);

    const params = {
      msg: "Hi",
    };

    const response = await bffClient.post(url, mockRequestBody, { params });

    expect(mockAxiosInstance.post).toBeCalledWith(url, mockRequestBody, {
      params,
    });

    expect(mockResponse).toBe(response);
  });

  it("when post error then handle error", async () => {
    const url = "/users";

    const error = new Error("Request failed with status code 400");

    mockAxiosInstance.post.mockRejectedValue(error);

    await bffClient.post(url, {});

    expect(bffClientExceptionHandler.handle).toBeCalledWith(error);
  });
});
