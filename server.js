const express = require( "express");
const dotenv = require("dotenv");
const ejs =  ("ejs");

dotenv.config();

const app = express();

app.set("view engine", "ejs");
app.use(express.static(__dirname + "/public"));

app.get("/", (req, res) => {
  res.render("login");
});

const PORT = process.env.PORT || 5000;

app.listen(PORT, console.log(`Server running on port ${PORT}`));
