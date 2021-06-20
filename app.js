const express = require("express");
const { body, validationResult } = require("express-validator");
const dotenv = require("dotenv");

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
  res.render("index", { active: "login", errors: [] });
});

// Login request
app.post(
  "/",
  body("bilkent_id").not().isEmpty().withMessage("Bilkent ID cannot be blank"),
  body("bilkent_id")
    .trim()
    .isInt()
    .withMessage("Bilkent ID must be an integer"),
  body("password")
    .isLength({ min: 6 })
    .withMessage("Password is too short (minimum is 6 characters)"),
  (req, res) => {
    const errors = validationResult(req);
    if (!errors.isEmpty()) {
      return res
        .status(400)
        .render("index", { active: "login", errors: errors.array() });
    }

    let user = { id: req.body.bilkent_id, password: req.body.password };
    console.log(user);

    if (isUserRegistered(user)) {
      return res
        .status(200)
        .render("login_success", { id: user.id, active: null });
    } else {
      return res
        .status(401)
        .render("index", {
          active: "login",
          errors: [
            {
              param: "password",
              msg: "Wrong password or Bilkent ID number.",
            },
          ],
        });
    }
  }
);

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
