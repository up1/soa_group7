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
        delete:{
            controller: 'CommentController',
            method: 'deleteCommentsByPostId'
        }
    },
    '/posts/star/comments/:commentId': {
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
    },
    '/posts/:postId/comments/count': {
        get: {
            controller: 'CommentController',
            method: 'countCommentByPostId',
        },

    }
};
