import { useCallback, useEffect, useState } from "react";
import {
  Container,
  Row,
  Col,
  Table,
  Pagination,
  Spinner,
  Button,
  ButtonGroup,
} from "react-bootstrap";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faPen, faTrash } from "@fortawesome/free-solid-svg-icons";
import { useInjection } from "inversify-react";
import { usercases } from "../../../diSymbols";
import GetUsersUserCase from "../usercases/get-users/GetUsersUserCase";
import { GetUsersUserCaseResponse } from "../usercases/get-users/GetUsersUserCaseResponse";
import Pageable from "../../../common/pagination/Pageable";
import { useNavigate } from "react-router-dom";
import DeleteUserUserCase from "../usercases/delete-user/DeleteUserUserCase";

const UsersList = () => {
  const navigate = useNavigate();

  const getUsersUserCase = useInjection<GetUsersUserCase>(usercases.GET_USERS);
  const deleteUserUserCase = useInjection<DeleteUserUserCase>(
    usercases.DELETE_USER
  );

  const [usersData, setUsersData] = useState<GetUsersUserCaseResponse[]>([]);
  const [pageable, setPageable] = useState<Pageable>(
    new Pageable({ pageNumber: 0, isFirst: true, isLast: true })
  );
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

  const handleAdd = useCallback(() => {
    navigate("/users/new");
  }, [navigate]);

  const handleEdit = useCallback(
    (id: string) => {
      navigate(`/users/${id}`);
    },
    [navigate]
  );

  const handleDelete = useCallback(
    async (id: string) => {
      await deleteUserUserCase.run(id);
      await fetchData({ pageNumber: pageable.pageNumber });
    },
    [deleteUserUserCase, fetchData, pageable]
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
    <Container as="section">
      <Row className="my-2">
        <Col
          as="header"
          xs={8}
          className="d-flex justify-content-start"
          data-testid="header"
        >
          <h1 className="fs-2">Users</h1>
        </Col>
        <Col xs={4} className="d-flex justify-content-end">
          <Button
            variant="success"
            onClick={handleAdd}
            data-testid="add-button"
          >
            Add
          </Button>
        </Col>
      </Row>

      <Table data-testid="users-table" striped>
        <thead data-testid="users-table-header">
          <tr>
            <th className="col-6" data-testid="users-table-header-name">
              Name
            </th>
            <th className="col-4" data-testid="users-table-header-role">
              Role
            </th>
            <th className="col-2" data-testid="users-table-header-role"></th>
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
                <td className="d-flex justify-content-end">
                  <ButtonGroup className=" border">
                    <Button
                      onClick={() => handleEdit(userData.id)}
                      data-testid={`users-table-row-user-${userData.id}-edit-action`}
                    >
                      <FontAwesomeIcon icon={faPen} />
                    </Button>
                    <Button
                      variant="danger"
                      onClick={() => handleDelete(userData.id)}
                      data-testid={`users-table-row-user-${userData.id}-delete-action`}
                    >
                      <FontAwesomeIcon icon={faTrash} />
                    </Button>
                  </ButtonGroup>
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
