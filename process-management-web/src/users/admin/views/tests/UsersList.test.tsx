import { fireEvent, render, screen } from "@testing-library/react";
import { act } from "react-dom/test-utils";
import { setTimeout } from "timers/promises";
import { Page } from "../../../../common/pagination";
import Pageable from "../../../../common/pagination/Pageable";
import { GetUsersUserCaseResponse } from "../../usercases/get-users/GetUsersUserCaseResponse";
import UsersList from "../UsersList";

const mockGetUsersUserCase = {
  run: jest.fn(),
};

const mockNavigate = jest.fn();

jest.mock("react-router-dom", () => {
  return {
    useNavigate: () => mockNavigate,
  };
});

jest.mock("inversify-react", () => {
  const useInjection = () => {
    return mockGetUsersUserCase;
  };

  return {
    __esModule: true,
    useInjection,
  };
});

describe("UsersList test", () => {
  it("should render title", async () => {
    mockGetUsersUserCase.run.mockResolvedValue(
      new Page<GetUsersUserCaseResponse>(
        [],
        new Pageable({ pageNumber: 0, isFirst: true, isLast: true })
      )
    );
    render(<UsersList />);

    const header = await screen.findByTestId("header");

    expect(header).toBeInTheDocument();
    expect(header).toHaveTextContent("Users");
  });

  it("should show spinner when is loading", () => {
    mockGetUsersUserCase.run.mockImplementation(async () => {
      await setTimeout(1000);
      return new Page([], {} as any);
    });

    render(<UsersList />);

    const spinner = screen.getByTestId("spinner");
    expect(spinner).toBeInTheDocument();
  });

  it("should list users", async () => {
    const mockUsersData = [
      new GetUsersUserCaseResponse({
        id: "1",
        name: "Victor Nunes",
        role: "ADMIN",
      }),
      new GetUsersUserCaseResponse({
        id: "2",
        name: "John Lucas",
        role: "PROCESS_SCREENER",
      }),
      new GetUsersUserCaseResponse({
        id: "3",
        name: "Matheus Silva",
        role: "PROCESS_FINISHER",
      }),
    ];

    const mockPageable = new Pageable({
      pageNumber: 0,
      isFirst: true,
      isLast: false,
    });

    const mockPageResult = new Page<GetUsersUserCaseResponse>(
      mockUsersData,
      mockPageable
    );

    mockGetUsersUserCase.run.mockResolvedValue(mockPageResult);

    render(<UsersList />);

    const table = await screen.findByTestId("users-table");
    const tableHeader = await screen.findByTestId("users-table-header");
    const tableHeaderName = await screen.findByTestId(
      "users-table-header-name"
    );
    const tableHeaderRole = await screen.findByTestId(
      "users-table-header-role"
    );
    const tableHeaderActions = await screen.findByTestId(
      "users-table-header-actions"
    );
    const tableBody = await screen.findByTestId("users-table-body");

    const tableRowUser1 = await screen.findByTestId("users-table-row-user-1");
    const tableRowUser1Name = await screen.findByTestId(
      "users-table-row-user-1-name"
    );
    const tableRowUser1Role = await screen.findByTestId(
      "users-table-row-user-1-role"
    );
    const tableRowUser1EditAction = await screen.findByTestId(
      "users-table-row-user-1-edit-action"
    );

    const tableRowUser2 = await screen.findByTestId("users-table-row-user-2");
    const tableRowUser2Name = await screen.findByTestId(
      "users-table-row-user-2-name"
    );
    const tableRowUser2Role = await screen.findByTestId(
      "users-table-row-user-2-role"
    );
    const tableRowUser2EditAction = await screen.findByTestId(
      "users-table-row-user-2-edit-action"
    );

    const tableRowUser3 = await screen.findByTestId("users-table-row-user-3");
    const tableRowUser3Name = await screen.findByTestId(
      "users-table-row-user-3-name"
    );
    const tableRowUser3Role = await screen.findByTestId(
      "users-table-row-user-3-role"
    );
    const tableRowUser3EditAction = await screen.findByTestId(
      "users-table-row-user-3-edit-action"
    );

    expect(table).toBeInTheDocument();
    expect(table).toContainElement(tableHeader);
    expect(table).toContainElement(tableBody);
    expect(tableHeader).toContainElement(tableHeaderName);
    expect(tableHeader).toContainElement(tableHeaderRole);
    expect(tableHeader).toContainElement(tableHeaderActions);
    expect(tableBody).toContainElement(tableRowUser1);
    expect(tableBody).toContainElement(tableRowUser2);
    expect(tableBody).toContainElement(tableRowUser3);
    expect(tableRowUser1).toContainElement(tableRowUser1Name);
    expect(tableRowUser1).toContainElement(tableRowUser1Role);
    expect(tableRowUser1).toContainElement(tableRowUser1EditAction);
    expect(tableRowUser2).toContainElement(tableRowUser2Name);
    expect(tableRowUser2).toContainElement(tableRowUser2Role);
    expect(tableRowUser2).toContainElement(tableRowUser2EditAction);
    expect(tableRowUser3).toContainElement(tableRowUser3Name);
    expect(tableRowUser3).toContainElement(tableRowUser3Role);
    expect(tableRowUser3).toContainElement(tableRowUser3EditAction);
  });

  it("should renders pagination correctly", async () => {
    const mockPageable = new Pageable({
      pageNumber: 1,
      isFirst: false,
      isLast: false,
    });

    const mockPageResult = new Page<GetUsersUserCaseResponse>([], mockPageable);
    mockGetUsersUserCase.run.mockResolvedValue(mockPageResult);

    render(<UsersList />);

    const pagination = await screen.findByTestId("users-table-pagination");
    const paginationItemPrevious = await screen.findByTestId(
      "users-table-pagination-item-previous"
    );
    const paginationItemCurrent = await screen.findByTestId(
      "users-table-pagination-item-current"
    );
    const paginationItemNext = await screen.findByTestId(
      "users-table-pagination-item-next"
    );

    expect(pagination).toBeInTheDocument();
    expect(pagination).toContainElement(paginationItemPrevious);
    expect(paginationItemPrevious).toHaveTextContent("1");
    expect(pagination).toContainElement(paginationItemCurrent);
    expect(paginationItemCurrent).toHaveTextContent("2");
    expect(pagination).toContainElement(paginationItemNext);
    expect(paginationItemNext).toHaveTextContent("3");
  });

  it("when first page should not render previos pagination item", async () => {
    const mockPageable = new Pageable({
      pageNumber: 1,
      isFirst: true,
      isLast: false,
    });

    const mockPageResult = new Page<GetUsersUserCaseResponse>([], mockPageable);
    mockGetUsersUserCase.run.mockResolvedValue(mockPageResult);

    render(<UsersList />);

    const pagination = await screen.findByTestId("users-table-pagination");
    const paginationItemPrevious = screen.queryByTestId(
      "users-table-pagination-item-previous"
    );
    const paginationItemCurrent = await screen.findByTestId(
      "users-table-pagination-item-current"
    );
    const paginationItemNext = await screen.findByTestId(
      "users-table-pagination-item-next"
    );

    expect(pagination).toBeInTheDocument();
    expect(pagination).not.toContainElement(paginationItemPrevious);
    expect(pagination).toContainElement(paginationItemCurrent);
    expect(paginationItemCurrent).toHaveTextContent("2");
    expect(pagination).toContainElement(paginationItemNext);
    expect(paginationItemNext).toHaveTextContent("3");
  });

  it("when last page should not render next pagination item", async () => {
    const mockPageable = new Pageable({
      pageNumber: 1,
      isFirst: false,
      isLast: true,
    });

    const mockPageResult = new Page<GetUsersUserCaseResponse>([], mockPageable);
    mockGetUsersUserCase.run.mockResolvedValue(mockPageResult);

    render(<UsersList />);

    const pagination = await screen.findByTestId("users-table-pagination");

    const paginationItemPrevious = await screen.findByTestId(
      "users-table-pagination-item-previous"
    );
    const paginationItemCurrent = await screen.findByTestId(
      "users-table-pagination-item-current"
    );
    const paginationItemNext = screen.queryByTestId(
      "users-table-pagination-item-next"
    );

    expect(pagination).toBeInTheDocument();
    expect(pagination).toContainElement(paginationItemPrevious);
    expect(paginationItemPrevious).toHaveTextContent("1");
    expect(pagination).toContainElement(paginationItemCurrent);
    expect(paginationItemCurrent).toHaveTextContent("2");
    expect(pagination).not.toContainElement(paginationItemNext);
  });

  it("should change table's content when click on previous pagination item", async () => {
    const mockInitialUsersData = [
      new GetUsersUserCaseResponse({
        id: "1",
        name: "Victor Nunes",
        role: "ADMIN",
      }),
    ];

    const mockInitialPagePageable = new Pageable({
      pageNumber: 1,
      isFirst: false,
      isLast: false,
    });

    const mockNewPageUsersData = [
      new GetUsersUserCaseResponse({
        id: "2",
        name: "Jefter Oliveira",
        role: "PROCESS_SCREENER",
      }),
    ];

    const mockNewPagePageable = new Pageable({
      pageNumber: 0,
      isFirst: false,
      isLast: false,
    });

    const mockInitialPageResult = new Page(
      mockInitialUsersData,
      mockInitialPagePageable
    );
    const mockNewPageResult = new Page(
      mockNewPageUsersData,
      mockNewPagePageable
    );

    mockGetUsersUserCase.run.mockResolvedValueOnce(mockInitialPageResult);
    mockGetUsersUserCase.run.mockResolvedValueOnce(mockNewPageResult);

    render(<UsersList />);

    const paginationItemPrevious = await screen.findByTestId(
      "users-table-pagination-item-previous"
    );

    let tableRowUser1: HTMLElement | null = await screen.findByTestId(
      "users-table-row-user-1"
    );
    let tableRowUser2: HTMLElement | null = screen.queryByTestId(
      "users-table-row-user-2"
    );

    expect(tableRowUser1).toHaveTextContent(mockInitialUsersData[0].name);
    expect(tableRowUser1).toHaveTextContent(mockInitialUsersData[0].role);
    expect(tableRowUser2).not.toBeInTheDocument();

    act(() => {
      paginationItemPrevious.click();
    });

    expect(mockGetUsersUserCase.run).toBeCalledWith(
      expect.objectContaining({
        page: mockInitialPagePageable.pageNumber - 1,
      })
    );

    tableRowUser2 = await screen.findByTestId("users-table-row-user-2");
    tableRowUser1 = screen.queryByTestId("users-table-row-user-1");

    expect(tableRowUser1).not.toBeInTheDocument();
    expect(tableRowUser2).toHaveTextContent(mockNewPageUsersData[0].name);
    expect(tableRowUser2).toHaveTextContent(mockNewPageUsersData[0].role);
  });

  it("should change table's content when click on next pagination item", async () => {
    const mockInitialUsersData = [
      new GetUsersUserCaseResponse({
        id: "1",
        name: "Victor Nunes",
        role: "ADMIN",
      }),
    ];

    const mockInitialPagePageable = new Pageable({
      pageNumber: 0,
      isFirst: false,
      isLast: false,
    });

    const mockNewPageUsersData = [
      new GetUsersUserCaseResponse({
        id: "2",
        name: "Jefter Oliveira",
        role: "PROCESS_SCREENER",
      }),
    ];

    const mockNewPagePageable = new Pageable({
      pageNumber: 1,
      isFirst: false,
      isLast: false,
    });

    const mockInitialPageResult = new Page(
      mockInitialUsersData,
      mockInitialPagePageable
    );
    const mockNewPageResult = new Page(
      mockNewPageUsersData,
      mockNewPagePageable
    );

    mockGetUsersUserCase.run.mockResolvedValueOnce(mockInitialPageResult);
    mockGetUsersUserCase.run.mockResolvedValueOnce(mockNewPageResult);

    render(<UsersList />);

    const paginationItemNext = await screen.findByTestId(
      "users-table-pagination-item-next"
    );

    let tableRowUser1: HTMLElement | null = await screen.findByTestId(
      "users-table-row-user-1"
    );
    let tableRowUser2: HTMLElement | null = screen.queryByTestId(
      "users-table-row-user-2"
    );

    expect(tableRowUser1).toHaveTextContent(mockInitialUsersData[0].name);
    expect(tableRowUser1).toHaveTextContent(mockInitialUsersData[0].role);
    expect(tableRowUser2).not.toBeInTheDocument();

    act(() => {
      paginationItemNext.click();
    });

    expect(mockGetUsersUserCase.run).toBeCalledWith(
      expect.objectContaining({
        page: mockInitialPagePageable.pageNumber + 1,
      })
    );

    tableRowUser2 = await screen.findByTestId("users-table-row-user-2");
    tableRowUser1 = screen.queryByTestId("users-table-row-user-1");

    expect(tableRowUser1).not.toBeInTheDocument();
    expect(tableRowUser2).toHaveTextContent(mockNewPageUsersData[0].name);
    expect(tableRowUser2).toHaveTextContent(mockNewPageUsersData[0].role);
  });
});

it("should navigate to new user form when click on add button", async () => {
  mockGetUsersUserCase.run.mockResolvedValue(
    new Page<GetUsersUserCaseResponse>(
      [],
      new Pageable({ pageNumber: 0, isFirst: true, isLast: true })
    )
  );
  render(<UsersList />);

  const addButton = await screen.findByTestId("add-button");

  fireEvent.click(addButton);

  expect(mockNavigate).toBeCalledWith("/users/new");
});

it("should navigate to edit user form when click on a edit button", async () => {
  const editedUserId = "2";

  mockGetUsersUserCase.run.mockResolvedValue(
    new Page<GetUsersUserCaseResponse>(
      [
        new GetUsersUserCaseResponse({
          id: editedUserId,
          name: "Jefter Oliveira",
          role: "PROCESS_SCREENER",
        }),
      ],
      new Pageable({ pageNumber: 0, isFirst: true, isLast: true })
    )
  );
  render(<UsersList />);

  const editButton = await screen.findByTestId(
    `users-table-row-user-${editedUserId}-edit-action`
  );

  fireEvent.click(editButton);

  expect(mockNavigate).toBeCalledWith(`/users/${editedUserId}`);
});
