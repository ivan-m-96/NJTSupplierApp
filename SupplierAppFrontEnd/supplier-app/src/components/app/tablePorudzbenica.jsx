import React, { Component, useEffect } from "react";
import Table from "react-bootstrap/Table";
import Button from "react-bootstrap/Button";
import ButtonGroup from "react-bootstrap/ButtonGroup";
import Form from "react-bootstrap/Form";
import Col from "react-bootstrap/Col";

import "./tablePorudzbenica.css";
export class tablePorudzbenica extends Component {
  constructor(props) {
    super(props);
    this.state = {
      stavke: []
    };

    this.count = 0;
  }

  componentDidUpdate() {
    this.count = 0;
  }

  getCount = () => {
    this.count++;
    return this.count;
  };
  render() {
    return (
      <div id="obradaPorudzbenice">
        <div className="row justify-content-center">
          <Table className="table-hover" responsive>
            <thead>
              <tr>
                <th>#</th>
                <th>Naziv</th>
                <th>Jedinična cena</th>
                <th>Količina</th>
                <th>Ukupna cena</th>
                {this.props.obrada && <th>Izbriši</th>}
              </tr>
            </thead>
            <tbody>
              {this.props.proizvodi.map(stavka => {
                if (!stavka.zaBrisanje) {
                  return (
                    <tr key={stavka.id}>
                      <td>{this.getCount()}</td>
                      <td>{stavka.stavkaKataloga.naziv}</td>
                      <td>{stavka.stavkaKataloga.proizvod.cena}</td>
                      <td>
                        <input
                          className="kolicina"
                          id={stavka.porId}
                          type="text"
                          pattern="[0-9]*"
                          value={stavka.kolicina}
                          onChange={this.props.handleStavkaChange}
                        />
                        {/* <input
                      type=number
                      id={stavka.porId}
                      value={stavka.kolicina}
                      onChange={this.props.handleStavkaChange}
                    ></input> */}
                      </td>
                      <td>
                        {stavka.stavkaKataloga.proizvod.cena * stavka.kolicina}
                      </td>
                      {this.props.obrada && (
                        <td>
                          <Button
                            id={stavka.id}
                            variant="danger"
                            onClick={this.props.handleBrisanjeStavke}
                            disabled={stavka.zaBrisanje === true}
                          >
                            Izbriši
                          </Button>
                        </td>
                      )}
                      <th />
                    </tr>
                  );
                }
              })}
            </tbody>
          </Table>
        </div>
      </div>
    );
  }
}

export default tablePorudzbenica;
