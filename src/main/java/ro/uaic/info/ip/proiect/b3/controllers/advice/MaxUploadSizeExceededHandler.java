package ro.uaic.info.ip.proiect.b3.controllers.advice;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

@ControllerAdvice
public class MaxUploadSizeExceededHandler {
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    protected void handleFileUploadException(MaxUploadSizeExceededException e) {
    }
}
