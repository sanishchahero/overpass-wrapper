package nice.fontaine.overpass.models.response.geometries;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;

import nice.fontaine.overpass.Overpass;
import nice.fontaine.overpass.models.query.statements.base.Type;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

public class CountTest {
    private JsonAdapter<Count> adapter;

    @Before public void setup() {
        Moshi moshi = Overpass.moshi();
        adapter = moshi.adapter(Count.class);
    }

    @Test public void shouldContainsCount_whenDeserialized() throws IOException {
        Count count = adapter.fromJson(count());

        assert count != null;
        assertThat(count.type).isEqualTo(Type.COUNT);
        assertThat(count.tags.get("count")).isEqualTo("5");
    }

    private String count() {
        return String.format("{\"type\":\"%s\"," +
                "\"tags\":{\"count\":5}}", Type.COUNT);
    }
}