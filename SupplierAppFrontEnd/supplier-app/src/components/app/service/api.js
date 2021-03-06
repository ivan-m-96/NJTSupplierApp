import { func } from "prop-types";
import Cookies from "universal-cookie";
const axios = require("axios");

const cookies = new Cookies();
axios.defaults.headers.common["Authorization"] = cookies.get("Authorization");

export function updateHeader() {
  axios.defaults.headers.common["Authorization"] = cookies.get("Authorization");
}

export async function postDobavljac(dobavljac) {
  await axios({
    method: "post",
    url: "http://localhost:3001/api/dobavljaci",
    data: {
      naziv: dobavljac.naziv,
      adresa: dobavljac.adresa
    }
  }).then(function(response) {
    console.log("here's the postDobavljac response:");
    console.log(response);
    return response;
  });
}

export async function getOneDobavljac(id) {
  await axios({
    method: "get",
    url: "http://localhost:3001/api/dobavljaci",
    params: {
      id
    }
  })
    .then(function(response) {
      console.log("iz metode " + response);
      return response;
    })
    .catch(function(error) {
      console.log(error);
    });
}

export async function getAllDobavljac() {
  let reresponse = [];
  await axios({
    method: "get",
    url: "http://localhost:3001/api/dobavljaci"
  })
    .then(function(response) {
      console.log("from the func" + response.data);
      reresponse = response.data;
    })
    .catch(function(error) {
      console.log(error);
      return;
    });

  return reresponse;
}

export async function removeDobavljac(id) {
  let responsee = null;
  await axios
    .delete("http://localhost:3001/api/dobavljaci/" + id)
    .then(function(response) {
      console.log("stigo del");
      console.log(response);
      responsee = response;
      return response;
    })
    .catch(function(error) {
      console.log(error);
    });
  return responsee;
}

export async function updateDobavljac(id, naziv, adresa) {
  let responsee = null;
  await axios
    .patch("http://localhost:3001/api/dobavljaci/" + id, { naziv, adresa })
    .then(response => (responsee = response))
    .catch(e => {
      console.log(e.message);
    });

  return responsee;
}

export async function getAllKatalog(id) {
  let responsee = null;
  console.log("http://localhost:3001/api/katalozi/" + id);
  await axios({
    method: "get",
    url: "http://localhost:3001/api/katalozi/" + id
  })
    .then(function(response) {
      console.log("from the function getAllKatalog" + response);
      responsee = response.data;
    })
    .catch(function(error) {
      console.log(error);
      return;
    });
  return responsee;
}
export async function getKataloziZaDobavljaca(id) {
  let resp = null;
  await axios({
    method: "get",
    url: "http://localhost:3001/api/dobavljaci/" + id + "/katalozi"
  })
    .then(function(response) {
      console.log("from the functione" + response.data);
      resp = response.data;
    })
    .catch(function(error) {
      console.log(error);
      return;
    });
  return resp;
}

export async function getPorudzbeniceZaDobavljaca(id) {
  let resp = null;
  console.log(id + "<- id dobavljaca");
  await axios({
    method: "get",
    url: "http://localhost:3001/api/dobavljaci/" + id + "/porudzbenice"
  })
    .then(function(response) {
      console.log("from the functione" + response.data);
      resp = response.data;
    })
    .catch(function(error) {
      console.log(error);
      return;
    });
  return resp;
}

export async function getPorudzbenica(id) {
  let response = null;
  await axios({
    method: "get",
    url: "http://localhost:3001/api/porudzbenice/" + id
  })
    .then(resp => {
      response = resp;
    })
    .catch(err => console.log(err));
  return response;
}

export async function postPorudzbenica(porudzbenica) {
  let responsee = null;
  await axios({
    method: "post",
    url: "http://localhost:3001/api/porudzbenice",
    data: {
      id: porudzbenica.id,
      datum: porudzbenica.datum,
      dobavljac: porudzbenica.dobavljac,
      prenociste: porudzbenica.prenociste,
      stavke: [...porudzbenica.stavke]
    }
  }).then(function(response) {
    console.log(response);
    responsee = response;
    if (response.status === 200 && response.data) {
      responsee = { ...responsee, message: "Uspešno uneta porudžbenica!" };
    } else {
      responsee = { ...responsee, message: "Neuspešno uneta porudžbenica!" };
    }
  });
  return responsee;
}
export async function login(username, password) {
  let responsee = null;
  await axios({
    method: "post",
    url: "http://localhost:3001/login",
    headers: { "Content-Type": "application/json" },
    data: {
      username: username,
      password: password
    }
  })
    .then(function(response) {
      console.log("From Login");
      console.log(response.headers);
      responsee = response;
      if (response.status === 200) {
        responsee = { ...responsee, message: "Logged in!" };
      }
    })
    .catch(resp => console.log(resp.response));
  console.log(responsee);
  return responsee;
}

export async function patchPorudzbenica(porudzbenica) {
  let responsee = null;
  await axios
    .patch("http://localhost:3001/api/porudzbenice/" + porudzbenica.id, {
      id: porudzbenica.id,
      datum: porudzbenica.datum,
      dobavljac: porudzbenica.dobavljac,
      prenociste: porudzbenica.prenociste,
      stavke: [...porudzbenica.stavke]
    })
    .then(response => {
      responsee = response;
      if (response.status === 200) {
        responsee = {
          ...responsee,
          message: "Uspešno izmenjena porudžbenica!"
        };
      }
    })
    .catch(e => {
      console.log("PATCH");
      console.log(e.message);
    });
  console.log("PATCH done" + responsee);
  return responsee;
}

export async function removePorudzbenica(porudzbenica) {
  let responsee = null;
  console.log("porudzbenica iz zahteva");
  console.log(porudzbenica);
  await axios
    .delete("http://localhost:3001/api/porudzbenice/" + porudzbenica.id, {
      data: { porudzbenica }
    })
    .then(function(response) {
      console.log("stigo del");
      console.log(response);
      responsee = response;
      if (response.status === 200) {
        responsee = {
          ...responsee,
          message: "Uspešno izbrisana porudžbenica!"
        };
      }
      return response;
    })
    .catch(function(error) {
      console.log(error);
    });
  return responsee;
}
export default getAllDobavljac;
