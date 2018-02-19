package nice.fontaine.overpass.models.query.standalone;

import org.junit.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class RecursionTest {

    @Test
    public void downSetTest() {
        String expected = ">";
        String actual = Recursion.DOWN.create();

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void upSetTest() {
        String expected = "<";
        String actual = Recursion.UP.create();

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void doubleDownSetTest() {
        String expected = ">>";
        String actual = Recursion.DOUBLE_DOWN.create();

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void doubleUpSetTest() {
        String expected = "<<";
        String actual = Recursion.DOUBLE_UP.create();

        assertThat(actual).isEqualTo(expected);
    }
}