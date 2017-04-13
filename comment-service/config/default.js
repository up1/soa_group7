/**
 * Created by Jiravat on 4/2/2017.
 */
var env = require('node-env-file');
env(__dirname + '/../.env');

module.exports = {

    LOG_LEVEL: process.env.LOG_LEVEL || 'debug',
    PORT: process.env.PORT || 9006,
    JWT_SECRET: process.env.JWT_SECRET || 'JWT_SECRET',
    SALT_WORK_FACTOR: 2,
    TOKEN_EXPIRES: 10 * 60 * 60,
    API_VERSION: 1,
    RESET_CODE_EXPIRES: 60 * 60,
    db: {
        url: process.env.MONGOLAB_URI,
        poolSize: 5,
    }
};
