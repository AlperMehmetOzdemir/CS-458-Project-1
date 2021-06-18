const express = require("express");
const dotenv = require("dotenv");
const ejs = "ejs";

dotenv.config();

const app = express();

app.set("view engine", "ejs");
app.use(express.urlencoded({ extended: false }));
app.use(express.static(__dirname + "/public"));

const users = [
  {
    id: "12345",
    password: "pass12345",
  },
  {
    id: "20212021",
    password: "pass2021",
  },
  {
    id: "27",
    password: "admin27",
  },
];

// Index page
app.get("/", (req, res) => {
  res.render("index", { active: "login" });
});

// Login request
app.post("/", (req, res) => {
  let user = { id: req.body.bilkent_id, password: req.body.password };

  console.log(user);

  if (isUserRegistered(user)) {
    res.status(200).render("login_success", { id: user.id, active: null });
  } else {
    res.status(401).send("You shall not pass");
  }
});

app.get("/reset-password", (req, res) => {
  res.render("reset_password", { active: "reset" });
});

const PORT = process.env.PORT || 5000;

app.listen(PORT, () => {
  console.log(`Server running on port ${PORT}`);
});

function isUserRegistered(userToBeChecked) {
  try {
    if (
      users.some(
        (user) =>
          user.id == userToBeChecked.id &&
          user.password == userToBeChecked.password
      )
    ) {
      return true;
    } else {
      return false;
    }
  } catch (error) {
    throw new Error("[User] is not well defined");
  }
}
