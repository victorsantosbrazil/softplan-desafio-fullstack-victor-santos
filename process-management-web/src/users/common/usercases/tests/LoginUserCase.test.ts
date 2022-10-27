import LoginUserCaseImpl from "../LoginUserCaseImpl";
import LoginUserCaseRequest from "../LoginUserCaseRequest";

describe("LoginUserCase tests", () => {
  const mockBffClient = {
    post: jest.fn(),
  };

  const userCase = new LoginUserCaseImpl(mockBffClient as any);

  it("should post to login route", async () => {
    const request = new LoginUserCaseRequest({
      email: "test@test.com",
      password: "1234",
    });

    const mockApiToken = "jvkanvadfçdasfd";
    const mockBffResponse = {
      data: {
        apiToken: mockApiToken,
      },
    };

    mockBffClient.post.mockResolvedValue(mockBffResponse);

    const response = await userCase.run(request);

    expect(mockBffClient.post).toBeCalledWith("/auth/basic", request);
    expect(response).toBe(mockApiToken);
  });
});
