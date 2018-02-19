package nice.fontaine.overpass.models.query.settings;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ModifierTest {

    @Test
    public void bbModifierTest() {
        String expected = "bb";
        String actual = Modifier.BOUNDING_BOX.sign;
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void centerModifierTest() {
        String expected = "center";
        String actual = Modifier.CENTER.sign;
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void geomModifierTest() {
        String expected = "geom";
        String actual = Modifier.GEOM.sign;
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void countModifierTest() {
        String expected = "count";
        String actual = Modifier.COUNT.sign;
        assertThat(actual).isEqualTo(expected);
    }
}