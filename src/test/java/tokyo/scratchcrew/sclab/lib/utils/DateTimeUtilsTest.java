package tokyo.scratchcrew.sclab.lib.utils;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class DateTimeUtilsTest {

    @Test
    public void test_duration() {

        assertEquals(0L, DateTimeUtils.durationDays("2018-01-01 00:00:00.000", "2018-01-01 00:00:00.000",
                        DateTimeUtils.MS_FORMAT_CUSTOM));
        assertEquals(0L, DateTimeUtils.durationDays("2018-01-01 00:00:00.000", "2018-01-01 00:00:00.001",
                        DateTimeUtils.MS_FORMAT_CUSTOM));
        assertEquals(0L, DateTimeUtils.durationDays("2018-01-01 00:00:00.000", "2018-01-01 23:59:59.999",
                        DateTimeUtils.MS_FORMAT_CUSTOM));
        assertEquals(1L, DateTimeUtils.durationDays("2018-01-01 00:00:00.000", "2018-01-02 00:00:00.000",
                        DateTimeUtils.MS_FORMAT_CUSTOM));

        assertEquals(0L, DateTimeUtils.durationDays("2018-01-01 00:00:00.000", "2018-01-01 00:00:00.000",
                        DateTimeUtils.MS_FORMAT_CUSTOM));
        assertEquals(11L, DateTimeUtils.durationDays("2018-01-25 00:00:00.000", "2018-02-05 00:00:00.000",
                        DateTimeUtils.MS_FORMAT_CUSTOM));

        assertEquals(1L, DateTimeUtils.toDateTime("2018-01-01 00:00:00.001", DateTimeUtils.MS_FORMAT_CUSTOM).minus(
                        DateTimeUtils.toDateTime("2018-01-01 00:00:00.000", DateTimeUtils.MS_FORMAT_CUSTOM).getMillis())
                        .getMillis());

        assertEquals(1L, DateTimeUtils.durationMillis("2018-01-01 00:00:00.000", "2018-01-01 00:00:00.001",
                        DateTimeUtils.MS_FORMAT_CUSTOM));
    }

}
