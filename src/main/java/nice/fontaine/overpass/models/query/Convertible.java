package nice.fontaine.overpass.models.query;

public interface Convertible<B> {
    B toBuilder();
    String toQuery();
}
