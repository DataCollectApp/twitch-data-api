package app.datacollect.twitchdata.api.common.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

  @ExceptionHandler(IllegalArgumentException.class)
  public ResponseEntity<Object> handleIllegalArgumentException(
      RuntimeException ex, WebRequest request) {
    final RequestError requestError = new RequestError(ex.getMessage());
    final HttpStatus status = HttpStatus.BAD_REQUEST;
    return handleExceptionInternal(ex, requestError, null, status, request);
  }
}
