/**
 * Created by Jiravat on 3/26/2017.
 */
'use strict';

//Do Promise
global.Promise = require('bluebird');

const config = require('config');
const express = require('express');
const cors = require('cors');
const bodyParser = require('body-parser');
const _ = require('lodash');
const helper = require('./common/helper');
const errorMiddleware = require('./common/ErrorMiddleware');
const winston = require('winston');

const app = express();
const http = require('http').Server(app);

app.set('port', config.PORT);

app.use(bodyParser.json());
app.use(bodyParser.urlencoded({extended: false})); //If Encode Can't test with postman
app.use(cors());
const apiRouter = express.Router();

