package nice.fontaine.overpass.models.query.statements;

import nice.fontaine.overpass.models.query.filters.Tags;
import nice.fontaine.overpass.models.query.settings.Modifier;
import nice.fontaine.overpass.models.query.settings.Verbosity;
import nice.fontaine.overpass.models.query.standalone.Recursion;
import nice.fontaine.overpass.models.query.standalone.Set;
import nice.fontaine.overpass.models.query.statements.base.Statement;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static nice.fontaine.overpass.models.query.statements.ComplexQuery.Geometry.NODE;
import static nice.fontaine.overpass.models.query.statements.ComplexQuery.NO_CONTENT;
import static nice.fontaine.overpass.models.query.statements.ComplexQuery.NO_PREDECESSOR_RECURSION;
import static nice.fontaine.overpass.models.query.statements.ComplexQuery.EX_NO_PREDECESSOR;
import static nice.fontaine.overpass.models.query.statements.base.GeoQuery.EX_NOT_ASSIGNED;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class ComplexQueryTest {

    private static final String HEAD = "[out:json][timeout:10][maxsize:10485760];";
    private static final String TAIL = ";out;";

    @Test
    public void shouldCreateNode_whenNodeGiven() {
        String expected = HEAD + "node[\"name\"=\"Gielgen\"]" + TAIL;

        ComplexQuery actual = new ComplexQuery.Builder()
                .node(new NodeQuery.Builder()
                        .tags(new Tags.Builder()
                                .equal("name", "Gielgen")
                                .build())
                        .build())
                .build();

        assertThat(actual.toQuery()).isEqualTo(expected);
    }

    @Test
    public void shouldCreateWay_whenWayGiven() {
        String expected = HEAD + "way[\"name\"=\"Gielgen\"]" + TAIL;

        ComplexQuery actual = new ComplexQuery.Builder()
                .way(new WayQuery.Builder()
                        .tags(new Tags.Builder()
                                .equal("name", "Gielgen")
                                .build())
                        .build())
                .build();

        assertThat(actual.toQuery()).isEqualTo(expected);
    }

    @Test
    public void shouldCreateRelation_whenRelationGiven() {
        String expected = HEAD + "rel[\"name\"=\"Gielgen\"]" + TAIL;

        ComplexQuery actual = new ComplexQuery.Builder()
                .relation(new RelationQuery.Builder()
                        .tags(new Tags.Builder()
                                .equal("name", "Gielgen")
                                .build())
                        .build())
                .build();

        assertThat(actual.toQuery()).isEqualTo(expected);
    }

    @Test
    public void shouldFail_whenNoContentProvided() {
        assertThatThrownBy(() ->
                new ComplexQuery.Builder()
                        .build()
                        .toQuery())
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(NO_CONTENT);
    }

    @Test public void shouldCreateNode_whenNodeAdded() {
        String expected = HEAD + "node[\"name\"=\"Gielgen\"]" + TAIL;

        ComplexQuery actual = new ComplexQuery.Builder()
                .add(new NodeQuery.Builder()
                        .tags(new Tags.Builder()
                                .equal("name", "Gielgen")
                                .build())
                        .build())
                .build();

        assertThat(actual.toQuery()).isEqualTo(expected);
    }

    @Test public void shouldCreateWay_whenWayAdded() {
        String expected = HEAD + "way[\"name\"=\"Gielgen\"]" + TAIL;

        ComplexQuery actual = new ComplexQuery.Builder()
                .add(new WayQuery.Builder()
                        .tags(new Tags.Builder()
                                .equal("name", "Gielgen")
                                .build())
                        .build())
                .build();

        assertThat(actual.toQuery()).isEqualTo(expected);
    }

    @Test public void shouldCreateRelation_whenRelationAdded() {
        String expected = HEAD + "rel[\"name\"=\"Gielgen\"]" + TAIL;

        ComplexQuery actual = new ComplexQuery.Builder()
                .add(new RelationQuery.Builder()
                        .tags(new Tags.Builder()
                                .equal("name", "Gielgen")
                                .build())
                        .build())
                .build();

        assertThat(actual.toQuery()).isEqualTo(expected);
    }

    @Test public void shouldCreateNodeWayRelation_whenAllAdded() {
        String expected = HEAD + "node[\"name\"=\"Gielgen\"];way[\"name\"=\"Gielgen\"];" +
                "rel[\"name\"=\"Gielgen\"]" + TAIL;
        Tags tags = new Tags.Builder()
                .equal("name", "Gielgen")
                .build();

        ComplexQuery actual = new ComplexQuery.Builder()
                .add(new NodeQuery.Builder()
                        .tags(tags)
                        .build())
                .add(new WayQuery.Builder()
                        .tags(tags)
                        .build())
                .add(new RelationQuery.Builder()
                        .tags(tags)
                        .build())
                .build();

        assertThat(actual.toQuery()).isEqualTo(expected);
    }

    @Test public void shouldCreateNodeWayRelation_whenAllAddedAsList() {
        String expected = HEAD + "node[\"name\"=\"Gielgen\"];way[\"name\"=\"Gielgen\"];" +
                "rel[\"name\"=\"Gielgen\"]" + TAIL;
        Tags tags = new Tags.Builder()
                .equal("name", "Gielgen")
                .build();
        ArrayList<Statement> statements = new ArrayList<Statement>() {{
            add(new NodeQuery.Builder()
                    .tags(tags)
                    .build());
            add(new WayQuery.Builder()
                    .tags(tags)
                    .build());
            add(new RelationQuery.Builder()
                    .tags(tags)
                    .build());
        }};

        ComplexQuery actual = new ComplexQuery.Builder()
                .addAll(statements)
                .build();

        assertThat(actual.toQuery()).isEqualTo(expected);
    }

    @Test public void shouldCreateNodeWithVerbositySkeleton_whenGiven() {
        String expected = HEAD + "node[\"name\"=\"Gielgen\"];out skel;";

        ComplexQuery actual = new ComplexQuery.Builder()
                .node(new NodeQuery.Builder()
                        .tags(new Tags.Builder()
                                .equal("name", "Gielgen")
                                .build())
                        .build())
                .verbosity(Verbosity.SKELETON)
                .build();

        assertThat(actual.toQuery()).isEqualTo(expected);
    }

    @Test public void shouldCreateNodeWithModifierGeom_whenGiven() {
        String expected = HEAD + "node[\"name\"=\"Gielgen\"];out geom;";

        ComplexQuery actual = new ComplexQuery.Builder()
                .node(new NodeQuery.Builder()
                        .tags(new Tags.Builder()
                                .equal("name", "Gielgen")
                                .build())
                        .build())
                .modifier(Modifier.GEOM)
                .build();

        assertThat(actual.toQuery()).isEqualTo(expected);
    }

    @Test public void shouldCreateNodeWithModifierAndVerbosity_whenGiven() {
        String expected = HEAD + "node[\"name\"=\"Gielgen\"];out skel geom;";

        ComplexQuery actual = new ComplexQuery.Builder()
                .node(new NodeQuery.Builder()
                        .tags(new Tags.Builder()
                                .equal("name", "Gielgen")
                                .build())
                        .build())
                .verbosity(Verbosity.SKELETON)
                .modifier(Modifier.GEOM)
                .build();

        assertThat(actual.toQuery()).isEqualTo(expected);
    }

    @Test public void shouldCreateNodeWithModifierVerbositySorted_whenGiven() {
        String expected = HEAD + "node[\"name\"=\"Gielgen\"];out skel geom qt;";

        ComplexQuery actual = new ComplexQuery.Builder()
                .node(new NodeQuery.Builder()
                        .tags(new Tags.Builder()
                                .equal("name", "Gielgen")
                                .build())
                        .build())
                .verbosity(Verbosity.SKELETON)
                .modifier(Modifier.GEOM)
                .sort()
                .build();

        assertThat(actual.toQuery()).isEqualTo(expected);
    }

    @Test public void shouldCreateNodeWithModifierVerbositySortedLimited_whenGiven() {
        String expected = "[out:json][timeout:11][maxsize:122][bbox:10.0,2.0,11.0,5.0];" +
                "node[\"name\"=\"Gielgen\"];out skel geom qt 20;";

        ComplexQuery actual = new ComplexQuery.Builder()
                .node(new NodeQuery.Builder()
                        .tags(new Tags.Builder()
                                .equal("name", "Gielgen")
                                .build())
                        .build())
                .timeout(11)
                .size(122)
                .globalBoundingBox(new double[] {10.0,2.0, 11.0,5.0})
                .limit(20)
                .verbosity(Verbosity.SKELETON)
                .modifier(Modifier.GEOM)
                .sort()
                .build();

        assertThat(actual.toQuery()).isEqualTo(expected);
    }

    @Test public void shouldCreateDifferenceOfTwoNodes_whenCalled() {
        String expected = HEAD + "(node[\"name\"=\"Gielgen\"]; - node[\"name\"=\"Gielgen\"];)" + TAIL;
        Tags tags = new Tags.Builder().equal("name", "Gielgen").build();

        ComplexQuery actual = new ComplexQuery.Builder()
                .difference(
                        new NodeQuery.Builder()
                                .tags(tags)
                                .build(),
                        new NodeQuery.Builder()
                                .tags(tags)
                                .build())
                .build();

        assertThat(actual.toQuery()).isEqualTo(expected);
    }

    @Test public void shouldCreateDifferenceSet_whenCalled() {
        String expected = HEAD + "(node[\"name\"=\"Gielgen\"]; - node[\"name\"=\"Gielgen\"];)->.a" + TAIL;
        Tags tags = new Tags.Builder()
                .equal("name", "Gielgen")
                .build();

        ComplexQuery actual = new ComplexQuery.Builder()
                .difference(
                        new NodeQuery.Builder()
                                .tags(tags)
                                .build(),
                        new NodeQuery.Builder()
                                .tags(tags)
                                .build(),
                        new Set("a"))
                .build();

        assertThat(actual.toQuery()).isEqualTo(expected);
    }

    @Test public void shouldCreateDifferenceSetAndInsertIntoSuccessorNode_whenCalled() {
        String expected = HEAD + "(node[\"name\"=\"Gielgen\"]; - node[\"name\"=\"Gielgen\"];)->.a;" +
                "node.a[\"name\"=\"Gielgen\"]" + TAIL;
        Tags tags = new Tags.Builder()
                .equal("name", "Gielgen")
                .build();
        Set set = new Set("a");

        ComplexQuery actual = new ComplexQuery.Builder()
                .difference(
                        new NodeQuery.Builder()
                            .tags(tags)
                            .build(),
                        new NodeQuery.Builder()
                            .tags(tags)
                            .build(),
                        set)
                .add(new NodeQuery.Builder()
                        .tags(tags)
                        .set(set)
                        .build())
                .build();

        assertThat(actual.toQuery()).isEqualTo(expected);
    }

    @Test public void shouldFail_whenSetHasNoPredecessor() {
        assertThatThrownBy(() -> new ComplexQuery.Builder()
                .add(new NodeQuery.Builder()
                        .set(new Set("a"))
                        .build())
                .build())
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(EX_NOT_ASSIGNED);
    }

    @Test public void shouldCreateUnionOfTwoNodes_whenCalled() {
        String expected = HEAD + "(node[\"name\"=\"Gielgen\"];node[\"name\"=\"Gielgen\"];)" + TAIL;
        Tags tags = new Tags.Builder()
                .equal("name", "Gielgen")
                .build();
        List<Statement> nodes = new ArrayList<Statement>() {{
            add(new NodeQuery.Builder()
                    .tags(tags)
                    .build());
            add(new NodeQuery.Builder()
                    .tags(tags)
                    .build());
        }};

        ComplexQuery actual = new ComplexQuery.Builder()
                .union(nodes)
                .build();

        assertThat(actual.toQuery()).isEqualTo(expected);
    }

    @Test public void shouldCreateUnionSet_whenCalled() {
        String expected = HEAD + "(node[\"name\"=\"Gielgen\"];node[\"name\"=\"Gielgen\"];)->.a" + TAIL;
        Tags tags = new Tags.Builder()
                .equal("name", "Gielgen")
                .build();
        List<Statement> nodes = new ArrayList<Statement>() {{
            add(new NodeQuery.Builder()
                    .tags(tags)
                    .build());
            add(new NodeQuery.Builder()
                    .tags(tags)
                    .build());
        }};

        ComplexQuery actual = new ComplexQuery.Builder()
                .union(nodes, new Set("a"))
                .build();

        assertThat(actual.toQuery()).isEqualTo(expected);
    }

    @Test public void shouldCreateUnionSetAndInsertIntoSuccessorNode_whenCalled() {
        String expected = HEAD + "(node[\"name\"=\"Gielgen\"];node[\"name\"=\"Gielgen\"];)->.a;" +
                "node.a[\"name\"=\"Gielgen\"]" + TAIL;
        Tags tags = new Tags.Builder()
                .equal("name", "Gielgen")
                .build();
        Set set = new Set("a");
        List<Statement> nodes = new ArrayList<Statement>() {{
            add(new NodeQuery.Builder()
                    .tags(tags)
                    .build());
            add(new NodeQuery.Builder()
                    .tags(tags)
                    .build());
        }};

        ComplexQuery actual = new ComplexQuery.Builder()
                .union(nodes, set)
                .add(new NodeQuery.Builder()
                        .tags(tags)
                        .set(set)
                        .build())
                .build();

        assertThat(actual.toQuery()).isEqualTo(expected);
    }

    @Test public void shouldCreateIntersectionOfTwoNodes_whenCalled() {
        String expected = HEAD + "node[\"name\"=\"Gielgen\"]->.ab;node[\"name\"=\"Gielgen\"]->.bc;" +
                "node.ab.bc" + TAIL;
        Tags tags = new Tags.Builder()
                .equal("name", "Gielgen")
                .build();
        List<Statement> nodes = new ArrayList<Statement>() {{
            add(new NodeQuery.Builder()
                    .tags(tags)
                    .build());
            add(new NodeQuery.Builder()
                    .tags(tags)
                    .build());
        }};

        ComplexQuery actual = new ComplexQuery.Builder()
                .intersection(NODE, nodes)
                .build();

        assertThat(actual.toQuery()).isEqualTo(expected);
    }

    @Test public void shouldCreateIntersectionSet_whenCalled() {
        String expected = HEAD + "node[\"name\"=\"Gielgen\"]->.ab;node[\"name\"=\"Gielgen\"]->.bc;" +
                "node.ab.bc->.a" + TAIL;
        Tags tags = new Tags.Builder()
                .equal("name", "Gielgen")
                .build();
        List<Statement> nodes = new ArrayList<Statement>() {{
            add(new NodeQuery.Builder()
                    .tags(tags)
                    .build());
            add(new NodeQuery.Builder()
                    .tags(tags)
                    .build());
        }};

        ComplexQuery actual = new ComplexQuery.Builder()
                .intersection(NODE, nodes, new Set("a"))
                .build();

        assertThat(actual.toQuery()).isEqualTo(expected);
    }

    @Test public void shouldCreateIntersectionSetAndInsertIntoSuccessorNode_whenCalled() {
        String expected = HEAD + "node[\"name\"=\"Gielgen\"]->.ab;node[\"name\"=\"Gielgen\"]->.bc;" +
                "node.ab.bc->.a;node.a[\"name\"=\"Gielgen\"]" + TAIL;
        Tags tags = new Tags.Builder()
                .equal("name", "Gielgen")
                .build();
        Set set = new Set("a");
        List<Statement> nodes = new ArrayList<Statement>() {{
            add(new NodeQuery.Builder()
                    .tags(tags)
                    .build());
            add(new NodeQuery.Builder()
                    .tags(tags)
                    .build());
        }};

        ComplexQuery actual = new ComplexQuery.Builder()
                .intersection(NODE, nodes, set)
                .add(new NodeQuery.Builder()
                        .tags(tags)
                        .set(set)
                        .build())
                .build();

        assertThat(actual.toQuery()).isEqualTo(expected);
    }

    @Test public void shouldCreateRecursion_whenCalled() {
        String expected = HEAD + "node[\"name\"=\"Gielgen\"];<;>;<<;>>;<" + TAIL;

        ComplexQuery actual = new ComplexQuery.Builder()
                .add(new NodeQuery.Builder()
                        .tags(new Tags.Builder()
                                .equal("name", "Gielgen")
                                .build())
                        .build())
                .recurseUp()
                .recurseDown()
                .recurseUpRelation()
                .recurseDownRelation()
                .recursion(Recursion.UP)
                .build();

        assertThat(actual.toQuery()).isEqualTo(expected);
    }

    @Test public void shouldFail_whenRecursionCalledWithoutPredecessor() {
        assertThatThrownBy(() ->
                new ComplexQuery.Builder()
                        .recurseUp()
                        .build())
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(NO_PREDECESSOR_RECURSION);
    }

    @Test public void shouldFail_whenSetInsertedBeforePredecessor() {
        assertThatThrownBy(() ->
                new ComplexQuery.Builder()
                        .union(
                                new Set("a"),
                                new NodeQuery.Builder()
                                        .tags(new Tags.Builder()
                                                .equal("name", "Gielgen")
                                                .build())
                                        .build())
                        .build())
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(EX_NO_PREDECESSOR);
    }

    @Test public void shouldFail_whenRecursionInsertedIntoUnion() {
        assertThatThrownBy(() ->
                new ComplexQuery.Builder()
                        .union(
                                Recursion.UP,
                                new NodeQuery.Builder()
                                        .tags(new Tags.Builder()
                                                .equal("name", "Gielgen")
                                                .build())
                                        .build())
                        .build())
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(EX_NO_PREDECESSOR);
    }

    @Test public void getQueryAddSetWithNodeTest() {
        String expected = HEAD + "node[\"name\"=\"Gielgen\"]->.a;node.a[\"name\"=\"Gielgen\"]" + TAIL;
        Tags tags = new Tags.Builder()
                .equal("name", "Gielgen")
                .build();
        Set set = new Set("a");

        ComplexQuery actual = new ComplexQuery.Builder()
                .add(new NodeQuery.Builder()
                                .tags(tags)
                                .build(),
                        set)
                .add(new NodeQuery.Builder()
                        .tags(tags)
                        .set(set)
                        .build())
                .build();

        assertThat(actual.toQuery()).isEqualTo(expected);
    }
}