import { useCallback, useEffect, useState } from "react";
import { useInjection } from "inversify-react";
import { toast } from "react-toastify";
import { Container, Form, Row, Col, Button } from "react-bootstrap";
import { usercases } from "../../../diSymbols";
import { UserRole } from "../../../security/UserRole";
import CreateUserUserCase from "../usercases/create-user/CreateUserUserCase";
import { CreateUserUserCaseRequest } from "../usercases/create-user/models";
import { ValidationException } from "../../../exceptions/ValidationException";
import { useNavigate, useParams } from "react-router-dom";
import GetUserUserCase from "../usercases/get-user/GetUserUserCase";

const UsersForm = () => {
  const navigate = useNavigate();
  const { id } = useParams();
  const isEditing = !!id;

  const createUserUserCase = useInjection<CreateUserUserCase>(
    usercases.CREATE_USER
  );

  const getUserUserCase = useInjection<GetUserUserCase>(usercases.GET_USER);

  const updateUserUserCase = useInjection(usercases.UPDATE_USER) as any;

  const [formData, setFormData] = useState({
    name: "",
    email: "",
    password: "",
    role: UserRole.ADMIN,
  });

  const [formErrors, setFormErrors] = useState(new Map<string, string[]>());

  const fetchUser = useCallback(
    async (id: string) => {
      const userData = (await getUserUserCase.run(id)) as any;
      setFormData({
        name: userData.name,
        email: userData.email,
        role: userData.role,
        password: "",
      });
    },
    [getUserUserCase]
  );

  const handleChange = useCallback(
    async (key: string, evt: { target: { value: any } }) => {
      setFormData({ ...formData, [key]: evt.target.value });
    },
    [formData]
  );

  const createUser = useCallback(() => {
    const createUserRequest = new CreateUserUserCaseRequest({ ...formData });
    return createUserUserCase.run(createUserRequest);
  }, [createUserUserCase, formData]);

  const updateUser = useCallback(() => {
    return updateUserUserCase.run({
      id,
      name: formData.name,
      email: formData.email,
      role: formData.role,
    });
  }, [updateUserUserCase, id, formData]);

  const toastSuccess = useCallback((msg: string) => {
    toast(msg, {
      style: { background: "#07bc0c", color: "white" },
      theme: "colored",
    });
  }, []);

  const handleSubmit = useCallback(async () => {
    try {
      if (isEditing) {
        await updateUser();
      } else {
        await createUser();
      }
      navigate("/users");
      toastSuccess("User saved successfully");
    } catch (error) {
      if (error instanceof ValidationException) {
        setFormErrors(error.violations);
      }
    }
  }, [toastSuccess, isEditing, navigate, createUser, updateUser]);

  const handleCancel = useCallback(() => {
    navigate("/users");
  }, [navigate]);

  const isInvalidInput = useCallback(
    (propertyPath: string): boolean => {
      return !!formErrors.get(propertyPath);
    },
    [formErrors]
  );

  const invalidMsgs = useCallback(
    (propertyPath: string) => {
      const errorMsgs = formErrors.get(propertyPath) || [];

      return errorMsgs.map((msg, idx) => {
        const testId = `form-control-error-${propertyPath.replace(
          ".",
          "-"
        )}-${idx}`;

        return (
          <Form.Text className="text-danger" key={idx} data-testid={testId}>
            {msg}
          </Form.Text>
        );
      });
    },
    [formErrors]
  );

  useEffect(() => {
    if (id) {
      fetchUser(id);
    }
  }, [id, fetchUser]);

  return (
    <Container className="p-4" as="section">
      <Form data-testid="form">
        <Row>
          <Col
            className="d-flex flex-column align-items-start mb-2"
            sm={12}
            data-testid="form-group-name"
          >
            <Form.Label htmlFor="name-input" data-testid="form-label-name">
              Name
            </Form.Label>
            <Form.Control
              id="name-input"
              data-testid="form-control-name"
              value={formData.name}
              onChange={handleChange.bind(this, "name")}
              isInvalid={isInvalidInput("name")}
              aria-required
              aria-invalid={isInvalidInput("name")}
            />
            {invalidMsgs("name")}
          </Col>
          <Col
            className="d-flex flex-column align-items-start mb-2"
            sm={12}
            data-testid="form-group-email"
          >
            <Form.Label htmlFor="email-input" data-testid="form-label-email">
              Email
            </Form.Label>
            <Form.Control
              id="email-input"
              type="email"
              value={formData.email}
              onChange={handleChange.bind(this, "email")}
              isInvalid={isInvalidInput("email")}
              data-testid="form-control-email"
              aria-required
              aria-invalid={isInvalidInput("email")}
            />
            {invalidMsgs("email")}
          </Col>
          {!isEditing && (
            <Col
              className="d-flex flex-column align-items-start mb-2"
              md={6}
              sm={12}
              data-testid="form-group-password"
            >
              <Form.Label
                htmlFor="password-input"
                data-testid="form-label-password"
              >
                Password
              </Form.Label>
              <Form.Control
                id="password-input"
                type="password"
                value={formData.password}
                onChange={handleChange.bind(this, "password")}
                isInvalid={isInvalidInput("password")}
                data-testid="form-control-password"
                aria-required
                aria-invalid={isInvalidInput("password")}
              />
              {invalidMsgs("password")}
            </Col>
          )}
          <Col
            className="d-flex flex-column align-items-start mb-2"
            md={6}
            sm={12}
            data-testid="form-group-role"
          >
            <Form.Label data-testid="form-label-role">Role</Form.Label>
            <Form.Select
              id="role-input"
              value={formData.role}
              onChange={handleChange.bind(this, "role")}
              data-testid="form-control-role"
              aria-required
            >
              <option
                value={UserRole.ADMIN}
                data-testid="form-control-role-options"
              >
                Admin
              </option>
              <option
                value={UserRole.PROCESS_SCREENER}
                data-testid="form-control-role-options"
              >
                Process Screener
              </option>
              <option
                value={UserRole.PROCESS_FINISHER}
                data-testid="form-control-role-options"
              >
                Process Finisher
              </option>
            </Form.Select>
          </Col>
        </Row>
        <Row className="mt-2">
          <Col className="d-flex align-items-start">
            <Button onClick={handleSubmit} data-testid="save-button">
              Save
            </Button>
            <Button
              className="ms-2"
              variant="secondary"
              onClick={handleCancel}
              data-testid="cancel-button"
            >
              Cancel
            </Button>
          </Col>
        </Row>
      </Form>
    </Container>
  );
};

export default UsersForm;
