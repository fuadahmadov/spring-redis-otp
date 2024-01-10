package az.otp.constant;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum Error {

    INVALID_REQUEST("E1", "Invalid request", BAD_REQUEST),
    OTP_NOT_FOUND("E2", "Otp not found", NOT_FOUND),
    MOBILE_NUMBER_BLOCKED("E3", "Mobile number blocked", BAD_REQUEST),
    INVALID_OTP("E4", "Invalid otp", BAD_REQUEST),
    OTP_SEND_LIMIT_EXCEEDED("E5", "Send otp limit exceed", BAD_REQUEST);

    private final String code;
    private final String message;
    private final HttpStatus status;
}
