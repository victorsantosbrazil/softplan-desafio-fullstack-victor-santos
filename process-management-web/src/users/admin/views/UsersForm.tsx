import { useInjection } from "inversify-react";
import { useState } from "react";
import { Container, Form, Row, Col, Button } from "react-bootstrap";
import { usercases } from "../../../diSymbols";
import { UserRole } from "../../../security/UserRole";
import CreateUserUserCase from "../usercases/create-user/CreateUserUserCase";
import { CreateUserUserCaseRequest } from "../usercases/create-user/models";

const UsersForm = () => {
  const createUserUserCase = useInjection<CreateUserUserCase>(
    usercases.CREATE_USERS
  );

  const [formData, setFormData] = useState({
    name: "",
    email: "",
    password: "",
    role: UserRole.ADMIN,
  });

  const handleChange = async (key: string, evt: { target: { value: any } }) => {
    setFormData({ ...formData, [key]: evt.target.value });
  };

  const handleSubmit = async () => {
    const createUserRequest = new CreateUserUserCaseRequest({ ...formData });
    await createUserUserCase.run(createUserRequest);
  };

  return (
    <Container as="section">
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
              aria-required
            />
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
              data-testid="form-control-email"
              aria-required
            />
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
              data-testid="form-control-password"
              aria-required
            />
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
          </Col>
        </Row>
      </Form>
    </Container>
  );
};

export default UsersForm;
