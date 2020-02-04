import React, { Component } from "react";
import Combobox from "react-widgets/lib/Combobox";
import Button from "react-bootstrap/Button";
import Form from "react-bootstrap/Form";
import Col from "react-bootstrap/Col";
import Row from "react-bootstrap/Row";
import InputGroup from "react-bootstrap/InputGroup";
import Tabela from "./tablePorudzbenica";
import Popover from "react-bootstrap/Popover";
import {
  getAllDobavljac,
  getAllKatalog,
  getKataloziZaDobavljaca,
  postPorudzbenica,
  getPorudzbeniceZaDobavljaca,
  getPorudzbenica,
  patchPorudzbenica,
  removePorudzbenica
} from "./service/api";

import "react-widgets/dist/css/react-widgets.css";
import "./obradaPorudzbenice.css";
import { instanceOf } from "prop-types";
import { throws } from "assert";

export default class obradaPorudzbenice extends Component {
  constructor(props) {
    super(props);
    this.state = {
      kolicina: 0,
      dobavljaci: [],
      katalozi: [],
      proizvodi: [],
      stavke: [],
      porudzbenice: [],
      curStavkaId: 1,
      datum: new Date(),
      izmenaPorudzbenice: false,
      selectedDobavljac: "",
      selectedDobavljacPrikaz: "",
      selectedKatalog: "",
      selectedKatalogPrikaz: "",
      selectedProizvod: "",
      selectedProizvodPrikaz: "",
      selectedDobavljacZaPorudzbenice: "",
      selectedPorudzbenica: "",
      selectedPorudzbenicaPrikaz: "",
      insertMessage: "",
      errors: {}
    };

    let startState = {
      kolicina: 0,
      dobavljaci: [],
      katalozi: [],
      proizvodi: [],
      stavke: [],
      porudzbenice: [],
      curStavkaId: 1,
      datum: new Date(),
      izmenaPorudzbenice: false,
      selectedDobavljac: "",
      selectedKatalog: "",
      selectedProizvod: "",
      selectedDobavljacZaPorudzbenice: "",
      selectedPorudzbenica: "",
      selectedPorudzbenicaPrikaz: "",
      insertMessage: "",
      errors: {}
    };

    this.handleKolicinaChange = this.handleKolicinaChange.bind(this);
    this.getDobavljaci = this.getDobavljaci.bind(this);
    this.handleSelect = this.handleSelect.bind(this);
    this.handleOnClick = this.handleOnClick.bind(this);
    this.validateForm = this.validateForm.bind(this);
    this.handleUnosPorudzbenice = this.handleUnosPorudzbenice.bind(this);
    this.ucitajPorudzbenicu = this.ucitajPorudzbenicu.bind(this);
    this.proveriPostojanjePorudzbenice = this.proveriPostojanjePorudzbenice.bind(
      this
    );
    this.handleUnosNove = this.handleUnosNove.bind(this);
    this.handleBrisanjePorudzbenice = this.handleBrisanjePorudzbenice.bind(
      this
    );
    this.handleStavkaChange = this.handleStavkaChange.bind(this);
    this.handleBrisanjeStavke = this.handleBrisanjeStavke.bind(this);
  }

  handleKolicinaChange(e) {
    this.setState({ kolicina: parseInt(e.target.value) });
  }
  handleSelect(e, name) {
    console.log(e);
    this.setState({
      [name]: e
    });
  }

  componentWillMount() {
    if (this.props.location.state) {
      if (this.props.location.state && this.props.location.state.porudzbenica) {
        this.setState({
          selectedPorudzbenica: this.props.location.state.porudzbenica,
          selectedDobavljacZaPorudzbenice: this.props.location.state
            .porudzbenica.dobavljac
        });
      }
    }
  }

  componentDidMount() {
    this.getDobavljaci();
    if (this.state.selectedPorudzbenica) {
      this.ucitajPorudzbenicu();
    }
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
  async getProizvodi() {
    try {
      let proizvodi = [];
      await getAllKatalog(this.state.selectedKatalog).then(result => {
        console.log("from get all katalog");
        console.log(result);
        result.stavke.forEach(element => {
          element = { ...element, katalog: this.state.selectedKatalog };

          proizvodi.push(element);
        });
      });

      this.setState({ proizvodi: proizvodi });
    } catch (e) {
      console.log(e.message);
    }
  }
  async getKatalozi() {
    try {
      let katalozi = [];
      if (this.state.selectedDobavljac) {
        await getKataloziZaDobavljaca(this.state.selectedDobavljac.id).then(
          result => {
            console.log(result);
            katalozi = result.map(katalog => katalog.id);
          }
        );
        this.setState({ katalozi });
      }
      if (!katalozi) {
        console.log(
          "za dobavljaca sa id " +
            this.state.selectedDobavljac.id +
            "nema kataloga"
        );
      }
      return katalozi;
    } catch (error) {
      console.log(error);
    }
  }

  async getPorudzbenice() {
    try {
      let porudzbenice = [];
      if (this.state.selectedDobavljacZaPorudzbenice) {
        await getPorudzbeniceZaDobavljaca(
          this.state.selectedDobavljacZaPorudzbenice.id
        ).then(result => {
          console.log(result);
          porudzbenice = result.map(porudzbenica => porudzbenica);
        });
        this.setState({ porudzbenice });
        console.log(porudzbenice);
      }
      if (!porudzbenice) {
        console.log(
          "za dobavljaca sa id " +
            this.state.selectedDobavljacZaPorudzbenice.id +
            "nema kataloga"
        );
      }
      return porudzbenice;
    } catch (error) {
      console.log(error);
    }
  }

  validateForm() {
    let toSubmit = true;
    let errors = {};

    if (!this.state.selectedDobavljac) {
      errors["dobavljaci"] = "Morate izabrati dobavljača!";

      toSubmit = false;
    }

    if (!this.state.selectedKatalog) {
      errors["katalozi"] = "Morate izabrati katalog!";

      toSubmit = false;
    }

    if (!this.state.selectedProizvod) {
      errors["proizvodi"] = "Morate izabrati proizvod!";
      toSubmit = false;
    }

    if (this.state.kolicina <= 0) {
      errors["kolicina"] = "Količina mora biti iznad 0!";
      toSubmit = false;
    }

    this.setState({ errors });
    return toSubmit;
  }

  handleOnClick(e) {
    let isValid = this.validateForm();
    if (isValid) {
      let stavke = [...this.state.stavke];

      let selectedProizvod = {
        stavkaKataloga: this.state.selectedProizvod,
        kolicina: this.state.kolicina
      };
      console.log(selectedProizvod);
      let postoji = false;
      if (stavke) {
        stavke.forEach(stavka => {
          if (
            stavka.stavkaKataloga.proizvod.id ===
            selectedProizvod.stavkaKataloga.proizvod.id
          ) {
            stavka.kolicina += this.state.kolicina;

            postoji = true;
          }
        });
      }
      if (!postoji) {
        let stavkeCopy = [...stavke];
        console.log(stavkeCopy);
        if (stavkeCopy.length > 0) {
          selectedProizvod.id = stavkeCopy.pop().id + 1;
        } else selectedProizvod.id = 1;
        // this.setState({ curStavkaId: this.state.curStavkaId + 1 });
        stavke.push(selectedProizvod);
        console.log(stavke);
      }
      this.setState({ stavke });
    }
  }

  async handleUnosPorudzbenice() {
    if (this.state.stavke.length > 0) {
      let porudzbenica = {
        id: this.props.redniBrojPorudzbenice,
        datum: this.state.datum,
        dobavljac: this.state.selectedDobavljac,
        prenociste: { id: 1 },
        stavke: this.state.stavke
      };
      console.log(porudzbenica);
      this.proveriPostojanjePorudzbenice(this.props.redniBrojPorudzbenice);
      let message = "";
      if (!this.state.izmenaPorudzbenice) {
        let result = await postPorudzbenica(porudzbenica);
        if (result) {
          console.log("POST IZVRSEN");
          message = result.message;
        }
      } else {
        let result = await patchPorudzbenica(porudzbenica);
        if (result) {
          console.log("PATCH IZVRSEN");
          console.log(result);
          message = result.message;
        }
      }
      this.setState({ insertMessage: message });
    }
  }

  async proveriPostojanjePorudzbenice(id) {
    let porudzbenica = await getPorudzbenica(id);
    if (porudzbenica.data) {
      console.log(porudzbenica);
      console.log(
        "Menjam izmenaPorudzbenice u true iz proveriPostojanjePorudzbenice"
      );
      this.setState({ izmenaPorudzbenice: true });
    } else this.setState({ izmenaPorudzbenice: false });
    console.log(
      "OVA PORUDZBENICA JE ZA IZMENU: " + this.state.izmenaPorudzbenice
    );
    return true;
  }
  async ucitajPorudzbenicu() {
    let porudzbenica = null;

    if (
      this.state.selectedPorudzbenica &&
      this.state.selectedPorudzbenica.dobavljac.id ===
        this.state.selectedDobavljacZaPorudzbenice.id
    ) {
      porudzbenica = await getPorudzbenica(this.state.selectedPorudzbenica.id);

      console.log("----PORUDZBENICA----");
      console.log(porudzbenica.data);
    }
    if (porudzbenica) {
      this.setState({ stavke: porudzbenica.data.stavke });
      this.setState({ selectedDobavljac: porudzbenica.data.dobavljac });
      this.setState({ datum: new Date(porudzbenica.data.datum) });
      this.getKatalozi();
      await this.props.setSelectedPorudzbenica(this.state.selectedPorudzbenica);
      this.setState({
        redniBrojPorudzbenice: this.props.redniBrojPorudzbenice
      });
      this.setState({ izmenaPorudzbenice: true });
      this.setState({ insertMessage: "" });
    }
  }

  async handleUnosNove() {
    await this.setState({
      kolicina: 0,
      katalozi: [],
      proizvodi: [],
      stavke: [],
      porudzbenice: [],
      curStavkaId: 1,
      datum: new Date(),
      izmenaPorudzbenice: false,
      selectedDobavljac: "",
      selectedKatalog: "",
      selectedProizvod: "",
      selectedDobavljacZaPorudzbenice: "",
      selectedPorudzbenica: "",
      insertMessage: "",
      errors: {}
    });
    this.props.setSelectedPorudzbenica(this.state.selectedPorudzbenica);
  }

  async handleBrisanjePorudzbenice() {
    let result = await removePorudzbenica({
      ...this.state.selectedPorudzbenica,
      prenociste: { id: 1 }
    });

    await this.setState({
      kolicina: 0,
      katalozi: [],
      proizvodi: [],
      stavke: [],
      porudzbenice: [],
      curStavkaId: 1,
      datum: new Date(),
      izmenaPorudzbenice: false,
      selectedDobavljac: "",
      selectedKatalog: "",
      selectedProizvod: "",
      selectedDobavljacZaPorudzbenice: "",
      selectedPorudzbenica: "",
      insertMessage: "Porudzbenica je izbrisana!",
      errors: {}
    });
    this.props.setSelectedPorudzbenica(null);
  }

  handleStavkaChange(e) {
    console.log(e.target.id + " " + e.target.value);
    e.target.value = e.target.value.replace(/\D/, "");
    let proizvodi = [...this.state.stavke];
    let stavke = [];
    console.log(
      "--------------------------------PROIZVODI----------------------------------------"
    );
    console.log(proizvodi);
    proizvodi.forEach(element => {
      console.log(element);
      console.log(typeof element.id + " " + typeof e.target.value);

      if (element.id == e.target.id) {
        console.log(element.id + " " + e.target.id);
        element.kolicina = e.target.value;

        console.log(element);
      }
      stavke.push(element);
    });

    this.setState({ stavke: stavke });
  }

  handleBrisanjeStavke(e) {
    let stavkaId = e.target.id;
    let proizvodi = [...this.state.stavke];

    console.log("stavkaId" + stavkaId);
    proizvodi.forEach((element, index, proizvodi) => {
      console.log(element);
      if (element.id == stavkaId) {
        element = { ...element, zaBrisanje: true };
        proizvodi[index] = element;
        console.log("izmenjeni");
        console.log(element);
      }
    });
    console.log(proizvodi);
    this.setState({ stavke: proizvodi });
  }

  render() {
    return (
      <div id="obradaPorudzbenice">
        <div>
          <Form.Group>
            <Row bsPrefix="prikazPorudzbenice rounded mb-0">
              <Col>
                <Form.Label>Dobavljač</Form.Label>

                <Combobox
                  id="dobavljaci"
                  data={this.state.dobavljaci}
                  textField="naziv"
                  filter="contains"
                  onSelect={async e => {
                    await this.handleSelect(
                      e,
                      "selectedDobavljacZaPorudzbenice"
                    );
                    await this.handleSelect("", "selectedPorudzbenica");

                    await this.handleSelect("", "selectedPorudzbenicaPrikaz");
                    await this.getPorudzbenice();
                  }}
                ></Combobox>
              </Col>

              <Col>
                <div className="container">
                  <Form.Label>Porudžbenica</Form.Label>
                  <InputGroup bsPrefix="porud" id="porudzbeniceIG">
                    <Combobox
                      id="porudzbenice"
                      data={this.state.porudzbenice}
                      value={this.state.selectedPorudzbenicaPrikaz}
                      onChange={value =>
                        this.setState({ selectedPorudzbenicaPrikaz: value })
                      }
                      textField={item => {
                        if (this.state.selectedDobavljacZaPorudzbenice) {
                          return typeof item === "string"
                            ? item
                            : "ID: " +
                                item.id +
                                " " +
                                new Date(item.datum).toLocaleDateString();
                        }
                      }}
                      filter="contains"
                      onSelect={async e => {
                        this.handleSelect(e, "selectedPorudzbenica");
                        this.handleSelect(e, "selectedPorudzbenicaPrikaz");
                      }}
                    ></Combobox>

                    <Button
                      id="ucitavanje"
                      variant="primary"
                      onClick={this.ucitajPorudzbenicu}
                    >
                      Učitaj
                    </Button>
                  </InputGroup>
                </div>
              </Col>
            </Row>
          </Form.Group>
          <div className="inputKolicina rounded mb-0">
            <Form bsPrefix="inputKolicina" noValidate>
              <Form.Group controlId="formInputKolicina">
                <Row>
                  <Col>
                    <Form.Label>Dobavljač</Form.Label>

                    <Combobox
                      id="dobavljaci"
                      data={this.state.dobavljaci}
                      disabled={
                        this.state.izmenaPorudzbenice ||
                        this.state.stavke.length > 0
                      }
                      textField="naziv"
                      filter="contains"
                      value={this.state.selectedDobavljacPrikaz}
                      onChange={value => {
                        this.setState({ selectedDobavljacPrikaz: value });
                        if (!this.selectedDobavljac) {
                          this.handleSelect("", "selectedKatalog");
                          this.handleSelect("", "selectedKatalogPrikaz");
                          this.handleSelect("", "selectedProizvod");
                          this.handleSelect("", "selectedProizvodPrikaz");
                        }
                      }}
                      onSelect={async e => {
                        await this.handleSelect(e, "selectedDobavljac");
                        await this.handleSelect(e, "selectedDobavljacPrikaz");
                        this.handleSelect("", "selectedKatalogPrikaz");
                        this.handleSelect("", "selectedKatalog");
                        this.handleSelect("", "selectedProizvod");
                        this.handleSelect("", "selectedProizvodPrikaz");
                        await this.getKatalozi();
                        await this.props.setSelectedDobavljac(
                          this.state.selectedDobavljac
                        );
                      }}
                    ></Combobox>
                    <div className="error">
                      {this.state.errors["dobavljaci"]}
                    </div>
                  </Col>
                  <Col>
                    <Form.Label>Katalog</Form.Label>

                    <Combobox
                      id="katalozi"
                      ref={this.pokusajReference}
                      data={this.state.katalozi}
                      textField="naziv"
                      filter="contains"
                      minLength="1"
                      disabled={!this.state.selectedDobavljac}
                      value={this.state.selectedKatalogPrikaz}
                      onChange={value => {
                        this.setState({ selectedKatalogPrikaz: value });
                        if (!this.state.selectedKatalog) {
                          this.handleSelect("", "selectedProizvod");
                          this.handleSelect("", "selectedProizvodPrikaz");
                        }
                      }}
                      onSelect={async e => {
                        await this.handleSelect(e, "selectedKatalog");
                        await this.handleSelect(e, "selectedKatalogPrikaz");
                        this.handleSelect("", "selectedProizvod");
                        this.handleSelect("", "selectedProizvodPrikaz");

                        await this.getProizvodi();
                      }}
                      required
                    ></Combobox>
                    <div className="error">{this.state.errors["katalozi"]}</div>
                  </Col>
                </Row>
                <Row>
                  <Col>
                    <Form.Label>Proizvod</Form.Label>

                    <Combobox
                      id="proizvodi"
                      data={this.state.proizvodi}
                      textField="naziv"
                      value={this.state.selectedProizvodPrikaz}
                      onChange={e => {
                        this.setState({
                          selectedProizvodPrikaz: e
                        });
                        if (!this.state.selectedProizvod)
                          this.handleSelect(0, "kolicina");
                      }}
                      disabled={!this.state.selectedKatalog}
                      onSelect={e => {
                        this.handleSelect(e, "selectedProizvodPrikaz");
                        this.handleSelect(e, "selectedProizvod");
                      }}
                      filter="contains"
                    ></Combobox>
                    <div className="error">
                      {this.state.errors["proizvodi"]}
                    </div>
                  </Col>

                  <Col>
                    <Form.Label>Količina</Form.Label>
                    <InputGroup>
                      <Form.Control
                        type="number"
                        placeholder="Količina"
                        disabled={!this.state.selectedProizvod}
                        value={this.state.kolicina}
                        onChange={this.handleKolicinaChange}
                        name="kolicina"
                      />

                      <InputGroup.Append>
                        <Button
                          id="unos"
                          variant="primary"
                          onClick={this.handleOnClick}
                        >
                          Unesi
                        </Button>
                      </InputGroup.Append>
                    </InputGroup>
                    <div className="error">{this.state.errors["kolicina"]}</div>
                  </Col>
                </Row>
              </Form.Group>
            </Form>
          </div>
        </div>
        <div className="porudzbenicaTabela rounded mb-0">
          <Form>
            <Form.Group>
              <Row>
                <Col>
                  <Form.Label>Redni broj porudžbenice</Form.Label>

                  <Form.Control
                    type="text"
                    disabled={true}
                    value={this.props.redniBrojPorudzbenice}
                  ></Form.Control>
                </Col>
                <Col>
                  <Form.Label>Datum</Form.Label>
                  <InputGroup>
                    <Form.Control
                      type="text"
                      placeholder="Datum"
                      disabled={true}
                      value={this.state.datum.toDateString()}
                      name="date"
                    />
                  </InputGroup>
                </Col>
              </Row>
            </Form.Group>
          </Form>
          <Tabela
            proizvodi={this.state.stavke}
            handleStavkaChange={this.handleStavkaChange}
            handleBrisanjeStavke={this.handleBrisanjeStavke}
            obrada={true}
          ></Tabela>
          <div>{this.state.insertMessage}</div>
          <div>
            <Button
              id="unesiPorudzbenicu"
              variant="primary"
              onClick={this.handleUnosPorudzbenice}
              data-toggle="popover"
            >
              Unesi
            </Button>
            <Button
              id="unosNove"
              variant="primary"
              onClick={this.handleUnosNove}
              data-toggle="popover"
            >
              Poništi
            </Button>
            <Button
              id="brisanjePorudzbenice"
              variant="danger"
              onClick={this.handleBrisanjePorudzbenice}
              data-toggle="popover"
              disabled={
                !this.state.selectedPorudzbenica &&
                !this.state.izmenaPorudzbenice
              }
            >
              Izbriši
            </Button>
          </div>
        </div>
      </div>
    );
  }
}
