package az.otp.service;

import az.otp.constant.Error;
import az.otp.exception.OtpException;
import az.otp.model.Otp;
import az.otp.model.OtpSendRequest;
import az.otp.model.OtpVerifyRequest;
import az.otp.util.OtpUtil;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DigestUtils;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OtpService {

    private static final Integer OTP_TTL = 1;
    private static final Integer MOBILE_NUMBER_BLOCK_TTL = 5;
    private static final Integer OTP_CODE_LENGTH = 6;

    private final RedisTemplate<String, Otp> redisTemplate;

    public void send(OtpSendRequest otpSendRequest) {
        var cachedOtp = redisTemplate.opsForValue().get(otpSendRequest.getMobileNumber());
        if (cachedOtp != null) {
            validateMobileNumberBlocked(cachedOtp);
            validateSendOtpLimit(cachedOtp);
            cachedOtp.setSendAttemptCount(cachedOtp.getSendAttemptCount() + 1);
            // call sms client
            saveCacheData(otpSendRequest.getMobileNumber(), cachedOtp, OTP_TTL);
            return;
        }

        // call sms client
        var otpCode = OtpUtil.generateOtpCode(OTP_CODE_LENGTH);
        var hashedOtpCode = DigestUtils.sha1DigestAsHex(otpCode);
        var otp = new Otp(hashedOtpCode);
        saveCacheData(otpSendRequest.getMobileNumber(), otp, OTP_TTL);
    }


    public void verify(OtpVerifyRequest otpVerifyRequest) {
        var otp = redisTemplate.opsForValue().get(otpVerifyRequest.getMobileNumber());
        validateOtpNotFound(otp);
        validateMobileNumberBlocked(otp);
        validateOtpCode(otpVerifyRequest, otp);
        removeCacheData(otpVerifyRequest.getMobileNumber());
    }


    private void validateMobileNumberBlocked(Otp otp) {
        if (otp.getVerifyAttemptCount() > 3) {
            throw new OtpException(Error.MOBILE_NUMBER_BLOCKED);
        }
    }

    private void validateOtpNotFound(Otp otp) {
        if (otp == null) {
            throw new OtpException(Error.OTP_NOT_FOUND);
        }
    }

    private void validateSendOtpLimit(Otp otp) {
        if (otp.getSendAttemptCount() > 3) {
            throw new OtpException(Error.OTP_SEND_LIMIT_EXCEEDED);
        }
    }

    private void validateOtpCode(OtpVerifyRequest otpVerifyRequest, Otp otp) {
        var hashedOtpCode = DigestUtils.sha1DigestAsHex(otpVerifyRequest.getCode());
        if (!otp.getCode().equals(hashedOtpCode)) {
            otp.setVerifyAttemptCount(otp.getVerifyAttemptCount() + 1);
            saveCacheData(otpVerifyRequest.getMobileNumber(), otp, MOBILE_NUMBER_BLOCK_TTL);
            throw new OtpException(Error.INVALID_OTP);
        }
    }

    private void saveCacheData(String key, Otp value, Integer ttl) {
        redisTemplate.opsForValue()
                .set(key, value, ttl, TimeUnit.MINUTES);
    }

    private void removeCacheData(String key) {
        redisTemplate.delete(key);
    }
}
