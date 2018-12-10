package tu.foxtrot.foxtrotdoorpanelmobileapp;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface registrationAPI {


    @POST("e1")
    Call<employee> createEmployee(@Body employee e1);
}


