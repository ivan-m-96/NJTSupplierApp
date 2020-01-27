import React from "react";
// eslint-disable-next-line
import { ReactDOM, render } from "react-dom";
import "./App.css";
import "react-widgets/dist/css/react-widgets.css";
// eslint-disable-next-line
import {
  BrowserRouter as Router,
  Route,
  Link,
  Redirect
} from "react-router-dom";
import {
  removeDobavljac,
  updateDobavljac,
  getPorudzbeniceZaDobavljaca,
  postDobavljac,
  login,
  updateHeader
} from "./service/api";
import Header from "./header";
import Forma from "./form";
import Tabela from "./tableDobavljaci";
import Login from "./Login";
import ObradaPorudzbenice from "./obradaPorudzbenice";
import Cookies from "universal-cookie";
const cookies = new Cookies();
class App extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      selectedRow: null,
      authed: false,
      naziv: "",
      adresa: "",
      selectedDobavljac: 0,
      selectedPorudzbenica: null,
      redniBrojPorudzbenice: 0
    };
    this.onRemove = this.onRemove.bind(this);
    this.setSelectedValues = this.setSelectedValues.bind(this);
    this.handleTextChange = this.handleTextChange.bind(this);
    this.onUpdate = this.onUpdate.bind(this);
    this.onInsert = this.onInsert.bind(this);
    this.setSelectedDobavljac = this.setSelectedDobavljac.bind(this);
    this.getRedniBrojPorudzbenice = this.getRedniBrojPorudzbenice.bind(this);
    this.setSelectedPorudzbenica = this.setSelectedPorudzbenica.bind(this);
    this.refresh = this.refresh.bind(this);
    this.appLogin = this.appLogin.bind(this);
    this.logout = this.logout.bind(this);
  }
  async onRemove() {
    try {
      console.log("proba");
      const id = this.state.selectedRow;

      let result = await removeDobavljac(id);
      console.log("poslato");
      this.setState({ selectedRow: null, naziv: "", adresa: "" });
      console.log(result);
    } catch (error) {
      console.log(error.message);
    }
  }

  async onUpdate() {
    try {
      await updateDobavljac(
        this.state.selectedRow,
        this.state.naziv,
        this.state.adresa
      ).then(r => {
        console.log(r);
      });
    } catch (error) {}
  }

  async onInsert() {
    try {
      await postDobavljac({
        naziv: this.state.naziv,
        adresa: this.state.adresa
      }).then(result => console.log(result));
    } catch (error) {
      console.log(error);
    }
  }

  setSelectedValues(naziv, adresa) {
    if (this.state.selectedRow) {
      this.setState({ naziv: naziv, adresa: adresa });
    } else {
      this.setState({ naziv: "", adresa: "" });
    }
  }

  handleTextChange(e) {
    this.setState({ [e.target.name]: e.target.value });
  }

  async getRedniBrojPorudzbenice() {
    if (!this.state.selectedPorudzbenica && this.state.selectedDobavljac) {
      let porudzbenice = [];
      await getPorudzbeniceZaDobavljaca(this.state.selectedDobavljac.id).then(
        result => {
          console.log("resultat" + result);
          result.forEach(element => {
            porudzbenice.push(element);
          });
        }
      );
      let id = [...porudzbenice].pop();
      if (id) {
        console.log(
          "redni broj porudzbenice iz getRedniBrojPorudzbenice()" + id.id
        );
        this.setState({ redniBrojPorudzbenice: id.id + 1 });
      } else this.setState({ redniBrojPorudzbenice: 1 });
    } else {
      this.setState({
        redniBrojPorudzbenice: this.state.selectedPorudzbenica.id
      });
    }
  }

  setSelectedDobavljac(id) {
    this.setState({ selectedDobavljac: id });
    this.getRedniBrojPorudzbenice();
  }

  setSelectedPorudzbenica(porudzbenica) {
    if (porudzbenica == null) {
      this.setState({ selectedPorudzbenica: null });
    } else {
      this.setState({ selectedPorudzbenica: porudzbenica });
      this.getRedniBrojPorudzbenice();
    }
  }
  setSelectedRow = id => this.setState({ selectedRow: id });
  refresh() {
    this.setState({ ...this.state });
  }

  async appLogin(username, pass) {
    let res = null;

    await login(username, pass).then(response => (res = response));
    console.log("from app login");
    console.log(res);
    if (res) {
      cookies.set("Authorization", res.headers.authorization, { path: "/" });
      this.setState({ authed: true });
      updateHeader();
    }
  }

  componentWillMount() {
    if (cookies.get("Authorization")) {
      console.log(cookies.get("Authorization"));
      this.setState({ authed: true });
    } else {
      this.setState({ authed: false });
    }
  }

  logout() {
    cookies.remove("Authorization");
    this.setState({ authed: false });
  }

  render() {
    return (
      <Router>
        <link
          rel="stylesheet"
          href="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
          integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T"
          crossOrigin="anonymous"
        />
        <div className="App">
          <Header logout={this.logout} />

          <div>
            <hr />
            <div>
              {/* <Route path="/form" render={props => <Forma {...props} />} /> */}
              <Route
                path="/login"
                render={props => (
                  <Login
                    {...props}
                    authed={this.state.authed}
                    login={this.appLogin}
                  />
                )}
              ></Route>

              <PrivateRoute
                path="/dobavljaci"
                component={Tabela}
                {...this.props}
                selectedRow={this.state.selectedRow}
                setSelectedRow={this.setSelectedRow}
                onRemove={this.onRemove}
                onUpdate={this.onUpdate}
                onInsert={this.onInsert}
                lastSelectedRow={this.state.lastSelectedRow}
                setSelectedValues={this.setSelectedValues}
                naziv={this.state.naziv}
                adresa={this.state.adresa}
                handleTextChange={this.handleTextChange}
                refresh={this.refresh}
                authed={this.state.authed}
              />
              <PrivateRoute
                path="/porudzbenica"
                component={ObradaPorudzbenice}
                {...this.props}
                getRedniBrojPorudzbenice={this.getRedniBrojPorudzbenice}
                setSelectedDobavljac={this.setSelectedDobavljac}
                redniBrojPorudzbenice={this.state.redniBrojPorudzbenice}
                setSelectedPorudzbenica={this.setSelectedPorudzbenica}
                authed={this.state.authed}
              ></PrivateRoute>
            </div>
          </div>
        </div>
      </Router>
    );
  }
}
function PrivateRoute({ component: Component, authed, ...rest }) {
  return (
    <Route
      {...rest}
      render={props =>
        authed === true ? (
          <Component {...props} {...rest} />
        ) : (
          <Redirect
            to={{ pathname: "/login", state: { from: props.location } }}
          />
        )
      }
    />
  );
}
export default App;
