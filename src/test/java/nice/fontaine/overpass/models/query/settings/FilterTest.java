package nice.fontaine.overpass.models.query.settings;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class FilterTest {

    @Test
    public void isFilterTest() {
        String expected = "";
        String actual = Filter.IS.sign;
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void isNotFilterTest() {
        String expected = "!";
        String actual = Filter.IS_NOT.sign;
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void equalFilterTest() {
        String expected = "\"=\"";
        String actual = Filter.EQUAL.sign;
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void notEqualFilterTest() {
        String expected = "\"!=\"";
        String actual = Filter.NOT_EQUAL.sign;
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void likeFilterTest() {
        String expected = "\"~\"";
        String actual = Filter.LIKE.sign;
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void notLikeFilterTest() {
        String expected = "\"!~\"";
        String actual = Filter.NOT_LIKE.sign;
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void ilikeFilterTest() {
        String expected = "\"~\"";
        String actual = Filter.ILIKE.sign;
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void notILikeFilterTest() {
        String expected = "\"!~\"";
        String actual = Filter.NOT_ILIKE.sign;
        assertThat(actual).isEqualTo(expected);
    }
}
