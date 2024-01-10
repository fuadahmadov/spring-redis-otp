package az.otp.model;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OtpVerifyRequest {

    @NotNull
    private String mobileNumber;
    @NotNull
    private String code;
}
