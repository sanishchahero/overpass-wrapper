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

public class NodeTest {

    private JsonAdapter<Node> adapter;

    @Before
    public void setup() {
        Moshi moshi = Overpass.moshi();
        adapter = moshi.adapter(Node.class);
    }

    @Test
    public void shouldHaveGeomAttributes_whenModifierIsGeom() throws IOException {
        String json = "{" +
                "\"type\":\"node\"," +
                "\"id\":663346172," +
                "\"lat\":52.5211479," +
                "\"lon\":13.4085983," +
                "\"tags\":{\"amenity\":\"restaurant\"}" +
                "}";

        Node node = adapter.fromJson(json);

        assert node != null;
        assertThat(node.type).isEqualTo(Type.NODE);
    }

    @Test
    public void shouldHaveAllAttributes_whenVerbosityIsMeta() throws IOException, ParseException {
        Date timestamp = TestUtils.toUtcDate("2016-10-11 14:00:37");
        String json = "{" +
                "\"type\":\"node\"," +
                "\"id\":663346172," +
                "\"lat\":52.5211479," +
                "\"lon\":13.4085983," +
                "\"timestamp\":\"2016-10-11T14:00:37Z\"," +
                "\"version\":7," +
                "\"changeset\":42802721," +
                "\"user\":\"wheelmap_visitor\"," +
                "\"uid\":290680," +
                "\"tags\":{\"amenity\":\"restaurant\"}" +
                "}";

        Node node = adapter.fromJson(json);

        assert node != null;
        assertThat(node.type).isEqualTo(Type.NODE);
        assertThat(node.id).isEqualTo(663346172);
        assertThat(node.lat).isEqualTo(52.5211479);
        assertThat(node.lon).isEqualTo(13.4085983);
        assertThat(node.tags.containsKey("amenity")).isTrue();
        assertThat(node.tags.containsValue("restaurant")).isTrue();
        assertThat(node.timestamp.getTime()).isEqualTo(timestamp.getTime());
        assertThat(node.version).isEqualTo(7);
        assertThat(node.changeset).isEqualTo(42802721);
        assertThat(node.user).isEqualTo("wheelmap_visitor");
        assertThat(node.uid).isEqualTo(290680);
    }
}
