/**
 * Created by Jiravat on 3/31/2017.
 * Modified by Chaniwat (chaniwat.meranote@gmail.com) on 4/28/2017.
 */

const Exception = require('../error/Exception');
const Comment = require('../models').Comment;
const _ = require('lodash');

/**
 * Comment Repository private function (helper)
 */
function constructPostPanigation(post, limit, offset) {
    let totalComments = post.comment_count;

    let json = _.omit(post.toJSON(), "comment_count");
    json.size = limit;
    json.number = offset;
    json.numberOfElements = post.comments.length;
    json.totalPages = limit != 0 ? Math.ceil(totalComments / limit) : 1;
    json.totalElements = totalComments;
    json.first = offset == 0;
    json.last = offset == json.totalPages - 1;

    return json;
}

/**
 * Comment Repository
 * Create base on Jiravat codes with modified schema (sub-docs)
 */
class CommentRepository {

    /**
     * Create new comment to post
     */
    *createOne(postId, userId, entity) {
        // Find post first
        let post = yield Comment.findOne({ postId });
        if (!post) {
            // If post not exist create a new one
            post = yield Comment.create({ postId, comment_count: 0 });
        }

        // Add comment to post
        entity.userId = userId;
        let comment = post.comments.create(entity);
        post.comments.push(comment);

        // Increase comment count
        post.comment_count++;
        // Save post to save the comment
        yield post.save()

        // Return the created comment
        return post.comments.id(comment.id);
    }

    /**
     * Update the comment
     * Bypass checking userId by pass null at 3rd argument
     */
    *updateOne(postId, commentId, userId, entity) {
        // Find post
        let post = yield Comment.findOne({ postId });
        if (!post) {
            // No comment in post (because its not create any single comment)
            throw new Exception("No comment in post", 404);
        }

        // Find targeted comment
        let comment = post.comments.id(commentId);
        if(!comment) {
            // No comment found, throwing
            throw new Exception("No comment found", 404);
        }

        // Check owner of comment
        if(userId != null && comment.userId != userId) {
            // comment's user id not match
            throw new Exception("Not authroized to update", 401);
        }

        // Update proerty
        _.extend(comment, entity);

        // Save post to update the comment
        yield post.save()

        // Return the updated comment
        return post.comments.id(commentId);
    }

    /**
     * Get all comments in post
     */
    *getAllByPostId(postId, limit, offset) {
        if(limit != null || offset != null) {
            // Default parameters value (if either limit or offset is set)
            if(limit == null) {
                limit = 10;
            }
            if(offset === null) {
                offset = 0;
            }

            let startOffset = limit * offset;
            let post = yield Comment.findOne({ postId }, {comments : {$slice : [startOffset, limit]}});

            return constructPostPanigation(post, limit, offset);
        } else {
            // Nothing is set, full return
            let post = yield Comment.findOne({ postId });
            return constructPostPanigation(post, 0, 0);
        }
    }

    /**
     * Get comment in post
     */
    *getOne(postId, commentId) {
        // Find post
        let post = yield Comment.findOne({ postId });
        if (!post) {
            // No comment in post (because its not create any single comment)
            return null;
        }

        // Return comment
        return post.comments.id(commentId);
    }

    /**
     * Delete the comments in post (Delete post)
     */
    *deleteAllByPostId(postId) {
        // Find post
        let post = yield Comment.findOne({ postId });

        // Remove post if exist
        if (post) {
            yield post.remove();
        }
    }

    /**
     * Delete comment in post
     * Bypass checking userId by pass null at 3rd argument
     */
    *deleteOne(postId, commentId, userId) {
        // Find post first
        let post = yield Comment.findOne({ postId });
        if (!post) {
            // No comment in post (because its not create any single comment)
            throw new Exception("No comment found", 404);
        }

        // Find targeted comment
        let comment = post.comments.id(commentId);
        if(!comment) {
            // No comment found, throwing
            throw new Exception("No comment found", 404);
        }

        // Check owner of comment
        if(userId != null && comment.userId != userId) {
            // comment's user id not match
            throw new Exception("Not authroized to delete", 401);
        }

        // Remove comment from post
        comment.remove();

        // Decrease comment count
        post.comment_count--;
        // Save post to delete the comment
        yield post.save();

        // Return deleted comment
        return comment;
    }

}

// Singleton the object
var service = new CommentRepository();

module.exports = service;
