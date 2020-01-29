import React, { Component } from "react";
import Table from "react-bootstrap/Table";
import Button from "react-bootstrap/Button";
import ButtonGroup from "react-bootstrap/ButtonGroup";
import Form from "react-bootstrap/Form";
import Col from "react-bootstrap/Col";
import Forma from "./form";
import TablePorudzbenice from "./tablePorudzbenice";
import TablePorudzbenica from "./tablePorudzbenica";
import { getPorudzbeniceZaDobavljaca } from "./service/api";
import "./App.css";
import "./tableDobavljaci.css";
import getAllDobavljac from "./service/api";

export class tableDobavljaci extends Component {
  constructor(props) {
    super(props);
    this.state = {
      naziv: "",
      adresa: "",
      dobavljaci: [],
      selectedDobavljac: null,
      porudzbenice: [],
      edit: false,
      selectedRowOfPorudzbenice: null,
      selectedPorudzbenica: null
    };
    this.handleClick = this.handleClick.bind(this);
    this.handleTextChange = this.handleTextChange.bind(this);
    this.getDobavljaci = this.getDobavljaci.bind(this);
    this.getPorudzbenice = this.getPorudzbenice.bind(this);
    this.setSelectedRow = this.setSelectedRow.bind(this);
    this.setDobavljac = this.setDobavljac.bind(this);
  }

  async getDobavljaci() {
    try {
      let dobavljaci = [];
      await getAllDobavljac().then(result =>
        result.forEach(element => {
          dobavljaci.push(element);
        })
      );

      this.setState({ dobavljaci: dobavljaci });
    } catch (e) {
      console.log(e.message);
    }
  }
  handleClick() {
    this.setState(this.props.lastSelectedRow);
  }
  async onRemove(e) {
    this.setSelectedRow(null);
    await this.props.onRemove().then();
  }
  componentWillReceiveProps({ someProp }) {
    this.setState({ ...this.state, someProp });
  }
  async setSelectedRow(id) {
    if (this.props.selectedRow === id) {
      this.props.setSelectedRow(null);
    } else {
      this.props.setSelectedRow(id);
    }
  }
  setSelectedValues(naziv, adresa) {
    this.props.setSelectedValues(naziv, adresa);
  }

  setDobavljac(dobavljac) {
    if (this.state.selectedDobavljac === dobavljac)
      this.setState({ selectedDobavljac: null });
    else this.setState({ selectedDobavljac: dobavljac });
  }

  componentWillMount(){
    this.props.setSelectedRow(null);
  }

  async componentDidMount() {
    await this.getDobavljaci();
  }
  async onUpdate() {
    await this.props.onUpdate();
  }
  handleTextChange(e) {
    this.props.handleTextChange(e);
  }

  setSelectedRowPorudzbenice = id =>
    this.setState({ selectedRowOfPorudzbenice: id });

  async getPorudzbenice() {
    try {
      let porudzbenice = [];
      if (this.state.selectedDobavljac) {
        await getPorudzbeniceZaDobavljaca(this.state.selectedDobavljac.id).then(
          result => {
            console.log(result);
            porudzbenice = result.map(porudzbenica => porudzbenica);
          }
        );
        this.setState({ porudzbenice });
        console.log(porudzbenice);
      }
      if (!porudzbenice) {
        console.log(
          "za dobavljaca sa id " +
            this.state.selectedDobavljac +
            "nema porudzbenica"
        );
      }
      this.setState({ porudzbenice });
    } catch (error) {
      console.log(error);
    }
  }

  setSelectedPorudzbenica = porudzbenica =>
    this.setState({ selectedPorudzbenica: porudzbenica });

  switchToObrada = () => {
    console.log("HEELLLOOOOO");
    this.props.history.push({
      pathname: "/porudzbenica",
      state: {
        porudzbenica: this.state.selectedPorudzbenica
      }
    });
  };

  render() {
    return (
      <div id="obradaDobavljaca">
        <Form>
          <div className="justify-content-center" id="izmena-dobavljaca">
            {/* <Forma getDobavljaci={this.getDobavljaci}></Forma> */}

            <Form.Row>
              <Col>
                <Form.Control
                  id="naziv"
                  name="naziv"
                  value={this.props.naziv}
                  placeholder="Naziv"
                  onChange={this.handleTextChange}
                />
              </Col>
              <Col>
                <Form.Control
                  id="adresa"
                  name="adresa"
                  value={this.props.adresa}
                  placeholder="Adresa"
                  onChange={this.handleTextChange}
                />
              </Col>
              <ButtonGroup>
                <Button
                  variant="primary"
                  disabled={
                    this.props.selectedRow ||
                    !this.props.naziv ||
                    !this.props.adresa
                  }
                  onClick={async e => {
                    await this.props.onInsert();
                    await this.getDobavljaci();
                  }}
                >
                  Dodaj
                </Button>
                <Button
                  variant="secondary"
                  disabled={!this.props.selectedRow}
                  onClick={async e => {
                    await this.onUpdate();
                    await this.getDobavljaci();
                  }}
                >
                  Izmena
                </Button>

                <Button
                  id="delete"
                  variant="danger"
                  disabled={!this.props.selectedRow}
                  onClick={async e => {
                    await this.onRemove(e);
                    await this.getDobavljaci();
                  }}
                >
                  Brisanje
                </Button>
              </ButtonGroup>
            </Form.Row>
          </div>
          <div className="justify-content-center">
            <Form.Row>
              <Table className="table-hover table-bordered" responsive>
                <thead>
                  <tr>
                    <th>#</th>
                    <th>Naziv</th>
                    <th>Adresa</th>
                  </tr>
                </thead>
                <tbody>
                  {this.state.dobavljaci.map(dobavljac => (
                    <tr
                      key={dobavljac.id}
                      className="table-row"
                      style={
                        this.props.selectedRow === dobavljac.id
                          ? { backgroundColor: "#D3D3D3	" }
                          : {}
                      }
                      onClick={async () => {
                        await this.setSelectedRow(dobavljac.id);
                        await this.setSelectedValues(
                          dobavljac.naziv,
                          dobavljac.adresa
                        );
                        await this.setDobavljac(dobavljac);
                        await this.getPorudzbenice();
                        await this.setSelectedPorudzbenica(null);
                        await this.setSelectedRowPorudzbenice(null);
                      }}
                    >
                      <td>{dobavljac.id}</td>
                      <td>{dobavljac.naziv}</td>
                      <td>{dobavljac.adresa}</td>
                    </tr>
                  ))}
                </tbody>
              </Table>
            </Form.Row>
            <Form.Row>
              <Col>
                {this.state.selectedDobavljac && (
                  <TablePorudzbenice
                    porudzbenice={this.state.porudzbenice}
                    setSelectedRow={this.setSelectedRowPorudzbenice}
                    selectedRow={this.state.selectedRowOfPorudzbenice}
                    setPorudzbenica={this.setSelectedPorudzbenica}
                    porudzbenica={this.state.selectedPorudzbenica}
                    switchToObrada={this.switchToObrada}
                  ></TablePorudzbenice>
                )}
              </Col>
            </Form.Row>
            <Form.Row>
              <Col>
                {this.state.selectedRowOfPorudzbenice &&
                  this.state.selectedDobavljac && (
                    <TablePorudzbenica
                      proizvodi={
                        this.state.selectedPorudzbenica
                          ? this.state.selectedPorudzbenica.stavke
                          : []
                      }
                    ></TablePorudzbenica>
                  )}
              </Col>
            </Form.Row>
          </div>
        </Form>
      </div>
    );
  }
}

export default tableDobavljaci;
