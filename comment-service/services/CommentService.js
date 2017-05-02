/**
 * Created by Jiravat on 3/31/2017.
 * Modified by Chaniwat (chaniwat.meranote@gmail.com) on 4/28/2017.
 */

const Exception = require('../error/Exception');
const Comment = require('../models').Comment;
const _ = require('lodash');

function constructPostPanigation(postId, post, limit, offset) {
    var json;
    if(!post) {
        json = {
            postId,
            comments: [],
            totalElements: 0,
            numberOfElements: 0
        }
    } else {
        json = _.omit(post.toJSON(), "comment_count");
        json.numberOfElements = post.comments.length;
        json.totalElements = post.comment_count;
    }

    json.size = limit;
    json.number = offset;
    json.totalPages = limit != 0 ? Math.ceil(json.totalElements / limit) : 1;
    json.first = offset == 0;
    json.last = offset == json.totalPages - 1;

    return json;
}

class CommentRepository {

    *createOne(postId, userId, entity) {

        let post = yield Comment.findOne({ postId });
        if (!post) {
            post = yield Comment.create({ postId, comment_count: 0 });
        }

        entity.userId = userId;
        const comment = post.comments.create(entity);
        post.comments.push(comment);

        post.comment_count++;
        yield post.save()

        return post.comments.id(comment.id);
    }

    *updateOne(postId, commentId, userId, entity) {
        const post = yield Comment.findOne({ postId });
        if (!post) {
            throw new Exception("No comment in post", 404);
        }

        const comment = post.comments.id(commentId);
        if(!comment) {
            throw new Exception("No comment found", 404);
        }

        if(userId != null && comment.userId != userId) {
            throw new Exception("Not authroized to update", 401);
        }

        _.extend(comment, entity);

        yield post.save()

        return post.comments.id(commentId);
    }

    *getAllByPostId(postId, limit, offset) {
        if(limit != null || offset != null) {
            if(limit == null) {
                limit = 10;
            }
            if(offset === null) {
                offset = 0;
            }

            const startOffset = limit * offset;
            const post = yield Comment.findOne({ postId }, {comments : {$slice : [startOffset, limit]}});

            return constructPostPanigation(postId, post, limit, offset);
        } else {
            const post = yield Comment.findOne({ postId });
            return constructPostPanigation(postId, post, 0, 0);
        }
    }

    *getOne(postId, commentId) {
        const post = yield Comment.findOne({ postId });
        if (!post) {
            return null;
        }

        return post.comments.id(commentId);
    }

    *deleteAllByPostId(postId) {
        const post = yield Comment.findOne({ postId });

        if (post) {
            yield post.remove();
        }
    }

    *deleteOne(postId, commentId, userId) {
        const post = yield Comment.findOne({ postId });
        if (!post) {
            throw new Exception("No comment found", 404);
        }

        const comment = post.comments.id(commentId);
        if(!comment) {
            throw new Exception("No comment found", 404);
        }

        if(userId != null && comment.userId != userId) {
            throw new Exception("Not authroized to delete", 401);
        }

        comment.remove();

        post.comment_count--;
        yield post.save();

        return comment;
    }

}

var service = new CommentRepository();

module.exports = service;
