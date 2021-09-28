package code.utils;

import static java.util.Arrays.asList;
import static lombok.AccessLevel.PRIVATE;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Objects;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
@RunWith(Parameterized.class)
public class MathUtilsTest {

    String input;
    String expected;

    @Parameters
    public static Collection<Object[]> data() {
        return asList(new Object[][] {
            { "1.13", "1.15" },
            { "1.16", "1.20" },
            { "1.151", "1.20" },
        });
    }

    @Test
    public void roundUp() {
        final String actual = MathUtils.roundUp(new BigDecimal(input)).toPlainString();

        System.out.printf("%s -> %s ? %s\n", input, expected, Objects.equals(expected, actual));

        Assert.assertEquals(expected, actual);
    }
}