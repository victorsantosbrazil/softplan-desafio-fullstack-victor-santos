import BFFClient from "../../../../../clients/BFFClient";
import { PageParams } from "../../../../../common/pagination";
import GetUsersUserCaseImpl from "../GetUsersUserCaseImpl";

describe("GetUsersUserCase tests", () => {
  const bffClient: Partial<jest.Mocked<BFFClient>> = {
    get: jest.fn(),
  };

  const userCase = new GetUsersUserCaseImpl(bffClient as any);

  it("should return page of users", async () => {
    const pageParams: PageParams = {
      page: 1,
      size: 5,
    };

    const mockHttpResponse = {
      data: {
        content: [
          { id: "fadlkfdjafda", name: "Antonio", role: "ADMIN" },
          { id: "fadlkfdjafda", name: "Fernando", role: "PROCESS_SCREENER" },
          { id: "fadlkfdjafda", name: "Cristiano", role: "PROCESS_FINISHER" },
        ],
        pageable: {
          pageNumber: pageParams.page,
        },
        first: false,
        last: true,
      },
    };

    bffClient.get?.mockResolvedValue(mockHttpResponse);

    const response = await userCase.run(pageParams);

    expect(bffClient.get).toBeCalledWith("/users", {
      params: { ...pageParams },
    });
    expect(mockHttpResponse.data.content).toEqual(response.content);
    expect(mockHttpResponse.data.first).toEqual(response.pageable.isFirst);
    expect(mockHttpResponse.data.last).toEqual(response.pageable.isLast);
  });
});
