package nice.fontaine.overpass.models.response;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import nice.fontaine.overpass.Overpass;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

public class OverpassResponseTest {

    private JsonAdapter<OverpassResponse> adapter;

    @Before
    public void setup() {
        Moshi moshi = Overpass.moshi();
        adapter = moshi.adapter(OverpassResponse.class);
    }

    @Test
    public void shouldReturnString_whenToStringCalled() throws IOException {
        String json = getJson();

        OverpassResponse response = adapter.fromJson(json);

        assert response != null;
        assertThat(response.toString())
                .isEqualTo(
                        "OverpassResponse{" +
                                "version=0.1, " +
                                "generator=OverpassApi API, " +
                                "osm3s=Osm3s{" +
                                    "timestamp_osm_base=2017-12-19T17:11:02Z, " +
                                    "copyright=ODbL}, " +
                                "elements=[]}");
    }

    @Test
    public void shouldContainAllAttributes_whenMapped() throws IOException {
        String json = getJson();

        OverpassResponse response = adapter.fromJson(json);

        assert response != null;
        assertThat(response.version).isEqualTo(0.1);
        assertThat(response.generator).isEqualTo("OverpassApi API");
        assertThat(response.osm3s.timestamp_osm_base).isEqualTo("2017-12-19T17:11:02Z");
        assertThat(response.osm3s.copyright).isEqualTo("ODbL");
        assertThat(response.elements).isEmpty();
    }

    private String getJson() {
        return "{\"version\":\"0.1\", " +
                "\"generator\":\"OverpassApi API\", " +
                "\"osm3s\":{" +
                    "\"timestamp_osm_base\":\"2017-12-19T17:11:02Z\"," +
                    "\"copyright\":\"ODbL\"" +
                "}, " +
                "\"elements\":[]}";
    }
}