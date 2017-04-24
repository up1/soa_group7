/**
 * Created by Jiravat on 4/2/2017.
 */
var env = require('node-env-file');

module.exports = {
  load (environment) {
    // Try to load environment
    try {
      environment = environment.toLowerCase();
      env(__dirname + `/../.env.${environment}`);
      console.log(`use env: .env.${environment}`);
    } catch(e) {
      // Fallback to default
      try {
        console.log(`${environment}.env not found, fallback to default (.env)`);
        env(__dirname + `/../.env`);
      } catch(e) {
        // Can't load .env, exit
        console.log(`Default .env not found, exiting (please edit .env.example to .env)`);
        console.error(e);
        process.exit(1);
      }
    }
  }
};
