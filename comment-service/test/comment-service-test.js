/**
 * Created by: Jiravat
 * Date: 4/12/2017
 * Project: comment-service
 */

'use strict';
global.Promise = require('bluebird');

const models = require('../models');
const Comment = models.Comment;

let chai = require('chai');
let chaiHttp = require('chai-http');
let server = require('../server');
let should = chai.should();

const comments = require('./comments.json');

chai.use(chaiHttp);

Comment.remove({});

describe('Comment', function() {

    describe('/GET Comments => /posts/:postId/comments', () => {
        beforeEach((done) => {
                Comment.remove({}, (err) => {
                    done();
                });
            });

        it('GET all the Comments by postId It should empty', (done) => {

            chai.request(server)
                .get('/posts/1/comments')
                .end((err, res) => {
                    res.should.have.status(200);
                    res.body.should.be.a('array');
                    res.body.length.should.be.eql(0);
                    done();
                });
        });
    });


    describe('/POST Comment => /posts/:postId/comments?userId=:userId', () => {
        beforeEach((done) => {
            Comment.remove({}, (err) => {
                done();
            });
        });

        let comment = {
            "text": "Hello"
        };
        it('POST a Comment', (done) => {

            chai.request(server)
                .post('/posts/1/comments?userId=1')
                .send(comment)
                .end((err, res) => {
                    res.should.have.status(201);
                    res.body.should.be.a('object');
                    res.body.should.have.property('msg');
                    res.body.should.have.property('msg').eql('create success');
                    done();
                });
        });

        it('POST '+comments.length+' Comments', (done) => {
            for(var c in comments) {
                chai.request(server)
                    .post('/posts/1/comments?userId=1')
                    .send(comments[c])
                    .end((err, res) => {
                        res.should.have.status(201);
                        res.body.should.be.a('object');
                        res.body.should.have.property('msg');
                        res.body.should.have.property('msg').eql('create success');

                    });
            }
            done();
        });
    });


    describe('/GET Comments => /posts/:postId/comments', () => {
        it('GET all the Comments by postId it should return 10 comments', (done) => {
            chai.request(server)
                .get('/posts/1/comments')
                .end((err, res) => {
                    res.should.have.status(200);
                    res.body.should.be.a('array');
                    res.body.length.should.be.eql(10);
                    res.body.should.to.have.deep.property('[0].text', comments[0].text);
                    done();
                });
        });
    });


    describe('/PUT Comment => /posts/:postId/comments/:commentId', () => {
        it('Update Comment', (done) => {
            Comment.findOne({}, (err, comment) => {
                    comment.text = "Updated Comment";
                    chai.request(server)
                        .put('/posts/1/comments/'+comment._id)
                        .send(comment)
                        .end((err, res) => {
                            res.should.have.status(200);
                            done();
                        });
                });
        });
    });

    describe('/DELETE Comment => /posts/:postId/comments/:commentId', () => {
        it('Delete Comment', (done) => {
            Comment.findOne({}, (err, comment) => {
                comment.text = "Delete Comment";
                chai.request(server)
                    .delete('/posts/1/comments/'+comment._id)
                    .send(comment)
                    .end((err, res) => {
                        res.should.have.status(200);
                        done();
                    });
            });
        });
    });
});