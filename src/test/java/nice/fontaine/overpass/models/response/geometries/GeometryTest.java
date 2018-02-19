package nice.fontaine.overpass.models.response.geometries;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import nice.fontaine.overpass.Overpass;
import nice.fontaine.overpass.TestUtils;
import org.junit.Before;
import org.junit.Test;
import java.io.IOException;
import java.text.ParseException;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

public class GeometryTest {

    private JsonAdapter<Geometry> adapter;

    @Before public void setup() {
        Moshi moshi = Overpass.moshi();
        adapter = moshi.adapter(Geometry.class);
    }

    @Test public void shouldHaveAllAttributes_whenVerbosityIsMeta() throws IOException, ParseException {
        Date timestamp = TestUtils.toUtcDate("2016-10-10 14:00:37");
        String json = "{" +
                "\"id\":663346172,\n" +
                "\"lat\":52.5211479,\n" +
                "\"lon\":13.4085983,\n" +
                "\"timestamp\":\"2016-10-10T14:00:37Z\",\n" +
                "\"version\":7,\n" +
                "\"changeset\":42802721,\n" +
                "\"user\":\"wheelmap_visitor\",\n" +
                "\"uid\":290680" +
                "}";

        Geometry geometry = adapter.fromJson(json);

        assert geometry != null;
        assertThat(geometry.id).isEqualTo(663346172);
        assertThat(geometry.timestamp.getTime()).isEqualTo(timestamp.getTime());
        assertThat(geometry.version).isEqualTo(7);
        assertThat(geometry.changeset).isEqualTo(42802721);
        assertThat(geometry.user).isEqualTo("wheelmap_visitor");
        assertThat(geometry.uid).isEqualTo(290680);
    }
}
