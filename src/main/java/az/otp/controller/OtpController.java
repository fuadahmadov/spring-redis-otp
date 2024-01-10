package az.otp.controller;

import az.otp.model.OtpSendRequest;
import az.otp.model.OtpVerifyRequest;
import az.otp.service.OtpService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/otp")
public class OtpController {

    private final OtpService otpService;

    @PostMapping("/send")
    public void send(@Valid @RequestBody OtpSendRequest otpSendRequest) {
        otpService.send(otpSendRequest);
    }

    @PostMapping("/verify")
    public void verify(@Valid @RequestBody OtpVerifyRequest otpVerifyRequest) {
        otpService.verify(otpVerifyRequest);
    }

}

