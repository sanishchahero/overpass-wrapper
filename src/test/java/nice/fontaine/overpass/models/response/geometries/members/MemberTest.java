package nice.fontaine.overpass.models.response.geometries.members;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import nice.fontaine.overpass.Overpass;
import nice.fontaine.overpass.models.query.statements.base.Type;
import org.junit.Before;
import org.junit.Test;
import java.io.IOException;
import java.text.ParseException;

import static org.assertj.core.api.Assertions.assertThat;

public class MemberTest {

    private JsonAdapter<Member> adapter;

    @Before
    public void setup() {
        Moshi moshi = Overpass.moshi();
        adapter = moshi.adapter(Member.class);
    }

    @Test
    public void shouldHaveAllAttributes_whenVerbosityIsMeta() throws IOException, ParseException {
        String json = "{" +
                "\"type\":\"node\"," +
                "\"role\":\"role\"," +
                "\"ref\":1" +
                "}";

        Member member = adapter.fromJson(json);

        assert member != null;
        assertThat(member.type).isEqualTo(Type.NODE);
        assertThat(member.role).isEqualTo("role");
        assertThat(member.ref).isEqualTo(1);
    }
}