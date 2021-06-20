const express = require("express");
const { body, validationResult } = require("express-validator");
const cookieParser = require("cookie-parser");
const session = require("express-session");
const dotenv = require("dotenv");

dotenv.config();

const app = express();

app.use(cookieParser());
app.use(session({ secret: "Th3y Wi11 N3v3r Gue55 Thi5!" }));

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
const captchaError = { param: "captcha", msg: null };
const LOGIN_ATTEMPTS_FOR_CAPTCHA = 3;

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
      const errorsArray = errors.array();
      if (passedInvalidLoginAttemptsForSession(req)) {
        errorsArray.push(captchaError);
      }

      return res
        .status(400)
        .render("index", { active: "login", errors: errorsArray });
    }

    let user = { id: req.body.bilkent_id, password: req.body.password };
    console.log(user);

    if (isUserRegistered(user)) {
      return res
        .status(200)
        .render("login_success", { id: user.id, active: null });
    } else {
      const errorsArray = [
        { param: "password", msg: "Wrong password or Bilkent ID number." },
      ];

      if (passedInvalidLoginAttemptsForSession(req)) {
        errorsArray.push(captchaError);
      }

      return res.status(401).render("index", {
        active: "login",
        errors: errorsArray,
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

function passedInvalidLoginAttemptsForSession(request) {
  if (request.session.login_attempts) {
    request.session.login_attempts++;
    console.log(request.session.login_attempts)
    if (request.session.login_attempts >= LOGIN_ATTEMPTS_FOR_CAPTCHA) {
      return true;
    }
  } else {
    request.session.login_attempts = 1;
    console.log(request.session.login_attempts)

    return false;
  }
}
