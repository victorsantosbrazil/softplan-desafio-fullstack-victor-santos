import GetUserUserCaseImpl from "../GetUserUserCaseImpl";
import GetUserUserCaseResponse from "../GetUserUserCaseResponse";

describe("GetUserUserCaseImpl tests", () => {
  const mockBffClient = { get: jest.fn() };

  const usercase = new GetUserUserCaseImpl(mockBffClient as any);
  it("should get user", async () => {
    const id = "lfjdafa";

    const mockClientResponse = {
      data: {
        id,
        name: "Tomas Alquino",
        email: "tomas@test.com",
        role: "ADMIN",
      },
    };

    const expectedResponse = new GetUserUserCaseResponse(
      mockClientResponse.data
    );

    mockBffClient.get.mockResolvedValue(mockClientResponse);

    const response = await usercase.run(id);

    expect(mockBffClient.get).toBeCalledWith("/users/" + id);
    expect(expectedResponse).toEqual(response);
  });
});
