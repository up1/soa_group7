/**
 * Default error handler interceptors
 */

export const DefaultErrorHandler = (request, next) => {
  next((response) => {
    if (response.status >= 400) {
      let error = response.body
      console.error(`${request.method} ${error.path} => ${error.status} (${error.error}): ${error.message} (at ${error.service})`)
    }
  })
}
