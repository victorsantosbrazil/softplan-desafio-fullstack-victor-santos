import styled from "styled-components";
import LoggedAreaSidebar from "./LoggedAreaSidebar";

const Wrapper = styled.div`
  display: grid;
  width: 100%;
  height: 100vh;

  grid-template-columns: 250px 1fr;

  grid-template-areas: "nav main";

  & #nav {
    background-color: lightblue;
    grid-area: nav;
  }

  & #main {
    grid-area: main;
  }
`;

const LoggedArea = (props: { children: JSX.Element }) => {
  return (
    <Wrapper>
      <LoggedAreaSidebar />
      <div id="main">{props.children}</div>
    </Wrapper>
  );
};

export default LoggedArea;
