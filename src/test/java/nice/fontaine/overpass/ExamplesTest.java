package nice.fontaine.overpass;

import nice.fontaine.overpass.models.query.filters.SpatialSearch;
import nice.fontaine.overpass.models.query.filters.Tags;
import nice.fontaine.overpass.models.query.settings.Modifier;
import nice.fontaine.overpass.models.query.settings.Verbosity;
import nice.fontaine.overpass.models.query.standalone.Recursion;
import nice.fontaine.overpass.models.query.statements.*;
import nice.fontaine.overpass.models.query.statements.base.Statement;
import org.junit.Test;
import java.util.ArrayList;

import static nice.fontaine.overpass.models.query.settings.SpatialSearchType.BOUNDING_BOX;
import static org.assertj.core.api.Assertions.assertThat;

public class ExamplesTest {

    private static final String HEAD = "[out:json][timeout:10][maxsize:10485760];";

    @Test public void shouldQueryUnionOfWayWithTagAndDownRecursion_whenCalled() {
        String expected = HEAD + "(way[\"name\"=\"Gielgenstraße\"];>;);out skel;";

        ComplexQuery actual = new ComplexQuery.Builder()
                .union(new WayQuery.Builder()
                                .equal("name", "Gielgenstraße")
                                .build(),
                        Recursion.DOWN)
                .verbosity(Verbosity.SKELETON)
                .build();

        assertThat(actual.toQuery()).isEqualTo(expected);
    }

    @Test public void shouldQueryWithMultipleStatements_whenCalled() {
        String expected = HEAD + "(way[\"amenity\"=\"school\"](50.6,7.0,50.8,7.3);" +
                ">;node[\"amenity\"=\"school\"](50.6,7.0,50.8,7.3););out skel;";
        double[] coords = new double[] {50.6,7.0,50.8,7.3};
        SpatialSearch region = new SpatialSearch(BOUNDING_BOX, coords);
        Tags tags = new Tags.Builder()
                .equal("amenity", "school")
                .build();

        ComplexQuery actual = new ComplexQuery.Builder()
                .union(new ArrayList<Statement>() {{
                    add(new WayQuery.Builder()
                            .tags(tags)
                            .region(region)
                            .build());
                    add(Recursion.DOWN);
                    add(new NodeQuery.Builder()
                            .tags(tags)
                            .region(region)
                            .build());}})
                .verbosity(Verbosity.SKELETON)
                .build();

        assertThat(actual.toQuery()).isEqualTo(expected);
    }

    @Test public void shouldQueryILikeAlexanderplatz_whenCalled() {
        String expected = "[out:json][timeout:10][maxsize:10485760];" +
                "node[\"name\"~\"alex\", i](around:1000.0,52.521884,13.413181);" +
                "out qt 10;";

        NodeQuery node = new NodeQuery.Builder()
                .ilike("name", "alex")
                .around(52.521884, 13.413181, 1000F)
                .sort()
                .limit(10)
                .build();

        assertThat(node.toQuery()).isEqualTo(expected);
    }

    @Test public void shouldFindRoutesBySpecificStationNode_whenCalled() {
        String expected = "[out:json][timeout:10][maxsize:10485760];" +
                "(node[\"name\"~\"U Boddinstraße\", i]" +
                "[\"public_transport\"=\"stop_position\"]" +
                "[\"railway\"=\"stop\"];<;);out geom;";

        ComplexQuery complex = new ComplexQuery.Builder()
                .union(
                        new NodeQuery.Builder()
                                .ilike("name", "U Boddinstraße")
                                .equal("public_transport", "stop_position")
                                .equal("railway", "stop")
                                .build(),
                        Recursion.UP)
                .modifier(Modifier.GEOM)
                .build();

        assertThat(complex.toQuery()).isEqualTo(expected);
    }
}
