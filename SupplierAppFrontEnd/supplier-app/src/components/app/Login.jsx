import React, { Component } from "react";
import { Form, Button } from "react-bootstrap";

export default class Login extends Component {
  constructor(props) {
    super(props);
    this.state = {
      username: "",
      password: ""
    };
    this.onChange = this.onChange.bind(this);
    this.onLogin = this.onLogin.bind(this);
  }

  componentWillMount() {
    if (this.props.authed) {
      this.props.history.push("/");
    }
  }

  onLogin() {
    this.props.login(this.state.username, this.state.password);
    this.props.history.push("/");
  }
  onChange(e) {
    this.setState({ [e.target.name]: e.target.value });
  }

  render() {
    return (
      <div>
        <Form>
          <Form.Group controlId="formBasicEmail">
            <Form.Label>Username</Form.Label>
            <Form.Control
              type="username"
              name="username"
              placeholder="Enter username"
              value={this.state.username}
              onChange={this.onChange}
            />
          </Form.Group>

          <Form.Group controlId="formBasicPassword">
            <Form.Label>Password</Form.Label>
            <Form.Control
              type="password"
              name="password"
              onChange={this.onChange}
              value={this.state.password}
              placeholder="Password"
            />
          </Form.Group>

          <Button variant="primary" onClick={this.onLogin}>
            Submit
          </Button>
        </Form>
      </div>
    );
  }
}
