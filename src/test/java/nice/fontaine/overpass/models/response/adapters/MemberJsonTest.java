package nice.fontaine.overpass.models.response.adapters;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import nice.fontaine.overpass.models.response.geometries.Coordinate;
import org.junit.Test;
import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

public class MemberJsonTest {

    private Moshi moshi = new Moshi.Builder().build();
    private JsonAdapter<MemberJson> jsonAdapter = moshi.adapter(MemberJson.class);

    @Test
    public void shouldConvertToMemberJson_whenAllAttributesInString() throws IOException {
        Coordinate expected = new Coordinate(52.5216163, 13.4116241);
        String string = getString();

        MemberJson json = jsonAdapter.fromJson(string);

        assert json != null;
        assertThat(json.type).isEqualTo("memberjson");
        assertThat(json.ref).isEqualTo(150389484);
        assertThat(json.role).isEqualTo("platform");
        assertThat(json.lat).isEqualTo(52.5213728);
        assertThat(json.lon).isEqualTo(13.4120602);
        assertThat(json.geometry.get(0)).isEqualTo(expected);
    }

    private String getString() {
        return "{" +
                "\"type\":\"memberjson\"," +
                "\"ref\":150389484," +
                "\"role\":\"platform\"," +
                "\"lat\": 52.5213728," +
                "\"lon\": 13.4120602," +
                "\"geometry\":[{\"lat\":52.5216163,\"lon\":13.4116241}]" +
                "}";
    }
}
