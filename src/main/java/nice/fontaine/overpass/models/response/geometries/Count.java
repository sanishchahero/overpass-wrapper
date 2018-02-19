package nice.fontaine.overpass.models.response.geometries;

import nice.fontaine.overpass.models.query.statements.base.Type;

import java.util.Map;

public final class Count extends Element {
    Count(Map<String, String> tags) {
        super(Type.COUNT, tags);
    }

    @Override
    public String toString() {
        return String.format("Count{type=%s, tags=%s}", type, tags);
    }
}
