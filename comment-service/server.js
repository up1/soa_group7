/**
 * Created by Jiravat on 3/26/2017.
 */
'use strict';

//Do Promise
global.Promise = require('bluebird');

const express = require('express');
const cors = require('cors');
const bodyParser = require('body-parser');
const _ = require('lodash');
const winston = require('winston');

const app = express();
const config = require('config').load(app.settings.env);
const http = require('http').Server(app);

const helper = require('./common/helper');
const errorMiddleware = require('./common/ErrorMiddleware');

app.set('port', process.env.PORT);

app.use(bodyParser.json());
app.use(bodyParser.urlencoded({extended: false})); //If Encode Can't test with postman
app.use(cors());
const apiRouter = express.Router();

// load all routes
_.each(require('./routes'), (verbs, url) => {
    _.each(verbs, (def, verb) => {
        let actions = [
            function (req, res, next) {
                req.signature = def.controller + '#' + def.method;
                next();
            },
        ];
        const method = require('./controllers/' + def.controller)[def.method];
        if (!method) {
            throw new Error(def.method + ' is undefined, for controller ' + def.controller);
        }
        if (def.middleware && def.middleware.length > 0) {
            actions = actions.concat(def.middleware);
        }
        actions.push(method);
        winston.info(`register ${verb} ${url}`);
        apiRouter[verb](`${url}`, helper.autoWrapExpress(actions));
    });
});

app.use('/', apiRouter);
app.use(errorMiddleware());

http.listen(app.get('port'), () => {
    winston.info(`Comment Service listening on port ${app.get('port')}`);
    winston.info(`environment ${app.settings.env}`)
});

module.exports = app;
