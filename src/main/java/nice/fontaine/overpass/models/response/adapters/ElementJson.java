package nice.fontaine.overpass.models.response.adapters;

import nice.fontaine.overpass.models.response.geometries.Bounds;
import nice.fontaine.overpass.models.response.geometries.Coordinate;
import nice.fontaine.overpass.models.response.geometries.members.Member;

import java.util.Map;

public class ElementJson {
    // element
    public String type;
    public Map<String, String> tags;

    // node
    public double lat;
    public double lon;

    // way
    public long[] nodes;
    public Coordinate[] geometry;

    // relation
    public Member[] members;

    // geometry
    public long id;
    public String timestamp;
    public int version;
    public long changeset;
    public String user;
    public long uid;

    // 2d geometry
    public Bounds bounds;
    public Coordinate center;
}
