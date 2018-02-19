package nice.fontaine.overpass.models.response.adapters;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;

import nice.fontaine.overpass.TestUtils;
import nice.fontaine.overpass.models.query.statements.base.Type;
import nice.fontaine.overpass.models.response.geometries.Element;
import nice.fontaine.overpass.models.response.geometries.Node;
import nice.fontaine.overpass.models.response.geometries.Relation;
import nice.fontaine.overpass.models.response.geometries.Way;
import nice.fontaine.overpass.models.response.geometries.members.NodeMember;
import nice.fontaine.overpass.models.response.geometries.members.WayMember;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

public class ElementAdapterTest {

    private JsonAdapter<ElementJson> jsonAdapter;
    private JsonAdapter<Element> elementAdapter;
    private ElementAdapter adapter;

    @Before
    public void setup() {
        Moshi moshi = new Moshi.Builder().build();
        jsonAdapter = moshi.adapter(ElementJson.class);
        elementAdapter = moshi.adapter(Element.class);
        adapter = new ElementAdapter();
    }

    @Test
    public void shouldConvertToElementJson_whenGivenElement() throws Exception {
        String string = elementString();
        Element element = elementAdapter.fromJson(string);

        ElementJson json = adapter.toJson(element);

        assertThat(json.type).isEqualTo(Type.NODE);
        assertThat(json.tags.get("amenity")).isEqualTo("bar");
    }

    @Test
    public void shouldConvertToNode_whenTypeNode() throws ParseException, IOException {
        Date timestamp = TestUtils.toUtcDate("2016-10-11 14:00:37");
        String string = nodeElementString();
        ElementJson json = jsonAdapter.fromJson(string);

        Node node = (Node) adapter.fromJson(json);

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

    @Test
    public void shouldConvertToWay_whenTypeWay() throws ParseException, IOException {
        Date timestamp = TestUtils.toUtcDate("2016-10-11 14:00:37");
        String string = wayElementString();
        ElementJson json = jsonAdapter.fromJson(string);

        Way way = (Way) adapter.fromJson(json);

        assert way != null;
        assertThat(way.type).isEqualTo(Type.WAY);
        assertThat(way.id).isEqualTo(663346172);
        assertThat(way.nodes[0]).isEqualTo(2451469102L);
        assertThat(way.nodes[1]).isEqualTo(2451469108L);
        assertThat(way.tags.containsKey("amenity")).isTrue();
        assertThat(way.tags.containsValue("restaurant")).isTrue();
        assertThat(way.timestamp.getTime()).isEqualTo(timestamp.getTime());
        assertThat(way.version).isEqualTo(7);
        assertThat(way.changeset).isEqualTo(42802721);
        assertThat(way.user).isEqualTo("wheelmap_visitor");
        assertThat(way.uid).isEqualTo(290680);
        assertThat(way.bounds.minlat).isEqualTo(52.5238949);
        assertThat(way.bounds.minlon).isEqualTo(13.4117364);
        assertThat(way.bounds.maxlat).isEqualTo(52.5239248);
        assertThat(way.bounds.maxlon).isEqualTo(13.4122036);
        assertThat(way.geometry[0].lat).isEqualTo(52.5238949);
        assertThat(way.geometry[0].lon).isEqualTo(13.4117364);
        assertThat(way.center.lat).isEqualTo(52.5239098);
        assertThat(way.center.lon).isEqualTo(13.4119700);
    }

    @Test
    public void shouldConvertToRelation_whenTypeRelation() throws ParseException, IOException {
        Date timestamp = TestUtils.toUtcDate("2016-10-11 14:00:37");
        String string = relationElementString();
        ElementJson json = jsonAdapter.fromJson(string);

        Relation relation = (Relation) adapter.fromJson(json);

        assert relation != null;
        assertThat(relation.type).isEqualTo(Type.RELATION_RESPONSE);
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

    private String elementString() {
        return "{\"type\": \"node\", \"tags\": {\"amenity\": \"bar\"}}";
    }

    private String nodeElementString() {
        return "{" +
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
    }

    private String wayElementString() {
        return "{" +
                "\"type\":\"way\"," +
                "\"id\":663346172," +
                "\"nodes\":[2451469102, 2451469108]," +
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
                "\"geometry\": [" +
                "    {\"lat\": 52.5238949, \"lon\": 13.4117364}" +
                "]," +
                "\"center\": {" +
                "    \"lat\": 52.5239098," +
                "    \"lon\": 13.4119700" +
                "}," +
                "\"tags\":{\"amenity\":\"restaurant\"}" +
                "}";
    }

    private String relationElementString() {
        return "{" +
                "\"type\":\"relation\"," +
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
    }
}