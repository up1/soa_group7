/**
 * Created by Jiravat on 3/31/2017.
 * Modified by Chaniwat (chaniwat.meranote@gmail.com) on 4/28/2017.
 */

const Exception = require('../error/Exception');

const CommentRepository = require('../services/CommentService');
const models = require('../models');

module.exports = {
    createSingle,
    updateSingle,
    getSingle,
    deleteSingle,
    getCommentsByPostId,
    deleteCommentsByPostId
};

function* createSingle(req, res) {
    const comment = yield CommentRepository.createOne(req.params.postId, req.query.userId, req.body);
    return res.status(201).json(comment);
}

function* updateSingle(req, res) {
    try {
        const comment = yield CommentRepository.updateOne(req.params.postId, req.params.commentId, req.query.userId, req.body);
        res.status(200).json(comment);
    } catch(e) {
        res.status(e.status).json(e);
    }
}

function* deleteSingle(req, res) {
    try {
        const comment = yield CommentRepository.deleteOne(req.params.postId, req.params.commentId, req.query.userId);
        res.status(200).json(comment);
    } catch(e) {
        res.status(e.status).json(e);
    }
}

function* getSingle(req, res) {
    const comment = yield CommentRepository.getOne(req.params.postId, req.params.commentId);
    if (!comment) {
        res.status(404).json(new Exception("Comment not found", 404))
    } else {
        res.status(200).json(comment);
    }
}

function* getCommentsByPostId(req, res) {
    let limit = req.query.limit ? parseInt(req.query.limit) : null;
    let offset = req.query.offset ? parseInt(req.query.offset) : null;
    let comment = yield CommentRepository.getAllByPostId(req.params.postId, limit, offset);
    res.status(200).json(comment);
}

function* deleteCommentsByPostId(req, res) {
    yield CommentRepository.deleteAllByPostId(req.params.postId);
    res.status(200).end();
}
