import { faUser } from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { Nav } from "react-bootstrap";
import styled from "styled-components";

const Wrapper = styled(Nav)`
  display: flex;
  flex-direction: column;
  text-align: start;

  .nav-item {
    width: 100%;
  }

  .nav-link.active {
    background: #4d4d4d;
    color: #f1f1f1;
    font-weight: 500;
  }
`;

const LoggedAreaSidebar = () => {
  return (
    <Wrapper className="py-2 bg-light">
      <Nav.Item>
        <Nav.Link eventKey="1" href="/users" active>
          <FontAwesomeIcon icon={faUser} className="me-2" />
          Users
        </Nav.Link>
      </Nav.Item>
    </Wrapper>
  );
};

export default LoggedAreaSidebar;
