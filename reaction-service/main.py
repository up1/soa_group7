import pymysql
from flask import Flask, request, json, jsonify
from flaskext.mysql import MySQL


app = Flask(__name__)
mysql = MySQL()

app.config['MYSQL_DATABASE_USER'] = 'root'
app.config['MYSQL_DATABASE_PASSWORD'] = 'mysql'
app.config['MYSQL_DATABASE_DB'] = 'reactions'
app.config['MYSQL_DATABASE_HOST'] = 'localhost'
mysql.init_app(app)


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
        cursor = mysql.get_db().cursor()
        sql = "INSERT INTO reactions(user_id, post_id, reaction) VALUES (%s, %s, %s)"
        cursor.execute(sql, (user_id, post_id, reaction))
        mysql.get_db().commit()

        return get_react(post_id, user_id)
    except Exception as e:
        return json.dumps({'error': str(e)})


def update_react(post_id, user_id, reaction):
    raise NotImplemented()


def delete_react(post_id, user_id):
    raise NotImplemented()


@app.route('/posts/<int:post_id>/reacts', methods=['GET', 'POST', 'PUT', 'DELETE'])
def reacts(post_id):
    if request.method == 'GET':
        return get_reacts(post_id)
    elif request.method == 'POST':
        user_id = request.form['user_id']
        reaction = request.form['reaction']
        return create_react(post_id, user_id, reaction)
    elif request.method == 'PUT':
        user_id = request.form['user_id']
        reaction = request.form['reaction']
        return update_react(post_id, user_id, reaction)
    elif request.method == 'DELETE':
        user_id = request.form['user_id']
        return delete_react(post_id, user_id)

if __name__ == "__main__":
    app.run(port=9007, debug=True)
