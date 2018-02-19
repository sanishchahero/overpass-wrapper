package nice.fontaine.overpass.models.response.geometries;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import nice.fontaine.overpass.Overpass;
import nice.fontaine.overpass.TestUtils;
import nice.fontaine.overpass.models.query.statements.base.Type;
import org.junit.Before;
import org.junit.Test;
import java.io.IOException;
import java.text.ParseException;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

public class WayTest {

    private JsonAdapter<Way> adapter;

    @Before
    public void setup() {
        Moshi moshi = Overpass.moshi();
        adapter = moshi.adapter(Way.class);
    }

    @Test
    public void shouldHaveAllAttributes_whenVerbosityIsMetaAndAllModifiers() throws IOException, ParseException {
        Date timestamp = TestUtils.toUtcDate("2016-10-11 14:00:37");
        String json = "{" +
                "\"type\":\"way\"," +
                "\"id\":663346172," +
                "\"nodes\":[2451469102, 2451469108]," +
                "\"timestamp\":\"2016-10-11T14:00:37Z\"," +
                "\"version\":7," +
                "\"changeset\":42802721," +
                "\"user\":\"wheelmap_visitor\"," +
                "\"uid\":290680," +
                "\"bounds\": {" +
                "    \"minlat\": 52.5238949," +
                "    \"minlon\": 13.4117364," +
                "    \"maxlat\": 52.5239248," +
                "    \"maxlon\": 13.4122036" +
                "}," +
                "\"geometry\": [" +
                "    {\"lat\": 52.5238949, \"lon\": 13.4117364}" +
                "]," +
                "\"center\": {" +
                "    \"lat\": 52.5239098," +
                "    \"lon\": 13.4119700" +
                "}," +
                "\"tags\":{\"amenity\":\"restaurant\"}" +
                "}";

        Way way = adapter.fromJson(json);

        assert way != null;
        assertThat(way.type).isEqualTo(Type.WAY);
        assertThat(way.id).isEqualTo(663346172);
        assertThat(way.nodes[0]).isEqualTo(2451469102L);
        assertThat(way.nodes[1]).isEqualTo(2451469108L);
        assertThat(way.tags.containsKey("amenity")).isTrue();
        assertThat(way.tags.containsValue("restaurant")).isTrue();
        assertThat(way.timestamp.getTime()).isEqualTo(timestamp.getTime());
        assertThat(way.version).isEqualTo(7);
        assertThat(way.changeset).isEqualTo(42802721);
        assertThat(way.user).isEqualTo("wheelmap_visitor");
        assertThat(way.uid).isEqualTo(290680);
        assertThat(way.bounds.minlat).isEqualTo(52.5238949);
        assertThat(way.bounds.minlon).isEqualTo(13.4117364);
        assertThat(way.bounds.maxlat).isEqualTo(52.5239248);
        assertThat(way.bounds.maxlon).isEqualTo(13.4122036);
        assertThat(way.geometry[0].lat).isEqualTo(52.5238949);
        assertThat(way.geometry[0].lon).isEqualTo(13.4117364);
        assertThat(way.center.lat).isEqualTo(52.5239098);
        assertThat(way.center.lon).isEqualTo(13.4119700);
    }
}
