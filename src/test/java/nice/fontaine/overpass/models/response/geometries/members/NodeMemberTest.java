package nice.fontaine.overpass.models.response.geometries.members;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import nice.fontaine.overpass.Overpass;
import org.junit.Before;
import org.junit.Test;
import java.io.IOException;
import java.text.ParseException;

import static org.assertj.core.api.Assertions.assertThat;

public class NodeMemberTest {

    private JsonAdapter<NodeMember> adapter;

    @Before
    public void setup() {
        Moshi moshi = Overpass.moshi();
        adapter = moshi.adapter(NodeMember.class);
    }

    @Test
    public void shouldHaveAllAttributes_whenVerbosityIsMeta() throws IOException, ParseException {
        String json = "{" +
                "\"lat\":52.5211479," +
                "\"lon\":13.4085983" +
                "}";

        NodeMember member = adapter.fromJson(json);

        assert member != null;
        assertThat(member.lon).isEqualTo(13.4085983);
        assertThat(member.lat).isEqualTo(52.5211479);
    }
}
