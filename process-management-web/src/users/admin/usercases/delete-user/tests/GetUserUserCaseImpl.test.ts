import GetUserUserCaseImpl from "../DeleteUserUserCaseImpl";

describe("DeleteUserUserCaseImpl tests", () => {
  const mockBffClient = { delete: jest.fn() };

  const usercase = new GetUserUserCaseImpl(mockBffClient as any);
  it("should delete user", async () => {
    const id = "lfjdafa";

    await usercase.run(id);

    expect(mockBffClient.delete).toBeCalledWith("/users/" + id);
  });
});
