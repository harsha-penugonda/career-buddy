package com.careerbuddy.exception;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.io.IOException;
import java.time.Instant;
import java.time.ZoneOffset;
import java.util.Map;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = ApiException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ResponseEntity<Object> handleApiException(WebRequest request, ApiException ex) {
        return respondWithError(request, ex, HttpStatus.valueOf(ex.getStatusCode()));
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<Object> handleAllUncaughtRuntimeException(RuntimeException exception, WebRequest request) {
        log.error("Exception : {}", exception.getMessage());
        return respondWithError(request, exception, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<Object> handleAllUncaughtException(Exception exception, HttpServletRequest request) {
        if (isClientDisconnectException(exception)) {
            log.error("User : {}, disconnected from : {}", request.getRemoteUser(), request.getRequestURI());
            return null;
        }
        WebRequest webRequest = (WebRequest) request;
        return respondWithError(webRequest, exception, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private boolean isClientDisconnectException(Exception exception) {
        return exception.getMessage() != null
                && exception instanceof IOException
                && (exception.getMessage().contains("Broken pipe")
                || exception.getMessage().contains("connection was aborted")
                || exception.getMessage().contains("Client closed connection"));
    }

    public ResponseEntity<Object> respondWithError(WebRequest request, Exception exception, HttpStatus status) {
        return respondWithError(request, exception, status, false);
    }

    public ResponseEntity<Object> respondWithError(
            WebRequest request, Exception exception, HttpStatus status, boolean suppressStackTrace) {
        if (suppressStackTrace) log.error("Exception on request: {}, {}", request, exception.getMessage());
        else
            log.error(
                    "Exception on request: {}, {}, {}",
                    request,
                    exception.getMessage(),
                    ExceptionUtils.getStackTrace(exception));
        return ResponseEntity.status(status)
                .body(Map.of(
                        "timestamp",
                        Instant.now().atOffset(ZoneOffset.UTC).toString(),
                        "status",
                        status.value(),
                        "error",
                        status.getReasonPhrase(),
                        "path",
                        (String) request.getAttribute(
                                "org.springframework.web.servlet.HandlerMapping.pathWithinHandlerMapping", 0),
                        "message",
                        exception.getMessage()));
    }
}
