package nice.fontaine.overpass.models.query.settings;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class SelectionTest {

    @Test
    public void forwardNodeTest() {
        String expected = "n";
        String actual = Selection.FORWARD_NODE.sign;
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void forwardWayTest() {
        String expected = "w";
        String actual = Selection.FORWARD_WAY.sign;
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void forwardRelationTest() {
        String expected = "r";
        String actual = Selection.FORWARD_RELATION.sign;
        assertThat(actual).isEqualTo(expected);
    }


    @Test
    public void backwardNodeTest() {
        String expected = "bn";
        String actual = Selection.BACKWARD_NODE.sign;
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void backwardWayTest() {
        String expected = "bw";
        String actual = Selection.BACKWARD_WAY.sign;
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void backwardRelationTest() {
        String expected = "br";
        String actual = Selection.BACKWARD_RELATION.sign;
        assertThat(actual).isEqualTo(expected);
    }
}