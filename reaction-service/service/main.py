import pymysql
from flask import Flask, request, json, jsonify
import requests
from flaskext.mysql import MySQL


app = Flask(__name__)
mysql = MySQL()

app.config['MYSQL_DATABASE_USER'] = 'root'
app.config['MYSQL_DATABASE_PASSWORD'] = 'shenzhentagram07user'
app.config['MYSQL_DATABASE_DB'] = 'reactions'
app.config['MYSQL_DATABASE_HOST'] = '35.185.137.69'
mysql.init_app(app)

POST_SERVICE_URL = "http://0.0.0.0:9002/posts"


def increase_react(post_id):
    url = "{0}/{1}/reactions/count".format(POST_SERVICE_URL, post_id)
    r = requests.post(url)
    return r.status_code


def decrease_react(post_id):
    url = "{0}/{1}/reactions/count".format(POST_SERVICE_URL, post_id)
    r = requests.put(url)
    return r.status_code


def get_react(post_id, user_id):
    cursor = mysql.get_db().cursor(pymysql.cursors.DictCursor)
    sql = "SELECT id, reaction, user_id from reactions where post_id = %s AND user_id = %s"
    cursor.execute(sql, (post_id, user_id))
    return jsonify(cursor.fetchone())


def get_reacts(post_id):
    try:
        cursor = mysql.get_db().cursor(pymysql.cursors.DictCursor)
        sql = "SELECT id, reaction, user_id from reactions where post_id = %s"
        cursor.execute(sql, post_id)
        result = cursor.fetchall()
        return jsonify(result)
    except Exception as e:
        return json.dumps({'error': str(e)})


def create_react(post_id, user_id, reaction):
    try:
        # status_code = increase_react(post_id)
        # if status_code != 200:
        #     return '', status_code

        cursor = mysql.get_db().cursor()
        sql = "INSERT INTO reactions(user_id, post_id, reaction) VALUES (%s, %s, %s)"
        cursor.execute(sql, (user_id, post_id, reaction))
        mysql.get_db().commit()

        return get_react(post_id, user_id)
    except Exception as e:
        # decrease_react(post_id)
        return json.dumps({'error': str(e)})


def update_react(post_id, user_id, reaction):
    try:
        cursor = mysql.get_db().cursor()
        sql = "UPDATE reactions SET reaction = %s WHERE post_id = %s AND user_id = %s"
        cursor.execute(sql, (reaction, post_id, user_id))
        mysql.get_db().commit()

        return get_react(post_id, user_id)
    except Exception as e:
        return json.dumps({'error': str(e)})


def delete_react(post_id, user_id):
    try:
        # status_code = decrease_react(post_id)
        # if status_code != 200:
        #     return '', status_code

        cursor = mysql.get_db().cursor()
        sql = "DELETE FROM reactions WHERE post_id = %s AND user_id = %s"
        cursor.execute(sql, (post_id, user_id))
        mysql.get_db().commit()

        return '', 200
    except Exception as e:
        # increase_react(post_id)
        return json.dumps({'error': str(e)})


@app.route('/posts/<int:post_id>/reactions', methods=['GET', 'POST', 'PUT', 'DELETE'])
def reacts(post_id):
    body = request.get_json()
    if body is None:
        body = request.form
    if request.method == 'GET':
        return get_reacts(post_id)
    elif request.method == 'POST':
        user_id = body['user_id']
        reaction = body['reaction']
        return create_react(post_id, user_id, reaction)
    elif request.method == 'PUT':
        user_id = body['user_id']
        reaction = body['reaction']
        return update_react(post_id, user_id, reaction)
    elif request.method == 'DELETE':
        user_id = body['user_id']
        return delete_react(post_id, user_id)

if __name__ == "__main__":
    app.run(host='0.0.0.0', port=9007, debug=True)
