/**
 * Created by Jiravat on 4/2/2017.
 */
'use strict';
// The mongoose instance.
const _mongoose = require('mongoose');

// use bluebird promise library instead of mongoose default promise library
_mongoose.Promise = global.Promise;

//show log
_mongoose.set('debug', false);

// The database mapping.
var dbs;

function getDB() {
    if (!dbs) {
        var options = {
            db: { native_parser: true },
            server: { poolSize: process.env.DB_POOL_SIZE }
        }
        
        if(process.env.DB_AUTH) {
          dbs = _mongoose.createConnection(`mongodb://${process.env.DB_USER}:${process.env.DB_PASSWORD}@${process.env.DB_HOST}:${process.env.DB_PORT}/${process.env.DB_NAME}?authSource=${process.env.DB_NAME}`, options);
        } else {
          dbs = _mongoose.createConnection(`mongodb://${process.env.DB_HOST}:${process.env.DB_PORT}/${process.env.DB_NAME}`, options);
        }
    }

    return dbs;
}

function getMongoose() {
    return _mongoose;
}

// exports the functions
module.exports = {
    getDB,
    getMongoose,
};
