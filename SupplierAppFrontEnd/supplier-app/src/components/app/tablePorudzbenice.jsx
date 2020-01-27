import React, { Component } from "react";
import Table from "react-bootstrap/Table";
import Button from "react-bootstrap/Button";
import ButtonGroup from "react-bootstrap/ButtonGroup";
import Form from "react-bootstrap/Form";
import Col from "react-bootstrap/Col";
import "./tablePorudzbenice.css";

export default class tablePorudzbenice extends Component {
  constructor(props) {
    super(props);
    this.state = { porudzbenice: [] };
  }
  setSelectedRow(id) {
    if (this.props.selectedRow === id) this.props.setSelectedRow(null);
    else this.props.setSelectedRow(id);
  }

  setPorudzbenica(porudzbenica) {
    this.props.setPorudzbenica(porudzbenica);
  }

  render() {
    return (
      <div>
        {" "}
        <div className="justify-content-center" id="tablePorudzbenice">
          <Form.Row>
            <Table className="table-hover table-bordered" responsive>
              <thead>
                <tr>
                  <th>#</th>
                  <th>Datum</th>
                  <th>Izmeni?</th>
                </tr>
              </thead>
              <tbody>
                {this.props.porudzbenice.map(porudzbenica => (
                  <tr
                    key={porudzbenica.id}
                    className="table-row"
                    style={
                      this.props.selectedRow === porudzbenica.id
                        ? { backgroundColor: "#D3D3D3	" }
                        : {}
                    }
                    onClick={async () => {
                      await this.setSelectedRow(porudzbenica.id);
                      await this.setPorudzbenica(porudzbenica);
                    }}
                  >
                    <td>{porudzbenica.id}</td>
                    <td>{porudzbenica.datum}</td>
                    <td>
                      <Button
                        name="Izmena"
                        onClick={async () => {
                          await this.setPorudzbenica(porudzbenica);
                          this.props.switchToObrada();
                        }}
                      >
                        Izmena
                      </Button>
                    </td>
                  </tr>
                ))}
              </tbody>
            </Table>
          </Form.Row>
        </div>
      </div>
    );
  }
}
