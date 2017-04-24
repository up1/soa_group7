/**
 * Created by Jiravat on 4/2/2017.
 */
'use strict';

const auth = require('./common/Auth.js');

module.exports = {
    '/posts/:postId/comments': {
        get: {
            controller: 'CommentController',
            method: 'getCommentsByPostId',
        },
        post: {
            controller: 'CommentController',
            method: 'createSingle',
            // middleware: [auth()],
        },
    },
    '/posts/:postId/comments/:commentId': {
        get: {
            controller: 'CommentController',
            method: 'getSingle'
        },
        put: {
            controller: 'CommentController',
            method: 'updateSingle',
            // middleware: [auth()],
        },
        delete: {
            controller: 'CommentController',
            method: 'deleteSingle',
            // middleware: [auth()],
        }
    }
};