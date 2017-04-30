/**
 * Created by Jiravat on 3/31/2017.
 * Modified by Chaniwat (chaniwat.meranote@gmail.com) on 4/28/2017.
 */
'use strict';

const _ = require('lodash');
const mongoose = require('../datasource').getMongoose();
const timestamps = require('mongoose-timestamp');

function sanitizeFragmentComment(doc, ret) {
    const sanitized = _.omit(ret, '__v', '_id');
    sanitized.id = doc._id;
    return sanitized;
}

const CommentSchema = new mongoose.Schema({
    userId: {type: Number, require: true},
    text: {type: String, require: true}
}, {
  toJSON: {
    transform: sanitizeFragmentComment
  },
  toObject: {
    transform: sanitizeFragmentComment
  }
});
CommentSchema.plugin(timestamps);

function sanitizeComment(doc, ret) {
    const sanitized = _.omit(ret, '__v', '_id');
    return sanitized;
}

const PostCommentsSchema = new mongoose.Schema({
    postId: { type: Number, require: true },
    comments: [CommentSchema],
    comment_count: { type: Number, require: true }
}, {
    toJSON: {
      transform: sanitizeComment
    },
    toObject: {
      transform: sanitizeComment
    }
});

module.exports = {
    CommentSchema: PostCommentsSchema,
};
