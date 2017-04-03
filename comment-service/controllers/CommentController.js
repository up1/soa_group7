/**
 * Created by Jiravat on 3/31/2017.
 */
const CommentService = require('../services/CommentService');

const models = require('../models');


module.exports = {
    createSingle,
    updateSingle,
    getSingle,
    deleteSingle,
    getCommentsByPostId
};

function* createSingle(req, res) {
    req.body.userId = req.auth.sub;
    req.body.postId = req.params.postId;
    const comment = yield CommentService.create(req.body);
    if(comment.error)res.json({"msg": "not create"}, 304);
    else{res.json({"msg": "create success"}, 201);}
}

function* updateSingle(req, res) {
    const comment = yield CommentService.update(req.params.id, req.body);
    if (comment.error) res.json({"msg": "not found"}, 404);
    else res.json({msg: "update success"}, 200);
}

function* deleteSingle(req, res) {
    const comment = yield CommentService.deleteSingle(req.params.id);
    if (comment.error) res.json({"msg": "not found"}, 404);
    else res.json({"msg": "delete success"}, 200);
}

function* getSingle(req, res) {
    const comment = yield CommentService.getSingle(req.params.id);
    if (comment.error) res.json({"msg": "not found"}, 404);
    else res.json(comment, 200);
}

function* getCommentsByPostId(req, res) {

}