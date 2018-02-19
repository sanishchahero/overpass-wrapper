package nice.fontaine.overpass.models.response.geometries;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import nice.fontaine.overpass.Overpass;
import nice.fontaine.overpass.TestUtils;
import nice.fontaine.overpass.models.query.statements.base.Type;
import nice.fontaine.overpass.models.response.geometries.members.NodeMember;
import nice.fontaine.overpass.models.response.geometries.members.WayMember;
import org.junit.Before;
import org.junit.Test;
import java.io.IOException;
import java.text.ParseException;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

public class RelationTest {

    private JsonAdapter<Relation> adapter;

    @Before
    public void setup() {
        Moshi moshi = Overpass.moshi();
        adapter = moshi.adapter(Relation.class);
    }

    @Test
    public void shouldHaveAllAttributes_whenVerbosityIsMetaAndAllModifiers() throws IOException, ParseException {
        Date timestamp = TestUtils.toUtcDate("2016-10-11 14:00:37");
        String json = "{" +
                "\"type\":\"rel\"," +
                "\"id\":663346172," +
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
                "\"center\": {" +
                "    \"lat\": 52.5239098," +
                "    \"lon\": 13.4119700" +
                "}," +
                "\"members\": [{" +
                "    \"type\": \"node\"," +
                "    \"ref\": 1615531689," +
                "    \"role\": \"stop\"," +
                "    \"lat\": 52.5213728," +
                "    \"lon\": 13.4120602" +
                "}," +
                "{" +
                "    \"type\":\"way\"," +
                "    \"ref\":150389484," +
                "    \"role\":\"platform\"," +
                "    \"geometry\":[{\"lat\":52.5216163,\"lon\":13.4116241}]" +
                "}" +
                "]," +
                "\"tags\":{\"amenity\":\"restaurant\"}" +
                "}";

        Relation relation = adapter.fromJson(json);

        assert relation != null;
        assertThat(relation.type).isEqualTo(Type.RELATION);
        assertThat(relation.id).isEqualTo(663346172);
        assertThat(relation.tags.containsKey("amenity")).isTrue();
        assertThat(relation.tags.containsValue("restaurant")).isTrue();
        assertThat(relation.timestamp.getTime()).isEqualTo(timestamp.getTime());
        assertThat(relation.version).isEqualTo(7);
        assertThat(relation.changeset).isEqualTo(42802721);
        assertThat(relation.user).isEqualTo("wheelmap_visitor");
        assertThat(relation.uid).isEqualTo(290680);
        assertThat(relation.bounds.minlat).isEqualTo(52.5238949);
        assertThat(relation.bounds.minlon).isEqualTo(13.4117364);
        assertThat(relation.bounds.maxlat).isEqualTo(52.5239248);
        assertThat(relation.bounds.maxlon).isEqualTo(13.4122036);
        assertThat(relation.center.lat).isEqualTo(52.5239098);
        assertThat(relation.center.lon).isEqualTo(13.4119700);

        NodeMember nodeMember = (NodeMember) relation.members[0];
        assertThat(nodeMember.ref).isEqualTo(1615531689);
        assertThat(nodeMember.type).isEqualTo(Type.NODE);
        assertThat(nodeMember.role).isEqualTo("stop");
        assertThat(nodeMember.lat).isEqualTo(52.5213728);
        assertThat(nodeMember.lon).isEqualTo(13.4120602);

        WayMember member = (WayMember) relation.members[1];
        assertThat(member.ref).isEqualTo(150389484);
        assertThat(member.type).isEqualTo(Type.WAY);
        assertThat(member.role).isEqualTo("platform");
        assertThat(member.geometry.get(0).lat).isEqualTo(52.5216163);
        assertThat(member.geometry.get(0).lon).isEqualTo(13.4116241);
    }
}
