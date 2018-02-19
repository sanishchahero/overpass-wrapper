package nice.fontaine.overpass.models.response.adapters;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import nice.fontaine.overpass.Overpass;
import nice.fontaine.overpass.models.query.statements.base.Type;
import nice.fontaine.overpass.models.response.geometries.members.Member;
import nice.fontaine.overpass.models.response.geometries.members.NodeMember;
import nice.fontaine.overpass.models.response.geometries.members.WayMember;
import org.junit.Before;
import org.junit.Test;
import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

public class MemberAdapterTest {

    private JsonAdapter<MemberJson> jsonAdapter;
    private JsonAdapter<Member> memberAdapter;
    private MemberAdapter adapter;

    @Before
    public void setup() {
        Moshi moshi = Overpass.moshi();
        jsonAdapter = moshi.adapter(MemberJson.class);
        memberAdapter = moshi.adapter(Member.class);
        adapter = new MemberAdapter();
    }

    @Test
    public void shouldConvertToNodeMember_whenMemberJsonHasLatAndlon() throws IOException {
        String string = nodeMemberString();
        MemberJson json = jsonAdapter.fromJson(string);

        NodeMember member = (NodeMember) adapter.fromJson(json);

        assertThat(member.type).isEqualTo(Type.NODE);
        assertThat(member.ref).isEqualTo(1615531689);
        assertThat(member.role).isEqualTo("stop");
        assertThat(member.lat).isEqualTo(52.5213728);
        assertThat(member.lon).isEqualTo(13.4120602);
    }

    @Test
    public void shouldConvertToWayMember_whenMemberJsonHasGeometry() throws IOException {
        String string = wayMemberString();
        MemberJson json = jsonAdapter.fromJson(string);

        WayMember member = (WayMember) adapter.fromJson(json);

        assertThat(member.type).isEqualTo(Type.WAY);
        assertThat(member.ref).isEqualTo(150389484);
        assertThat(member.role).isEqualTo("platform");
        assertThat(member.geometry.get(0).lat).isEqualTo(52.5216163);
        assertThat(member.geometry.get(0).lon).isEqualTo(13.4116241);
    }

    @Test
    public void shouldConvertToMemberJson_whenGivenMember() throws IOException {
        String string = memberJsonString();
        Member member = memberAdapter.fromJson(string);

        MemberJson json = adapter.toJson(member);

        assertThat(json.type).isNotNull();
        assertThat(json.role).isNotNull();
    }

    private String nodeMemberString() {
        return "{" +
                "\"type\": \"node\"," +
                "\"ref\": 1615531689," +
                "\"role\": \"stop\"," +
                "\"lat\": 52.5213728," +
                "\"lon\": 13.4120602" +
                "}";
    }

    private String wayMemberString() {
        return "{" +
                "\"type\":\"way\"," +
                "\"ref\":150389484," +
                "\"role\":\"platform\"," +
                "\"geometry\":[{\"lat\":52.5216163,\"lon\":13.4116241}]" +
                "}";
    }

    private String memberJsonString() {
        return "{" +
                "\"type\": \"node\"," +
                "\"ref\": 1615531689," +
                "\"role\": \"stop\"" +
                "}";
    }
}
