package nice.fontaine.overpass.models.query.statements;

import nice.fontaine.overpass.models.query.filters.SpatialSearch;
import nice.fontaine.overpass.models.query.filters.Tags;
import nice.fontaine.overpass.models.query.settings.Filter;
import nice.fontaine.overpass.models.query.settings.Modifier;
import nice.fontaine.overpass.models.query.settings.Settings;
import nice.fontaine.overpass.models.query.settings.Verbosity;
import org.junit.Test;

import static nice.fontaine.overpass.models.query.settings.Selection.BACKWARD_NODE;
import static nice.fontaine.overpass.models.query.settings.Selection.BACKWARD_RELATION;
import static nice.fontaine.overpass.models.query.settings.Selection.BACKWARD_WAY;
import static nice.fontaine.overpass.models.query.settings.SpatialSearchType.AREA;
import static nice.fontaine.overpass.models.query.statements.base.GeoQuery.EX_SELECTION_AREA;
import static nice.fontaine.overpass.models.query.statements.base.GeoQuery.EX_WRONG_SCHEMA;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class NodeQueryTest {

    private static final String HEAD = "[out:json][timeout:11][maxsize:10485760];";
    private static final String TAIL = ";out;";

    @Test public void shouldQueryAround_whenCalled() {
        String expected = HEAD + "node[\"name\"=\"Gielgen\"](around:50.0,52.4,13.4)" + TAIL;

        NodeQuery actual = new NodeQuery.Builder()
                .settings(new Settings.Builder().timeout(11))
                .equal("name", "Gielgen")
                .around(52.4, 13.4, 50)
                .build();

        assertThat(actual.toQuery()).isEqualTo(expected);
    }

    @Test public void shouldQueryBoundingBox_whenCalled() {
        String expected = HEAD + "node[\"name\"=\"Gielgen\"](50.6,7.0,50.8,7.3)" + TAIL;

        NodeQuery actual = new NodeQuery.Builder()
                .settings(new Settings.Builder().timeout(11))
                .equal("name", "Gielgen")
                .boundingBox(new double[] { 50.6, 7.0, 50.8, 7.3 })
                .build();

        assertThat(actual.toQuery()).isEqualTo(expected);
    }

    @Test public void shouldQueryPolygon_whenCalled() {
        String expected = HEAD + "node[\"name\"=\"Gielgen\"](poly:\"51.0 7.0 52.0 8.0 51.0 8.0\")" + TAIL;

        NodeQuery actual = new NodeQuery.Builder()
                .settings(new Settings.Builder().timeout(11))
                .equal("name", "Gielgen")
                .polygon(new double[][] {{ 51.0, 7.0 }, { 52.0, 8.0 }, { 51.0, 8.0 }})
                .build();

        assertThat(actual.toQuery()).isEqualTo(expected);
    }

    @Test public void shouldQueryArea_whenCalled() {
        String expected = HEAD + "area[\"name\"=\"Troisdorf\"];node(area)[\"highway\"][\"name\"]" + TAIL;

        NodeQuery actual = new NodeQuery.Builder()
                .settings(new Settings.Builder().timeout(11))
                .tags(new Tags.Builder()
                        .is("highway")
                        .is("name")
                        .build())
                .region(new SpatialSearch(AREA,"Troisdorf"))
                .build();

        assertThat(actual.toQuery()).isEqualTo(expected);
    }

    @Test public void shouldGetAllFilterAndOutputParamsCorrect_whenCalled() {
        String expected =
                "[out:json][timeout:11][maxsize:122];" +
                "node[\"amenity\"]" +
                "[!\"highway\"]" +
                "[\"name\"=\"Gielgen\"]" +
                "[\"name\"!=\"Bonn\"]" +
                "[\"amenity\"~\"bar\"]" +
                "[\"amenity\"!~\"restaurant\"]" +
                "[\"amenity\"~\"bar\", i]" +
                "[\"amenity\"!~\"restaurant\", i]" +
                "[\"name\"=\"Gielgen\"]" +
                "[\"name\"~\"Gielgen\"];" +
                "out skel geom qt 20;";

        NodeQuery actual = new NodeQuery.Builder()
                .timeout(11)
                .size(122)
                .is("amenity")
                .isNot("highway")
                .equal("name", "Gielgen")
                .notEqual("name", "Bonn")
                .like("amenity", "bar")
                .notLike("amenity", "restaurant")
                .ilike("amenity", "bar")
                .notILike("amenity", "restaurant")
                .tag("name", "Gielgen")
                .tag("name", "Gielgen", Filter.LIKE)
                .limit(20)
                .verbosity(Verbosity.SKELETON)
                .modifier(Modifier.GEOM)
                .sort()
                .build();

        assertThat(actual.toQuery()).isEqualTo(expected);
    }

    @Test
    public void shouldFail_whenAreaTestAndThenWayRecursion() {
        assertThatThrownBy(() ->
                new NodeQuery.Builder()
                        .region(new SpatialSearch(AREA, "Troisdorf"))
                        .select(BACKWARD_WAY)
                        .build())
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(EX_SELECTION_AREA);
    }

    @Test
    public void shouldFail_whenWayRecursionAndThenAreaTest() {
        assertThatThrownBy(() ->
                new NodeQuery.Builder()
                        .select(BACKWARD_WAY)
                        .region(new SpatialSearch(AREA, "Troisdorf"))
                        .build())
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(EX_WRONG_SCHEMA);
    }

    @Test
    public void shouldFail_whenAreaTestAndThenRelationRecursion() {
        assertThatThrownBy(() ->
                new NodeQuery.Builder()
                        .region(new SpatialSearch(AREA, "Troisdorf"))
                        .select(BACKWARD_RELATION)
                        .build())
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(EX_SELECTION_AREA);
    }

    @Test
    public void shouldFail_whenRelationRecursionAndThenAreaTest() {
        assertThatThrownBy(() ->
                new NodeQuery.Builder()
                        .select(BACKWARD_RELATION)
                        .region(new SpatialSearch(AREA, "Troisdorf"))
                        .build())
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(EX_WRONG_SCHEMA);
    }

    @Test
    public void shouldFail_whenAreaTestAndThenNodeRecursion() {
        assertThatThrownBy(() ->
                new NodeQuery.Builder()
                        .region(new SpatialSearch(AREA, "Troisdorf"))
                        .select(BACKWARD_NODE)
                        .build())
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(EX_SELECTION_AREA);
    }

    @Test
    public void shouldFail_whenNodeRecursionAndThenAreaTest() {
        assertThatThrownBy(() ->
                new NodeQuery.Builder()
                        .select(BACKWARD_NODE)
                        .region(new SpatialSearch(AREA, "Troisdorf"))
                        .build())
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(EX_WRONG_SCHEMA);
    }
}