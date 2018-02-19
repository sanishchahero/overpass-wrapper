package nice.fontaine.overpass.models.response.meta;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import nice.fontaine.overpass.Overpass;
import org.junit.Before;
import org.junit.Test;
import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

public class Osm3sTest {

    private JsonAdapter<Osm3s> adapter;

    @Before
    public void setup() {
        Moshi moshi = Overpass.moshi();
        adapter = moshi.adapter(Osm3s.class);
    }

    @Test
    public void shouldBeEqual_whenTimestampAndCopyrightSame() throws IOException {
        Osm3s expected = new Osm3s("1970-01-01 00:00:00", "apache2");
        String json = getJson();

        Osm3s actual = adapter.fromJson(json);

        assert actual != null;
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void shouldContainTimestampAndCopyright_whenMappedFromJson() throws IOException {
        String json = getJson();

        Osm3s actual = adapter.fromJson(json);

        assert actual != null;
        assertThat(actual.timestamp_osm_base).isEqualTo("1970-01-01 00:00:00");
        assertThat(actual.copyright).isEqualTo("apache2");
    }

    @Test
    public void shouldReturnCorrectString_whenStringCalled() throws IOException {
        String expected = getCorrectString();
        String json = getJson();

        Osm3s actual = adapter.fromJson(json);

        assert actual != null;
        assertThat(actual.toString()).isEqualTo(expected);
    }

    public String getJson() {
        return "{\"timestamp_osm_base\":\"1970-01-01 00:00:00\", " +
                "\"copyright\": \"apache2\"}";
    }

    public String getCorrectString() {
        return "Osm3s{timestamp_osm_base=1970-01-01 00:00:00, " +
                "copyright=apache2}";
    }
}