/**
 * Created by Jiravat on 3/26/2017.
 */
const _ = require('lodash');
const co = require('co');

module.exports = {
    wrapExpress,
    autoWrapExpress
};

function wrapExpress(fn) {
    return function (req, res, next) {
        co(fn(req, res, next)).catch(next);
    };
}

function autoWrapExpress(obj) {
    if (_.isArray(obj)) {
        return obj.map(autoWrapExpress);
    }
    if (_.isFunction(obj)) {
        if (obj.constructor.name === 'GeneratorFunction') {
            return wrapExpress(obj);
        }
        return obj;
    }
    _.each(obj, (value, key) => {
        obj[key] = autoWrapExpress(value);
    });
    return obj;
}