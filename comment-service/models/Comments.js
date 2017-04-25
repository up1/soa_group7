/**
 * Created by: Jiravat
 * Date: 4/25/2017
 * Project: comment-service
 */
'use strict';

const _ = require('lodash');
const mongoose = require('../datasource').getMongoose();
const ObjectId = mongoose.Schema.Types.ObjectId;

const CommentsSchema = new mongoose.Schema({
    postId: {
        type: Number,
        require: true
    },
    comments: {
        type: [ObjectId],
        require: false,
        ref:'Comment'
    }
});

CommentsSchema.plugin(timestamps);

if (!CommentsSchema.options.toObject) {
    CommentsSchema.options.toObject = {};
}

CommentsSchema.options.toObject.transform = function (doc, ret, options) {
    const sanitized = _.omit(ret, '__v', '_id', 'createdAt', 'updatedAt');
    sanitized.id = doc._id;
    return sanitized;
};

module.exports = {
    CommentsSchema,
};