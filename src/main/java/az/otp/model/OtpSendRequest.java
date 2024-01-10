package az.otp.model;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OtpSendRequest {

    @NotNull
    private String mobileNumber;
    @NotNull
    private String text;
}
