/**
 * Created by Jiravat on 4/2/2017.
 */
var env = require('node-env-file');

module.exports = {
  load (environment) {
    try {
      environment = environment.toLowerCase();
      env(__dirname + `/../.env.${environment}`);
    } catch(e) {
      try {
        env(__dirname + `/../.env`);
      } catch(e) {
        console.error(e);
        process.exit(1);
      }
    }
  }
};
