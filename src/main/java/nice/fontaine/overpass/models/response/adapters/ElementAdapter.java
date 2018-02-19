package nice.fontaine.overpass.models.response.adapters;

import com.squareup.moshi.FromJson;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.ToJson;
import nice.fontaine.overpass.models.query.statements.base.Type;
import nice.fontaine.overpass.models.response.geometries.*;

import java.io.IOException;
import java.util.Date;

import static nice.fontaine.overpass.models.query.statements.base.Type.RELATION_RESPONSE;

public class ElementAdapter {

    private Moshi moshi = new Moshi.Builder()
            .add(new MemberAdapter())
            .add(Date.class, new Iso8601Adapter())
            .build();
    private JsonAdapter<Element> elementAdapter = moshi.adapter(Element.class);
    private JsonAdapter<ElementJson> jsonAdapter = moshi.adapter(ElementJson.class);
    private JsonAdapter<Count> countAdapter = moshi.adapter(Count.class);
    private JsonAdapter<Node> nodeAdapter = moshi.adapter(Node.class);
    private JsonAdapter<Way> wayAdapter = moshi.adapter(Way.class);
    private JsonAdapter<Relation> relationAdapter = moshi.adapter(Relation.class);

    @ToJson public ElementJson toJson(Element element) throws IOException {
        String string = elementAdapter.toJson(element);
        return jsonAdapter.fromJson(string);
    }

    @FromJson public Element fromJson(ElementJson json) throws IOException {
        String string = jsonAdapter.toJson(json);
        switch (json.type) {
            case Type.NODE:
                return nodeAdapter.fromJson(string);
            case Type.WAY:
                return wayAdapter.fromJson(string);
            case RELATION_RESPONSE:
                return relationAdapter.fromJson(string);
            case Type.COUNT:
                return countAdapter.fromJson(string);
            default:
                throw new IllegalArgumentException(
                        String.format("type '%s' is not implemented yet.", json.type));
        }
    }
}
