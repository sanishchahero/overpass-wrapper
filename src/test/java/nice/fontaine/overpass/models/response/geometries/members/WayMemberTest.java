package nice.fontaine.overpass.models.response.geometries.members;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import nice.fontaine.overpass.Overpass;
import nice.fontaine.overpass.models.response.geometries.Coordinate;
import org.junit.Before;
import org.junit.Test;
import java.io.IOException;
import java.text.ParseException;

import static org.assertj.core.api.Assertions.assertThat;

public class WayMemberTest {

    private JsonAdapter<WayMember> adapter;

    @Before
    public void setup() {
        Moshi moshi = Overpass.moshi();
        adapter = moshi.adapter(WayMember.class);
    }

    @Test
    public void shouldHaveAllAttributes_whenVerbosityIsMeta() throws IOException, ParseException {
        String json = "{" +
                "\"geometry\":[{\"lat\":52.5211479,\"lon\":13.4085983}]" +
                "}";

        WayMember member = adapter.fromJson(json);

        assert member != null;
        Coordinate coordinate = member.geometry.get(0);
        assertThat(coordinate.lon).isEqualTo(13.4085983);
        assertThat(coordinate.lat).isEqualTo(52.5211479);
    }
}
