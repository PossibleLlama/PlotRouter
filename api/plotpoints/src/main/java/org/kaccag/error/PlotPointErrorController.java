package org.kaccag.error;

import com.mongodb.MongoClientException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Logger;

@ControllerAdvice
public class PlotPointErrorController extends ResponseEntityExceptionHandler {

    private static final Logger LOGGER = Logger.getLogger(
            PlotPointErrorController.class.getSimpleName());

    @ExceptionHandler({
            IllegalArgumentException.class,
            MongoClientException.class
    })
    public ResponseEntity<Object> processingPlotPointsError(RuntimeException e, WebRequest request) {
        LOGGER.warning("Error handler: processing plot points");
        DisplayedError error = new DisplayedError(
                HttpStatus.BAD_REQUEST,
                e.getLocalizedMessage()
        );
        return new ResponseEntity<Object>(error, error.getStatus());
    }

    @ExceptionHandler({
            MethodArgumentTypeMismatchException.class
    })
    protected ResponseEntity<Object> handleMethodArgumentTypeMismatch(
            MethodArgumentTypeMismatchException e, WebRequest request) {
        LOGGER.warning("Error handler: mismatched arguments");
        DisplayedError error = new DisplayedError(
                HttpStatus.BAD_REQUEST,
                String.format("%s should be of type %s", e.getName(), e.getRequiredType())
        );
        return new ResponseEntity<Object>(error, error.getStatus());
    }

    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(
            NoHandlerFoundException e, HttpHeaders headers, HttpStatus status, WebRequest request) {
        LOGGER.warning("Error handler: no handler hit");
        DisplayedError error = new DisplayedError(
                HttpStatus.NOT_FOUND,
                String.format("No handler for %s %s", e.getHttpMethod(), request.getContextPath())
        );
        return new ResponseEntity<Object>(error, error.getStatus());
    }

    class DisplayedError {
        private final LocalDateTime timestamp;

        private final HttpStatus status;

        private final String error;

        private final String message;

        private final Throwable exception;

        DisplayedError(final HttpStatus status, final String message) {
            this(status, message, null);
        }

        DisplayedError(final HttpStatus status, final String message, final Throwable exception) {
            this.timestamp = LocalDateTime.now();
            this.status = status;
            this.error = status.toString();
            this.message = message;
            this.exception = exception;
        }

        public String getTimestamp() {
            return timestamp.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        }

        public HttpStatus getStatus() {
            return status;
        }

        public String getError() {
            return error;
        }

        public String getMessage() {
            return message;
        }
    }
}
