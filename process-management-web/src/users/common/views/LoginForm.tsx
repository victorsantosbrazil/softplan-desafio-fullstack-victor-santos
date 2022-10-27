import { useInjection } from "inversify-react";
import { useCallback, useState } from "react";
import { Button, Container, Form } from "react-bootstrap";
import { useNavigate } from "react-router-dom";
import { toast } from "react-toastify";
import styled from "styled-components";
import { usercases } from "../../../diSymbols";
import LoginUserCase from "../usercases/LoginUserCase";

const Wrapper = styled.main`
  display: flex;
  align-items: center;
  background: #f1f1f1;
  height: 100vh;

  & #login-form-container {
    padding: 20px;
    background: white;
    width: 300px;
  }
`;

const LoginForm = () => {
  const navigate = useNavigate();

  const loginUserCase = useInjection<LoginUserCase>(usercases.LOGIN);

  const [formData, setFormData] = useState({ email: "", password: "" });

  const handleChange = useCallback(
    async (key: string, evt: { target: { value: any } }) => {
      setFormData({ ...formData, [key]: evt.target.value });
    },
    [formData]
  );

  const handleSubmit = useCallback(async () => {
    try {
      await loginUserCase.run({ ...formData });
      navigate("/");
    } catch (e) {
      const error = e as Error;
      toast(error.message, {
        className: "bg-danger text-white",
        theme: "colored",
      });
    }
  }, [loginUserCase, formData, navigate]);

  return (
    <Wrapper>
      <Container id="login-form-container">
        <Form className="text-start" data-testid="form">
          <Form.Group className="mb-2" data-testid="email-form-group">
            <Form.Label
              htmlFor="email-form-control"
              data-testid="email-form-label"
            >
              Email
            </Form.Label>
            <Form.Control
              id="email-form-control"
              value={formData.email}
              onChange={handleChange.bind(this, "email")}
              required
              aria-required
              data-testid="email-form-control"
            ></Form.Control>
          </Form.Group>
          <Form.Group data-testid="password-form-group">
            <Form.Label
              htmlFor="password-form-control"
              required
              data-testid="password-form-label"
            >
              Password
            </Form.Label>
            <Form.Control
              id="password-form-control"
              value={formData.password}
              onChange={handleChange.bind(this, "password")}
              required
              aria-required
              data-testid="password-form-control"
            ></Form.Control>
          </Form.Group>
          <Button
            className="w-100 my-4"
            onClick={handleSubmit}
            data-testid="submit-button"
          >
            Log in
          </Button>
        </Form>
      </Container>
    </Wrapper>
  );
};

export default LoginForm;
