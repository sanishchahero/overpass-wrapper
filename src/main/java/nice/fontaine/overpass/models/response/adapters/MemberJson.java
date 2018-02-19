package nice.fontaine.overpass.models.response.adapters;

import nice.fontaine.overpass.models.response.geometries.Coordinate;

import java.util.List;

public class MemberJson {
    public String type;
    public long ref;
    public String role;
    public double lat;
    public double lon;
    public List<Coordinate> geometry;
}
