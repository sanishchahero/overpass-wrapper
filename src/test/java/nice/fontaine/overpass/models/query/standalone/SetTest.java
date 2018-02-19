package nice.fontaine.overpass.models.query.standalone;

import org.junit.Test;

import static nice.fontaine.overpass.models.query.standalone.Set.DEFAULT_SET;
import static org.assertj.core.api.Assertions.assertThat;

public class SetTest {

    @Test
    public void getDefaultSetTest() {
        String expected = "node(w)->._";
        String expression = "node(w)";
        Set set = new Set(DEFAULT_SET);
        String actual = set.assign(expression);

        assertThat(actual).isEqualTo(expected);
        assertThat(set.isAssigned()).isTrue();
        assertThat(set.isSuccessor()).isTrue();
    }

    @Test
    public void getASetTest() {
        String expected = "node(w)->.a";
        String expression = "node(w)";
        Set set = new Set("a");

        assertThat(set.isAssigned()).isFalse();
        String actual = set.assign(expression);
        assertThat(actual).isEqualTo(expected);
    }
}
