/**
 * Created by Chaniwat on 4/28/2017.
 */

class Exception {

  constructor(message, status) {
    this.message = message;
    this.status = status;
    this.timestamp = Date.now();
  }

  toJSON() {
    return {
      message: this.message,
      status: this.status,
      timestamp: this.timestamp
    }
  }

}

module.exports = Exception;
