-- # Notification Data
insert into notifications (id, userId, type_, text, thumbnail, notificationId, checkStatus) values (1, 5, 'reaction', 'p3WA5r8699W138M8061P', 'http://dummyimage.com/120x236.png/dddddd/000000', 1, 1);
insert into notifications (id, userId, type_, text, thumbnail, notificationId, checkStatus) values (2, 6, 'comment', '37skrH8907rRwj2i4tZA', 'http://dummyimage.com/181x116.bmp/5fa2dd/ffffff', 2, 1);
insert into notifications (id, userId, type_, text, thumbnail, notificationId, checkStatus) values (3, 4, 'reaction', '628O8r6x4S3D61jVBEcj', 'http://dummyimage.com/138x232.png/dddddd/000000', 3, 1);
insert into notifications (id, userId, type_, text, thumbnail, notificationId, checkStatus) values (4, 6, 'comment', '7511297R23c41za551fp', 'http://dummyimage.com/241x124.bmp/cc0000/ffffff', 4, 1);
-- # NotificationPost Data
insert into notificationPosts (id, post_id, comment_id) values (1, 1, 1);
insert into notificationPosts (id, post_id, comment_id) values (2, 2, 2);
insert into notificationPosts (id, post_id, comment_id) values (3, 3, 3);
insert into notificationPosts (id, post_id, comment_id) values (4, 4, 4);
--Notification Reaction Data--
insert into notificationReactions (id, reaction_id) values (1, 1);
insert into notificationReactions (id, reaction_id) values (2, 2);
insert into notificationReactions (id, reaction_id) values (3, 3);
insert into notificationReactions (id, reaction_id) values (4, 4);
-- # Notification User Data
insert into notificationUsers (id, userId) values (1, 1);
insert into notificationUsers (id, userId) values (2, 2);
insert into notificationUsers (id, userId) values (3, 3);
insert into notificationUsers (id, userId) values (4, 4);