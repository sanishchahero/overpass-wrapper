package nice.fontaine.overpass.models.response.geometries;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;

import nice.fontaine.overpass.Overpass;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.text.ParseException;

import static org.assertj.core.api.Assertions.assertThat;

public class BoundsTest {
    private JsonAdapter<Bounds> adapter;

    @Before public void setup() {
        Moshi moshi = Overpass.moshi();
        adapter = moshi.adapter(Bounds.class);
    }

    @Test public void shouldHaveAttributes_whenDeserialized() throws IOException, ParseException {
        Bounds bounds = adapter.fromJson(json());

        assert bounds != null;
        assertThat(bounds.minlat).isEqualTo(52.0);
        assertThat(bounds.minlon).isEqualTo(13.1);
        assertThat(bounds.maxlat).isEqualTo(52.2);
        assertThat(bounds.maxlon).isEqualTo(13.4);
    }

    @Test public void shouldBeEqual_whenContainSameLatitudeAndLongitude() throws IOException {
        Bounds expected = new Bounds(52.0, 13.1, 52.2, 13.4);

        Bounds bounds = adapter.fromJson(json());

        assert bounds != null;
        assertThat(bounds).isEqualTo(expected);
    }

    private String json() {
        return "{" +
                "\"minlat\":52.0,\n" +
                "\"minlon\":13.1,\n" +
                "\"maxlat\":52.2,\n" +
                "\"maxlon\":13.4\n" +
                "}";
    }
}