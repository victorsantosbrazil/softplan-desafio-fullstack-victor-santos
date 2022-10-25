import BFFClient from "../../../../../clients/BFFClient";
import { UserRole } from "../../../../../security/UserRole";
import UpdateUserUserCaseImpl from "../UpdateUserUserCaseImpl";
import UpdateUserUserCaseRequest from "../UpdateUserUserCaseRequest";

describe("CreateUserUserCaseImpl tests", () => {
  const bffClient: Partial<jest.Mocked<BFFClient>> = {
    patch: jest.fn(),
  };

  const userCase = new UpdateUserUserCaseImpl(bffClient as any);

  it("should update user", async () => {
    const id = "1";
    const request = new UpdateUserUserCaseRequest({
      id,
      name: "Juliano Peixeira",
      email: "juliano@test.com",
      role: UserRole.ADMIN,
    });

    await userCase.run(request);

    expect(bffClient.patch).toBeCalledWith("/users/" + id, { ...request });
  });
});
