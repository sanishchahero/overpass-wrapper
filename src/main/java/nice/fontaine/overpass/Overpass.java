package nice.fontaine.overpass;

import com.squareup.moshi.Moshi;

import java.util.Date;

import nice.fontaine.overpass.models.query.statements.base.Query;
import nice.fontaine.overpass.models.response.OverpassResponse;
import nice.fontaine.overpass.models.response.adapters.ElementAdapter;
import nice.fontaine.overpass.models.response.adapters.Iso8601Adapter;
import nice.fontaine.overpass.models.response.adapters.MemberAdapter;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.moshi.MoshiConverterFactory;

public final class Overpass {

    private static String url;
    private static OkHttpClient client;

    private Overpass() {
        throw new IllegalStateException("Not to be initialized!");
    }

    public static Call<OverpassResponse> ask(final Query query) {
        OverpassApi api = api();
        return api.ask(query.toQuery());
    }

    public static void url(final String url) {
        Overpass.url = url;
    }

    public static void client(final OkHttpClient client) {
        Overpass.client = client;
    }

    public static OverpassApi api() {
        return new Retrofit.Builder()
                .baseUrl(url == null ? OverpassApi.BASE_URL : url)
                .addConverterFactory(MoshiConverterFactory.create(moshi()))
                .client(client == null ? new OkHttpClient() : client)
                .build()
                .create(OverpassApi.class);
    }

    public static Moshi moshi() {
        return new Moshi.Builder()
                .add(new MemberAdapter())
                .add(new ElementAdapter())
                .add(Date.class, new Iso8601Adapter())
                .build();
    }
}
