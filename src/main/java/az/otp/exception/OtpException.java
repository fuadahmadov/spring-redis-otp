package az.otp.exception;

import az.otp.constant.Error;
import lombok.Getter;

@Getter
public class OtpException extends RuntimeException {

    private final Error error;

    public OtpException(Error error) {
        super(error.getMessage());
        this.error = error;
    }
}