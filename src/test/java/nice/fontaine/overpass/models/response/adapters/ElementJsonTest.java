package nice.fontaine.overpass.models.response.adapters;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import nice.fontaine.overpass.Overpass;
import nice.fontaine.overpass.models.query.statements.base.Type;
import org.junit.Before;
import org.junit.Test;
import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

public class ElementJsonTest {

    private JsonAdapter<ElementJson> adapter;

    @Before
    public void setup() {
        Moshi moshi = Overpass.moshi();
        adapter = moshi.adapter(ElementJson.class);
    }

    @Test
    public void shouldHaveGeomAttributes_whenModifierIsGeom() throws IOException {
        String json = "{" +
                "\"type\":\"node\",\n" +
                "\"id\":663346172,\n" +
                "\"lat\":52.5211479,\n" +
                "\"lon\":13.4085983,\n" +
                "\"tags\":{\"amenity\":\"restaurant\"}" +
                "}";

        ElementJson node = adapter.fromJson(json);

        assert node != null;
        assertThat(node.type).isEqualTo(Type.NODE);
    }
}