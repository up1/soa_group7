/**
 * Created by Jiravat on 4/2/2017.
 */
'use strict';

const jwt = require('express-jwt');

const jwtCheck = jwt({
    // the auth0 doesn't base 64 encode the jwt secret now
    secret: new Buffer(process.env.JWT_SECRET, 'base64'),
    audience: process.env.AUTH0_CLIENT_ID,
    requestProperty: 'auth',
    getToken: function fromHeaderOrQuerystring(req) {
        if (req.headers.authorization && req.headers.authorization.split(' ')[0] === 'Bearer') {
            return req.headers.authorization.split(' ')[1];
        }
        return req.query.token;
    },
});

module.exports = () => jwtCheck;
