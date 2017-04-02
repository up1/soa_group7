/**
 * Created by Jiravat on 4/2/2017.
 */
'use strict';
// The mongoose instance.
const _mongoose = require('mongoose');

var env = require('node-env-file');
env(__dirname + '/.env');

// use bluebird promise library instead of mongoose default promise library
_mongoose.Promise = global.Promise;

//show log
_mongoose.set('debug', false);

// The database mapping.
const dbs = { };

function getDb(url, poolSize) {
    if (!dbs[url]) {
        var options = {
            db: { native_parser: true },
            server: { poolSize: 5 },
            user: process.env.DB_USER,
            password: process.env.DB_PASSWORD,
            auth: {
                authdb: 'admin'
            }
        }
        const db = _mongoose.createConnection(process.env.DB_URL, options);
        dbs[url] = db;
    }
    return dbs[url];
}

function getMongoose() {
    return _mongoose;
}

// exports the functions
module.exports = {
    getDb,
    getMongoose,
};
