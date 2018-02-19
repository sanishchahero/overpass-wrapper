package nice.fontaine.overpass.models.query.filters;

import nice.fontaine.overpass.models.query.settings.SpatialSearchType;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class SpatialSearchTest {

    @Test
    public void getAroundSearchAreaTest() {
        String expected = "(around:50.0,52.4,13.4)";
        double lat = 52.4;
        double lng = 13.4;
        SpatialSearch spatialSearch = new SpatialSearch(SpatialSearchType.AROUND, lat, lng, 50);
        String actual = spatialSearch.create();
        assertThat(actual).isEqualTo(expected);
    }

    @Test(expected = IllegalArgumentException.class)
    public void failWrongTypeAroundSearchAreaTest() {
        double lat = 52.4;
        double lng = 13.4;
        new SpatialSearch(SpatialSearchType.AREA, lat, lng, 50);
    }

    @Test
    public void getAreaSearchAreaTest() {
        String expected = "area[\"name\"=\"Troisdorf\"]";
        SpatialSearch spatialSearch = new SpatialSearch(SpatialSearchType.AREA, "Troisdorf");
        String actual = spatialSearch.create();
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void getBBoxSearchAreaTest() {
        String expected = "(51.0,7.0,52.0,8.0)";
        double[] coordinates = new double[] {51.0,7.0,52.0,8.0};
        SpatialSearch spatialSearch = new SpatialSearch(SpatialSearchType.BOUNDING_BOX, coordinates);
        String actual = spatialSearch.create();
        assertThat(actual).isEqualTo(expected);
    }

    @Test(expected = IllegalArgumentException.class)
    public void failTooLessCoordinatesBBoxSearchAreaTest() {
        double[] coordinates = new double[] {52.0,7.0,51.0,8.0,6.7};
        new SpatialSearch(SpatialSearchType.BOUNDING_BOX, coordinates);
    }

    @Test(expected = IllegalArgumentException.class)
    public void failSouthBiggerNorthBBoxSearchAreaTest() {
        double[] coordinates = new double[] {52.0,7.0,51.0,8.0};
        new SpatialSearch(SpatialSearchType.BOUNDING_BOX, coordinates);
    }

    @Test(expected = IllegalArgumentException.class)
    public void failWestBiggerEastBBoxSearchAreaTest() {
        double[] coordinates = new double[] {51.0,8.0,52.0,7.0};
        new SpatialSearch(SpatialSearchType.BOUNDING_BOX, coordinates);
    }

    @Test(expected = IllegalArgumentException.class)
    public void failSouthOutOfRangeBBoxSearchAreaTest() {
        double[] coordinates = new double[] {181.0,8.0,52.0,7.0};
        new SpatialSearch(SpatialSearchType.BOUNDING_BOX, coordinates);
    }

    @Test(expected = IllegalArgumentException.class)
    public void failWestOutOfRangeBBoxSearchAreaTest() {
        double[] coordinates = new double[] {81.0,185.0,52.0,7.0};
        new SpatialSearch(SpatialSearchType.BOUNDING_BOX, coordinates);
    }

    @Test(expected = IllegalArgumentException.class)
    public void failNorthOutOfRangeBBoxSearchAreaTest() {
        double[] coordinates = new double[] {81.0,5.0,185.0,7.0};
        new SpatialSearch(SpatialSearchType.BOUNDING_BOX, coordinates);
    }

    @Test(expected = IllegalArgumentException.class)
    public void failEastOutOfRangeBBoxSearchAreaTest() {
        double[] coordinates = new double[] {81.0,5.0,85.0,-297.0};
        new SpatialSearch(SpatialSearchType.BOUNDING_BOX, coordinates);
    }

    @Test
    public void getPolySearchAreaTest() {
        String expected = "(poly:\"51.0 7.0 52.0 8.0 51.0 8.0\")";
        double[][] coordinates = new double[][] {{51.0,7.0},{52.0,8.0},{51.0, 8.0}};
        SpatialSearch spatialSearch = new SpatialSearch(SpatialSearchType.POLYGON, coordinates);
        String actual = spatialSearch.create();
        assertThat(actual).isEqualTo(expected);
    }

    @Test(expected = IllegalArgumentException.class)
    public void failTooLessPairsPolySearchAreaTest() {
        double[][] coordinates = new double[][] {{51.0,7.0},{52.0,8.0}};
        new SpatialSearch(SpatialSearchType.POLYGON, coordinates);
    }

    @Test(expected = IllegalArgumentException.class)
    public void failTripletInsteadOfPairPolySearchAreaTest() {
        double[][] coordinates = new double[][] {{51.0,7.0},{52.0,52.0,8.0},{51.0, 8.0}};
        new SpatialSearch(SpatialSearchType.POLYGON, coordinates);
    }
}
