/**
 * Created by Jiravat on 3/26/2017.
 */
'use strict';

const errors = require('common-errors');
const httpStatus = require('http-status');
const _ = require('lodash');

const DEFAULT_MESSAGE = 'Internal server error';

function middleware(err, req, res) {
    if (err.isJoi) {
        res.status(httpStatus.BAD_REQUEST).json({
            error: _.map(err.details, d => d.message).join() || 'invalid request',
            status: httpStatus.BAD_REQUEST,
        });
    } else {
        const httpError = new errors.HttpStatusError(err);
        if (err.statusCode >= httpStatus.INTERNAL_SERVER_ERROR) {
            httpError.message = DEFAULT_MESSAGE;
        }
        res.status(httpError.statusCode).json({ error: httpError.message || DEFAULT_MESSAGE, status: httpError.statusCode });
    }
}

module.exports = function () {
    return middleware;
};
