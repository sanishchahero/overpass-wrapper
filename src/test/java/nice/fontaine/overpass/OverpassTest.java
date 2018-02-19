package nice.fontaine.overpass;

import nice.fontaine.overpass.models.query.settings.Modifier;
import nice.fontaine.overpass.models.query.settings.Verbosity;
import nice.fontaine.overpass.models.query.statements.NodeQuery;
import nice.fontaine.overpass.models.query.statements.RelationQuery;
import nice.fontaine.overpass.models.query.statements.WayQuery;
import nice.fontaine.overpass.models.query.statements.base.Query;
import nice.fontaine.overpass.models.query.statements.base.Type;
import nice.fontaine.overpass.models.response.OverpassResponse;
import nice.fontaine.overpass.models.response.geometries.Element;

import org.junit.Test;
import retrofit2.Call;
import retrofit2.Response;

import java.io.IOException;

import static nice.fontaine.overpass.models.query.statements.base.Type.RELATION_RESPONSE;
import static org.assertj.core.api.Assertions.assertThat;

public class OverpassTest {

    private static final int LIMIT = 10;

    @Test public void shouldNodeAroundMetaGeomResponse_whenRequested() throws IOException {
        NodeQuery node = new NodeQuery.Builder()
                .ilike("name", "alex")
                .around(52.521884, 13.413181, 1000F)
                .sort()
                .limit(LIMIT)
                .verbosity(Verbosity.META)
                .modifier(Modifier.GEOM)
                .build();

        checkResponse(node, Type.NODE, false);
    }

    @Test public void shouldWayAroundSkeletonCountResponse_whenRequested() throws IOException {
        WayQuery node = new WayQuery.Builder()
                .ilike("name", "alex")
                .around(52.521884, 13.413181, 1000F)
                .sort()
                .limit(LIMIT)
                .verbosity(Verbosity.SKELETON)
                .modifier(Modifier.COUNT)
                .build();

        checkResponse(node, Type.COUNT, false);
    }

    @Test public void shouldWayAroundBodyBoundingBoxResponse_whenRequested() throws IOException {
        WayQuery node = new WayQuery.Builder()
                .ilike("name", "alex")
                .around(52.521884, 13.413181, 1000F)
                .sort()
                .limit(LIMIT)
                .verbosity(Verbosity.BODY)
                .modifier(Modifier.BOUNDING_BOX)
                .build();

        checkResponse(node, Type.WAY, false);
    }

    @Test public void shouldRelationAroundTagsCenterResponse_whenRequested() throws IOException {
        RelationQuery node = new RelationQuery.Builder()
                .ilike("name", "alex")
                .around(52.521884, 13.413181, 1000F)
                .sort()
                .limit(LIMIT)
                .verbosity(Verbosity.TAGS)
                .modifier(Modifier.CENTER)
                .build();

        checkResponse(node, RELATION_RESPONSE, false);
    }

    @Test public void shouldRelationAroundIdsCenterResponse_whenRequested() throws IOException {
        RelationQuery node = new RelationQuery.Builder()
                .ilike("name", "alex")
                .around(52.521884, 13.413181, 1000F)
                .sort()
                .limit(LIMIT)
                .verbosity(Verbosity.IDS)
                .modifier(Modifier.CENTER)
                .build();

        checkResponse(node, RELATION_RESPONSE, true);
    }

    private void checkResponse(Query query, String kind, boolean isId) throws IOException {
        Call<OverpassResponse> call = Overpass.ask(query);

        Response<OverpassResponse> response = call.execute();
        assert response.isSuccessful();

        OverpassResponse overpassResponse = response.body();
        assertThat(overpassResponse).isNotNull();

        Element[] elements = overpassResponse.elements;
        assertThat(elements).isNotNull().isNotEmpty();
        assertThat(elements.length).isLessThanOrEqualTo(LIMIT);

        for (Element element : elements) {
            testElement(element, kind, isId);
        }
    }

    private void testElement(Element element, String kind, boolean isId) {
        assertThat(element).isNotNull();
        assertThat(element.type).isEqualTo(kind);
        if (!isId) assertThat(element.tags).isNotNull();
    }
}
