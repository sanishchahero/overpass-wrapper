package nice.fontaine.overpass.models.response.geometries;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import nice.fontaine.overpass.Overpass;
import org.junit.Before;
import org.junit.Test;
import java.io.IOException;
import java.text.ParseException;

import static org.assertj.core.api.Assertions.assertThat;

public class Geometry2DTest {

    private JsonAdapter<Geometry2D> adapter;

    @Before
    public void setup() {
        Moshi moshi = Overpass.moshi();
        adapter = moshi.adapter(Geometry2D.class);
    }

    @Test
    public void shouldHaveAllAttributes_whenVerbosityIsMeta() throws IOException, ParseException {
        String json = "{" +
                "\"bounds\":{\n" +
                "    \"minlat\": 52.5238949,\n" +
                "    \"minlon\": 13.4117364,\n" +
                "    \"maxlat\": 52.5239248,\n" +
                "    \"maxlon\": 13.4122036\n" +
                "}," +
                "\"center\": {\n" +
                "    \"lat\": 52.5239098,\n" +
                "    \"lon\": 13.4119700\n" +
                "  }" +
                "}";

        Geometry2D geometry = adapter.fromJson(json);

        assert geometry != null;
        assertThat(geometry.bounds.minlat).isEqualTo(52.5238949);
        assertThat(geometry.bounds.minlon).isEqualTo(13.4117364);
        assertThat(geometry.bounds.maxlat).isEqualTo(52.5239248);
        assertThat(geometry.bounds.maxlon).isEqualTo(13.4122036);
        assertThat(geometry.center.lat).isEqualTo(52.5239098);
        assertThat(geometry.center.lon).isEqualTo(13.4119700);
    }
}
