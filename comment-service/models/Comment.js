/**
 * Created by Jiravat on 3/31/2017.
 */
'use strict';

const _ = require('lodash');
const mongoose = require('../datasource').getMongoose();
const timestamps = require('mongoose-timestamp');
//const ObjectId = mongoose.Schema.Types.ObjectId;

const CommentSchema = new mongoose.Schema({
    text: {
        type: String,
        require: true
    },
    userId: {
        type: Number,
        require: true
    },
    postId: {
        type: Number,
        require: true
    }
});

CommentSchema.plugin(timestamps);

if (!CommentSchema.options.toObject) {
    CommentSchema.options.toObject = {};
}

CommentSchema.options.toObject.transform = function (doc, ret, options) {
    const sanitized = _.omit(ret, '__v', '_id', 'createdAt', 'updatedAt');
    sanitized.id = doc._id;
    return sanitized;
};

module.exports = {
    CommentSchema,
};