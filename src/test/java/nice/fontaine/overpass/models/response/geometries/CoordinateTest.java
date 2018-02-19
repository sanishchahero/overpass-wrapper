package nice.fontaine.overpass.models.response.geometries;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import nice.fontaine.overpass.Overpass;
import org.junit.Before;
import org.junit.Test;
import java.io.IOException;
import java.text.ParseException;

import static org.assertj.core.api.Assertions.assertThat;

public class CoordinateTest {

    private JsonAdapter<Coordinate> adapter;

    @Before public void setup() {
        Moshi moshi = Overpass.moshi();
        adapter = moshi.adapter(Coordinate.class);
    }

    @Test public void shouldHaveLatitudeAndLongitudes_whenDeserialized() throws IOException, ParseException {
        Coordinate coordinate = adapter.fromJson(json());

        assert coordinate != null;
        assertThat(coordinate.lon).isEqualTo(13.4085983);
        assertThat(coordinate.lat).isEqualTo(52.5211479);
    }

    @Test public void shouldBeEqual_whenContainSameLatitudeAndLongitude() throws IOException {
        Coordinate expected = new Coordinate(52.5211479,13.4085983);

        Coordinate coordinate = adapter.fromJson(json());

        assert coordinate != null;
        assertThat(coordinate).isEqualTo(expected);
    }

    private String json() {
        return "{" +
                "\"lat\":52.5211479,\n" +
                "\"lon\":13.4085983\n" +
                "}";
    }
}
