package az.otp.exception.handler;

import az.otp.constant.Error;
import az.otp.exception.OtpException;
import az.otp.model.ErrorResponse;
import java.util.HashMap;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class BaseExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> methodArgumentNotValid(MethodArgumentNotValidException ex) {
        var validations = new HashMap<String, String>();
        for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
            validations.put(fieldError.getField(), fieldError.getDefaultMessage());
        }

        var errorResponse = new ErrorResponse(Error.INVALID_REQUEST, validations);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }


    @ExceptionHandler(OtpException.class)
    public ResponseEntity<ErrorResponse> commonException(OtpException ex) {
        var errorResponse = new ErrorResponse(ex.getError());
        return ResponseEntity.status(ex.getError().getStatus()).body(errorResponse);
    }

}
