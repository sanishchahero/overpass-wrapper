package nice.fontaine.overpass.utils;

import nice.fontaine.overpass.TestUtils;
import org.junit.Test;
import java.text.ParseException;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

public class Iso8601UtilsTest {

    @Test
    public void shouldConvertToDate_whenIso8601StringInserted() throws ParseException {
        Date expected = TestUtils.toUtcDate("2016-10-11 14:00:37");
        String string = "2016-10-11T14:00:37Z";

        Date actual = Iso8601Utils.parse(string);

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void shouldConvertToString_whenIso8601DateInserted() throws ParseException {
        String expected = "2016-10-11T14:00:37Z";
        Date date = TestUtils.toUtcDate("2016-10-11 14:00:37");

        String actual = Iso8601Utils.format(date);

        assertThat(actual).isEqualTo(expected);
    }
}