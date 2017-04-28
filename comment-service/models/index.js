/**
 * Created by Jiravat on 4/2/2017.
 */
'use strict';

const db = require('../datasource').getDB();

const CommentSchema = require('./Comment').CommentSchema;
const Comment = db.model('Comment', CommentSchema);

module.exports = {
    Comment
};
