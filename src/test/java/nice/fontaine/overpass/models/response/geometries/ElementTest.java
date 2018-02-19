package nice.fontaine.overpass.models.response.geometries;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import nice.fontaine.overpass.models.query.statements.base.Type;
import org.junit.Before;
import org.junit.Test;
import java.io.IOException;
import java.text.ParseException;

import static org.assertj.core.api.Assertions.assertThat;

public class ElementTest {

    private JsonAdapter<Element> adapter;

    @Before
    public void setup() {
        Moshi moshi = new Moshi.Builder().build();
        adapter = moshi.adapter(Element.class);
    }

    @Test
    public void shouldHaveAllAttributes_whenVerbosityIsMeta() throws IOException, ParseException {
        String json = "{" +
                "\"type\":\"node\",\n" +
                "\"tags\":{\"amenity\":\"restaurant\"}" +
                "}";

        Element element = adapter.fromJson(json);

        assert element != null;
        assertThat(element.type).isEqualTo(Type.NODE);
        assertThat(element.tags.containsKey("amenity")).isTrue();
        assertThat(element.tags.containsValue("restaurant")).isTrue();
    }
}
