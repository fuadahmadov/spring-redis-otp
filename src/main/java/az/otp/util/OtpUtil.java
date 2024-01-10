package az.otp.util;

import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import lombok.experimental.UtilityClass;

@UtilityClass
public class OtpUtil {

    public static String generateOtpCode(Integer length) {
        return ThreadLocalRandom.current()
                .ints(length, 0, 10)
                .mapToObj(Integer::toString)
                .collect(Collectors.joining());
    }

}
