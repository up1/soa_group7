/**
 * Created by Jiravat on 3/31/2017.
 */
const CommentService = require('../services/CommentService');

const models = require('../models');
const Comment = models.Comment;


module.exports = {
    createSingle,
    updateSingle,
    getSingle,
    deleteSingle,
    getCommentsByPostId,
    deleteCommentsByPostId
};

function* createSingle(req, res) {
    //console.log(req.auth.id, req.params.postId);
    // req.body.userId = req.auth.id;
    req.body.userId = req.query.userId;
    req.body.postId = req.params.postId;
    const comment = yield CommentService.create(req.body);
    if(comment.error)res.json({"msg": "not create"}, 304);
    else{ res.status(201).json({"msg": "create success"});}
}

function* updateSingle(req, res) {
    // const userId = req.auth.id;
    const comment = yield CommentService.update(req.params.commentId, req.body);
    if (comment.error) res.json({"msg": "not found"}, 404);
    else res.status(200).json({msg: "update success"});
}

function* deleteSingle(req, res) {
    // const userId = req.auth.id;
    const comment = yield CommentService.deleteSingle(req.params.commentId);
    if (comment.error) res.json({"msg": "not found"}, 404);
    else res.status(200).json({"msg": "delete success"});
}

function* getSingle(req, res) {
    const comment = yield CommentService.getSingle(req.params.commentId);
    if (comment.error) res.json({"msg": "not found"}, 404);
    // else res.json(comment, 200);
    else res.status(200).json(comment);
}

function* getCommentsByPostId(req, res) {
    const postId = req.params.postId;
    const limit = req.query.limit || 10;
    const page = req.query.page || 0;
    try{
        Comment.find({'postId': postId})
            .skip(page*limit)
            .limit(limit)
            .exec(function(err,comments) {
                if (err) {

                return res.json({"msg": "error"}, 404);
            }
            comments = {
                "postId": postId,
                "comments" :comments
            };
            res.status(200).json(comments);
        });
    }
    catch(e){
        return res.json({"msg": "error"}, 404);
    }

}

function* deleteCommentsByPostId(req, res) {
    const postId = req.params.postId;
    try{
        Comment.remove({'postId': postId});
        res.status(200).json({ "msg": "Done"});
    }
    catch(e){
        return res.json({"msg": "error"}, 404);
    }

}