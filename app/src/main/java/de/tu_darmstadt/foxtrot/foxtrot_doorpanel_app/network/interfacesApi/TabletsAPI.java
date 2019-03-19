package de.tu_darmstadt.foxtrot.foxtrot_doorpanel_app.network.interfacesApi;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import de.tu_darmstadt.foxtrot.foxtrot_doorpanel_app.network.models.Tablet;

/**
 * The interface Tablets api.
 */
public interface TabletsAPI {
    /**
     * Register tablet call.
     *
     * @param tabletId the tablet id
     * @param token    the token
     * @return the call
     */
    @FormUrlEncoded
    @POST("tablets/{id}/token")
    Call<String> registerTablet(@Path("id") int tabletId, @Field("token") String token);
}
