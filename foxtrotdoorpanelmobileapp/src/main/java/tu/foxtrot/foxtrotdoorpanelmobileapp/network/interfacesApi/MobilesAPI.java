package tu.foxtrot.foxtrotdoorpanelmobileapp.network.interfacesApi;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import tu.foxtrot.foxtrotdoorpanelmobileapp.network.responseObjects.Employee;

public interface MobilesAPI {
    @FormUrlEncoded
    @POST("mobiles/{id}/token")
    Call<String> registerMobile(@Path("id") int mobileId, @Field("token") String token);
}


