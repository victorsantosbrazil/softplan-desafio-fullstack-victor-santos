import { useCallback, useState } from "react";
import { useInjection } from "inversify-react";
import { toast } from "react-toastify";
import { Container, Form, Row, Col, Button } from "react-bootstrap";
import { usercases } from "../../../diSymbols";
import { UserRole } from "../../../security/UserRole";
import CreateUserUserCase from "../usercases/create-user/CreateUserUserCase";
import { CreateUserUserCaseRequest } from "../usercases/create-user/models";
import { ValidationException } from "../../../exceptions/ValidationException";
import { useNavigate } from "react-router-dom";

const UsersForm = () => {
  const navigate = useNavigate();

  const createUserUserCase = useInjection<CreateUserUserCase>(
    usercases.CREATE_USERS
  );

  const [formData, setFormData] = useState({
    name: "",
    email: "",
    password: "",
    role: UserRole.ADMIN,
  });

  const [formErrors, setFormErrors] = useState(new Map<string, string[]>());

  const handleChange = useCallback(
    async (key: string, evt: { target: { value: any } }) => {
      setFormData({ ...formData, [key]: evt.target.value });
    },
    [formData]
  );

  const toastSuccess = useCallback((msg: string) => {
    toast(msg, {
      style: { background: "#07bc0c", color: "white" },
      theme: "colored",
    });
  }, []);

  const handleSubmit = useCallback(async () => {
    try {
      const createUserRequest = new CreateUserUserCaseRequest({ ...formData });
      await createUserUserCase.run(createUserRequest);
      navigate("/users");
      toastSuccess("User registered successfully");
    } catch (error) {
      if (error instanceof ValidationException) {
        setFormErrors(error.violations);
      }
    }
  }, [createUserUserCase, formData, toastSuccess, navigate]);

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

  return (
    <Container className="my-2" as="section">
      <header
        className="d-flex justify-content-start mb-3"
        data-testid="header"
      >
        <h1 className="fs-2">New User</h1>
      </header>
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
                data-testid="form-control-role-option-admin"
              >
                ADMIN
              </option>
              <option
                value={UserRole.PROCESS_SCREENER}
                data-testid="form-control-role-option-process-screener"
              >
                PROCESS SCREENER
              </option>
              <option
                value={UserRole.PROCESS_FINISHER}
                data-testid="form-control-role-option-process-finisher"
              >
                PROCESS FINISHER
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
