package tu.foxtrot.foxtrotdoorpanelmobileapp.network.interfacesApi;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import tu.foxtrot.foxtrotdoorpanelmobileapp.network.responseObjects.Worker;
import tu.foxtrot.foxtrotdoorpanelmobileapp.network.responseObjects.LoginResponse;

public interface WorkersAPI {

    @POST("employees")
    Call<Worker> createWorker(@Body Worker worker);

    @GET("employees/{id}")
    Call<Worker> getWorkerById(@Path("id") int employeeId);

    @FormUrlEncoded
    @POST("employees/status")
    Call<String> updateWorkerStatus(@Header("Authorization") String token,
                                    @Field("status") String status);

    @FormUrlEncoded
    @POST("/employees/login/")
    Call<LoginResponse> login(@Field("email") String email, @Field("password") String password);
}


