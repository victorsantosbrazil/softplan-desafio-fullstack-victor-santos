import { useCallback, useEffect, useState } from "react";
import {
  Container,
  Row,
  Col,
  Table,
  Pagination,
  Spinner,
} from "react-bootstrap";
import { useInjection } from "inversify-react";
import { usercases } from "../../../diSymbols";
import GetUsersUserCase from "../usercases/get-users/GetUsersUserCase";
import { GetUsersUserCaseResponse } from "../usercases/get-users/GetUsersUserCaseResponse";
import Pageable from "../../../common/pagination/Pageable";

const UsersList = () => {
  const getUsersUserCase = useInjection<GetUsersUserCase>(usercases.GET_USERS);

  const [usersData, setUsersData] = useState<GetUsersUserCaseResponse[]>([]);
  const [pageable, setPageable] = useState<Pageable>();
  const [isLoading, setIsLoading] = useState(true);

  const fetchData = useCallback(
    async (params: { pageNumber: number }) => {
      setIsLoading(true);
      const resp = await getUsersUserCase.run({ page: params.pageNumber });
      setUsersData(resp.content);
      setPageable(resp.pageable);
      setIsLoading(false);
    },
    [getUsersUserCase]
  );

  const changePage = useCallback(
    async (pageNumber: number) => {
      await fetchData({ pageNumber });
    },
    [fetchData]
  );

  useEffect(() => {
    fetchData({ pageNumber: 0 });
  }, [fetchData]);

  if (isLoading) {
    return (
      <Spinner animation="border" role="status" data-testid="spinner">
        <span className="visually-hidden">Loading...</span>
      </Spinner>
    );
  }

  return (
    <Container>
      <Row className="my-2">
        <Col
          md={8}
          className="d-flex justify-content-start"
          data-testid="header"
        >
          <h1 className="fs-2">Users</h1>
        </Col>
      </Row>

      <Table data-testid="users-table" striped>
        <thead data-testid="users-table-header">
          <tr>
            <th className="col-md-6" data-testid="users-table-header-name">
              Name
            </th>
            <th className="col-md-3" data-testid="users-table-header-role">
              Role
            </th>
            <th className="col-md-3" data-testid="users-table-header-actions">
              Actions
            </th>
          </tr>
        </thead>
        <tbody data-testid="users-table-body">
          {usersData.map((userData) => {
            return (
              <tr
                key={userData.id}
                data-testid={`users-table-row-user-${userData.id}`}
              >
                <td data-testid={`users-table-row-user-${userData.id}-name`}>
                  {userData.name}
                </td>
                <td data-testid={`users-table-row-user-${userData.id}-role`}>
                  {userData.role}
                </td>
              </tr>
            );
          })}
        </tbody>
      </Table>

      <Pagination
        data-testid="users-table-pagination"
        aria-label="Users Table Pagination"
      >
        {!pageable?.isFirst && (
          <Pagination.Item
            onClick={() => changePage(pageable!.pageNumber - 1)}
            data-testid="users-table-pagination-item-previous"
          >
            {pageable!.pageNumber}
          </Pagination.Item>
        )}
        <Pagination.Item
          data-testid="users-table-pagination-item-current"
          active
        >
          {pageable!.pageNumber + 1}
        </Pagination.Item>
        {!pageable?.isLast && (
          <Pagination.Item
            onClick={() => changePage(pageable!.pageNumber + 1)}
            data-testid="users-table-pagination-item-next"
          >
            {pageable!.pageNumber + 2}
          </Pagination.Item>
        )}
      </Pagination>
    </Container>
  );
};

export default UsersList;
