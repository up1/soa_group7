from flask import Flask, request, json
from flaskext.mysql import MySQL

app = Flask(__name__)
mysql = MySQL()

app.config['MYSQL_DATABASE_USER'] = 'root'
app.config['MYSQL_DATABASE_PASSWORD'] = 'mysql'
app.config['MYSQL_DATABASE_DB'] = 'reactions'
app.config['MYSQL_DATABASE_HOST'] = 'localhost'
mysql.init_app(app)


def get_reacts(post_id):
    raise NotImplemented()


def create_react(post_id, user_id):
    raise NotImplemented()


def update_react(post_id, user_id):
    raise NotImplemented()


def delete_react(post_id, user_id):
    raise NotImplemented()


@app.route('/posts/<int:post_id>/reacts', methods=['GET', 'POST', 'PUT', 'DELETE'])
def reacts(post_id):
    try:
        cursor = mysql.get_db().cursor()

        user_id = request.form['user_id']

        if request.method == 'GET':
            return get_reacts(post_id)
        elif request.method == 'POST':
            return create_react(post_id, user_id)
        elif request.method == 'PUT':
            return update_react(post_id, user_id)
        elif request.method == 'DELETE':
            return delete_react(post_id, user_id)
    except Exception as e:
        return json.dumps({'error': str(e)})


if __name__ == "__main__":
    app.run(port=9007, debug=True)
