package code.utils;

import static java.math.RoundingMode.HALF_UP;

import java.math.BigDecimal;
import java.util.Objects;

public final class MathUtils {

    private MathUtils() {}

    /**
     * Rounds up the given number to the nearest 5 cents.
     * 
     * @param num number
     * @return a number rounded up to the nearest 5 cents
     */
    public static BigDecimal roundUp(BigDecimal num) {

        Objects.requireNonNull(num, "num must not be null!");

        final double result = Math.ceil(num.doubleValue() * 20) / 20;

        return new BigDecimal(result+"").setScale(2, HALF_UP);
    }
}