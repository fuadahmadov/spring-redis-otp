package az.otp.model;

import az.otp.constant.Error;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.Map;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorResponse {
    private String code;
    private String message;
    private Map<String, String> validations;

    public ErrorResponse(Error error, Map<String, String> validations) {
        this.code = error.getCode();
        this.message = error.getMessage();
        this.validations = validations;
    }

    public ErrorResponse(Error error) {
        this.code = error.getCode();
        this.message = error.getMessage();
    }
}
