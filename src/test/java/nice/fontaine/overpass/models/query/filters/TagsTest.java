package nice.fontaine.overpass.models.query.filters;

import nice.fontaine.overpass.models.query.settings.Filter;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class TagsTest {

    @Test public void shouldCreateOneEqualTag_whenOneElementAdded() {
        String expected = "[\"amenity\"=\"post_box\"]";

        Tags actual = new Tags.Builder()
                .add("amenity", "post_box")
                .build();

        assertThat(actual.toQuery()).isEqualTo(expected);
    }

    @Test public void shouldCreateTwoEqualTags_whenTwoElementsAdded() {
        String expected = "[\"amenity\"=\"post_box\"][\"amenity\"=\"bar\"]";

        Tags actual = new Tags.Builder()
                .add("amenity", "post_box")
                .add("amenity", "bar")
                .build();

        assertThat(actual.toQuery()).isEqualTo(expected);
    }

    @Test public void shouldFail_whenAddKeyIsNull() {
        assertThatThrownBy(() -> new Tags.Builder()
                .add(null, "post_box")).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("the key should never be null");
    }

    @Test public void shouldFail_whenDefaultAddValueIsNull() {
        assertThatThrownBy(() -> new Tags.Builder()
                .add("amenity", null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Value can only be null when used with existence check");
    }

    @Test public void shouldFail_whenEqualAddValueIsNull() {
        assertThatThrownBy(() -> new Tags.Builder()
                .add("amenity", null, Filter.EQUAL))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Value can only be null when used with existence check");
    }

    @Test public void shouldCreateOneLikeTag_whenOneElementAdded() {
        String expected = "[\"amenity\"~\"post_box\"]";

        Tags actual = new Tags.Builder()
                .add("amenity", "post_box", Filter.LIKE)
                .build();

        assertThat(actual.toQuery()).isEqualTo(expected);
    }

    @Test public void shouldCreateExistTag_whenCallingIs() {
        String expected = "[\"amenity\"]";

        Tags actual = new Tags.Builder()
                .is("amenity")
                .build();

        assertThat(actual.toQuery()).isEqualTo(expected);
    }

    @Test public void shouldCreateNotExistTag_whenCallingIsNot() {
        String expected = "[!\"amenity\"]";

        Tags actual = new Tags.Builder()
                .isNot("amenity")
                .build();

        assertThat(actual.toQuery()).isEqualTo(expected);
    }

    @Test public void shouldCreateLikeTag_whenCallingLike() {
        String expected = "[\"amenity\"~\"bar\"]";

        Tags actual = new Tags.Builder()
                .like("amenity", "bar")
                .build();

        assertThat(actual.toQuery()).isEqualTo(expected);
    }

    @Test public void shouldCreateNotLikeTag_whenCallingNotLike() {
        String expected = "[\"amenity\"!~\"bar\"]";

        Tags actual = new Tags.Builder()
                .notLike("amenity", "bar")
                .build();

        assertThat(actual.toQuery()).isEqualTo(expected);
    }

    @Test public void shouldCreateILikeTag_whenCallingILike() {
        String expected = "[\"amenity\"~\"bar\", i]";

        Tags actual = new Tags.Builder()
                .ilike("amenity", "bar")
                .build();

        assertThat(actual.toQuery()).isEqualTo(expected);
    }

    @Test public void shouldCreateNotILikeTag_whenCallingNotILike() {
        String expected = "[\"amenity\"!~\"bar\", i]";

        Tags actual = new Tags.Builder()
                .notILike("amenity", "bar")
                .build();

        assertThat(actual.toQuery()).isEqualTo(expected);
    }

    @Test public void shouldCreateEqualTag_whenCallingEqual() {
        String expected = "[\"amenity\"=\"bar\"]";

        Tags actual = new Tags.Builder()
                .equal("amenity", "bar")
                .build();

        assertThat(actual.toQuery()).isEqualTo(expected);
    }

    @Test public void shouldCreateNotEqualTag_whenCallingNotEqual() {
        String expected = "[\"amenity\"!=\"bar\"]";

        Tags actual = new Tags.Builder()
                .notEqual("amenity", "bar")
                .build();

        assertThat(actual.toQuery()).isEqualTo(expected);
    }

    @Test public void shouldCreateEqualTag_whenCallingAddWithEqualFilter() {
        String expected = "[\"amenity\"=\"bar\"]";

        Tags actual = new Tags.Builder()
                .add("amenity", "bar", Filter.EQUAL)
                .build();

        assertThat(actual.toQuery()).isEqualTo(expected);
    }


    @Test public void shouldCreateNotEqualTag_whenCallingAddWithNotEqualFilter() {
        String expected = "[\"amenity\"!=\"bar\"]";

        Tags actual = new Tags.Builder()
                .add("amenity", "bar", Filter.NOT_EQUAL)
                .build();

        assertThat(actual.toQuery()).isEqualTo(expected);
    }

    @Test public void shouldCreateLikeTag_whenCallingAddWithLikeFilter() {
        String expected = "[\"amenity\"~\"bar\"]";

        Tags actual = new Tags.Builder()
                .add("amenity", "bar", Filter.LIKE)
                .build();

        assertThat(actual.toQuery()).isEqualTo(expected);
    }

    @Test public void shouldCreateNotLikeTag_whenCallingAddWithNotLikeFilter() {
        String expected = "[\"amenity\"!~\"bar\"]";

        Tags actual = new Tags.Builder()
                .add("amenity", "bar", Filter.NOT_LIKE)
                .build();

        assertThat(actual.toQuery()).isEqualTo(expected);
    }

    @Test public void shouldCreateExistTag_whenCallingAddWithValueNullAndIsFilter() {
        String expected = "[\"amenity\"]";

        Tags actual = new Tags.Builder()
                .add("amenity", null, Filter.IS)
                .build();

        assertThat(actual.toQuery()).isEqualTo(expected);
    }

    @Test public void shouldCreateExistTag_whenCallingAddWithValueNotNullAndIsFilter() {
        String expected = "[\"amenity\"]";

        Tags actual = new Tags.Builder()
                .add("amenity", "bar", Filter.IS)
                .build();

        assertThat(actual.toQuery()).isEqualTo(expected);
    }

    @Test public void shouldCreateNotExistTag_whenCallingAddWithValueNullAndIsFilter() {
        String expected = "[!\"amenity\"]";

        Tags actual = new Tags.Builder()
                .add("amenity", null, Filter.IS_NOT)
                .build();

        assertThat(actual.toQuery()).isEqualTo(expected);
    }

    @Test public void shouldCreateNotExistTag_whenCallingAddWithValueNotNullAndIsFilter() {
        String expected = "[!\"amenity\"]";

        Tags actual = new Tags.Builder()
                .add("amenity", "bar", Filter.IS_NOT)
                .build();

        assertThat(actual.toQuery()).isEqualTo(expected);
    }
}
