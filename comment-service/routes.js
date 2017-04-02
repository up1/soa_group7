/**
 * Created by Jiravat on 4/2/2017.
 */
'use strict';

const auth = require('./common/Auth.js');

module.exports = {
    '/comments/:postId': {
        get: {
            controller: 'CommentController',
            method: 'getComments',
        },
    },
    '/comment/:commend': {
        post: {
            controller: 'CommentController',
            method: 'getSIngle',
        },
    }
};