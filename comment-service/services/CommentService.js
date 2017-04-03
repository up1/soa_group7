/**
 * Created by Jiravat on 3/31/2017.
 */


const models = require('../models');

const Comment = models.Comment;

const _ = require('lodash');

module.exports = {
    create,
    update,
    getSingle,
    deleteSingle
};

function* create(entity) {
    const created = yield Comment.create(entity);
    return created.toObject();
}

function* update(id, entity){
    try{
        const comment = yield Comment.findOne({_id: id});
        _.extend(comment, entity);
        yield comment.save();
        return comment.toObject();
    }
    catch(e){
        return {
            error: true,
        };
    }
}

function* getSingle(id) {
    try {
        const comment = yield Comment.findOne({_id: id});
        return comment.toObject();
    } catch (e) {
        return {
            error: true,
        };
    }

}


function* deleteSingle(id) {
    try{
        const comment = yield Comment.findOne({_id: id});
        yield comment.remove();
    }
    catch(e){
        return {
            error: true,
        };
    }
}