package tu.foxtrot.foxtrotdoorpanelmobileapp.network.interfacesApi;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import tu.foxtrot.foxtrotdoorpanelmobileapp.network.models.Event;
import tu.foxtrot.foxtrotdoorpanelmobileapp.network.responseObjects.Worker;
import tu.foxtrot.foxtrotdoorpanelmobileapp.network.responseObjects.LoginResponse;

public interface WorkersAPI {

    @POST("workers")
    Call<Worker> createWorker(@Body Worker worker);

    @GET("workers/{id}")
    Call<Worker> getWorkerById(@Path("id") int workerId);

    @FormUrlEncoded
    @POST("workers/status")
    Call<String> updateWorkerStatus(@Header("Authorization") String token,
                                    @Field("status") String status);

    @FormUrlEncoded
    @POST("workers/personal-info")
    Call<String> updatePersonalInfo(@Header("Authorization") String token,
                                    @Field("phone") String phone, @Field("email") String email,
                                    @Field("room") String room);

    @FormUrlEncoded
    @POST("workers/photo")
    Call<String> updateWorkerPhoto(@Header("Authorization") String token,
                                  @Field("image") String image);



    @POST("workers/timeslot")
    Call<String> addWorkerTimeslot(@Header("Authorization") String token, @Body Event timeslot);

    @GET("workers/timeslots")
    Call<List<Event>> getTimeslots(@Header("Authorization") String token);

    @FormUrlEncoded
    @HTTP(method = "DELETE",path = "workers/{id}/timeslot", hasBody = true)
    Call<String> removeWorkerTimeslot(@Header("Authorization") String token,
                                      @Path("id") int workerId, @Field("timeslot") String timeslot);


    @FormUrlEncoded
    @POST("workers/login/")
    Call<LoginResponse> login(@Field("email") String email, @Field("password") String password);

    @FormUrlEncoded
    @POST("workers/summary")
    Call<String> updateWorkerSummary(@Header("Authorization") String token,
                                     @Field("summary") String summary);
}

