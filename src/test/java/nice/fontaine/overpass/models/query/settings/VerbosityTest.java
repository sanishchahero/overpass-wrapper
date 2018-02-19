package nice.fontaine.overpass.models.query.settings;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class VerbosityTest {

    @Test
    public void bodyVerbosityTest() {
        String expected = "body";
        String actual = Verbosity.BODY.sign;
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void skelVerbosityTest() {
        String expected = "skel";
        String actual = Verbosity.SKELETON.sign;
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void metaVerbosityTest() {
        String expected = "meta";
        String actual = Verbosity.META.sign;
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void idsVerbosityTest() {
        String expected = "ids";
        String actual = Verbosity.IDS.sign;
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void tagsVerbosityTest() {
        String expected = "tags";
        String actual = Verbosity.TAGS.sign;
        assertThat(actual).isEqualTo(expected);
    }
}