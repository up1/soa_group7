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
        },
        delete:{
            controller: 'CommentController',
            method: 'deleteCommentsByPostId'
        }
    },
    '/posts/:postId/comments/:commentId': {
        get: {
            controller: 'CommentController',
            method: 'getSingle'
        },
        put: {
            controller: 'CommentController',
            method: 'updateSingle',
        },
        delete: {
            controller: 'CommentController',
            method: 'deleteSingle',
        }
    }
};
