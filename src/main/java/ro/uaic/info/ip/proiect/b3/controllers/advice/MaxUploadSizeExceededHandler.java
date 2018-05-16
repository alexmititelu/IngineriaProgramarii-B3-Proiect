package ro.uaic.info.ip.proiect.b3.controllers.advice;

import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

@ControllerAdvice
public class MaxUploadSizeExceededHandler {
    private static final Logger logger = Logger.getLogger(MaxUploadSizeExceededHandler.class);

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    protected void handleFileUploadException(MaxUploadSizeExceededException e) {
        logger.warn("Someone tried to upload a file larger than the maximum allowed size!");
    }
}
