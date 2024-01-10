package az.otp.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Otp {

    private String code;
    private int verifyAttemptCount;
    private int sendAttemptCount;

    public Otp(String code) {
        this.code = code;
    }
}
