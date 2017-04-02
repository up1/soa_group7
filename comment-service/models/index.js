/**
 * Created by Jiravat on 4/2/2017.
 */
'use strict';

const config = require('config');

const db = require('../datasource').getDb(config.db.url, config.db.poolSize);

// Comment model
const CommentSchema = require('./Comment').CommentSchema;
const Comment = db.model('Comment', CommentSchema);

module.exports = {
    Comment
};