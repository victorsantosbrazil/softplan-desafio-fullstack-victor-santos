import BFFClient from "../../../../../clients/BFFClient";
import { UserRole } from "../../../../../security/UserRole";
import CreateUserUserCaseImpl from "../CreateUserUserCaseImpl";
import { CreateUserUserCaseRequest } from "../models";

describe("CreateUserUserCaseImpl tests", () => {
  const bffClient: Partial<jest.Mocked<BFFClient>> = {
    post: jest.fn(),
  };

  const userCase = new CreateUserUserCaseImpl(bffClient as any);

  it("should create user", async () => {
    const request = new CreateUserUserCaseRequest({
      name: "Juliano Peixeira",
      email: "juliano@test.com",
      password: "1234",
      role: UserRole.ADMIN,
    });

    await userCase.run(request);

    expect(bffClient.post).toBeCalledWith("/users", { ...request });
  });
});
