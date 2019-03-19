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

/**
 * The interface Workers api.
 */
public interface WorkersAPI {

    /**
     * Create worker call.
     *
     * @param worker the worker
     * @return the call
     */
    @POST("workers")
    Call<Worker> createWorker(@Body Worker worker);

    /**
     * Gets worker by id.
     *
     * @param workerId the worker id
     * @return the worker by id
     */
    @GET("workers/{id}")
    Call<Worker> getWorkerById(@Path("id") int workerId);

    /**
     * Update worker status call.
     *
     * @param token  the token
     * @param status the status
     * @return the call
     */
    @FormUrlEncoded
    @POST("workers/status")
    Call<String> updateWorkerStatus(@Header("Authorization") String token,
                                    @Field("status") String status);

    /**
     * Update personal info call.
     *
     * @param token the token
     * @param phone the phone
     * @param email the email
     * @param room  the room
     * @return the call
     */
    @FormUrlEncoded
    @POST("workers/personal-info")
    Call<String> updatePersonalInfo(@Header("Authorization") String token,
                                    @Field("phone") String phone, @Field("email") String email,
                                    @Field("room") String room);

    /**
     * Update worker photo call.
     *
     * @param token the token
     * @param image the image
     * @return the call
     */
    @FormUrlEncoded
    @POST("workers/photo")
    Call<String> updateWorkerPhoto(@Header("Authorization") String token,
                                  @Field("image") String image);


    /**
     * Add worker timeslot call.
     *
     * @param token    the token
     * @param timeslot the timeslot
     * @return the call
     */
    @POST("workers/timeslot")
    Call<String> addWorkerTimeslot(@Header("Authorization") String token, @Body Event timeslot);

    /**
     * Gets timeslots.
     *
     * @param token the token
     * @return the timeslots
     */
    @GET("workers/timeslots")
    Call<List<Event>> getTimeslots(@Header("Authorization") String token);

    /**
     * Remove worker timeslot call.
     *
     * @param token    the token
     * @param timeslot the timeslot
     * @return the call
     */
    @FormUrlEncoded
    @HTTP(method = "DELETE",path = "workers/timeslot", hasBody = true)
    Call<String> removeWorkerTimeslot(@Header("Authorization") String token,
                                      @Field("timeslot") String timeslot);


    /**
     * Login call.
     *
     * @param email    the email
     * @param password the password
     * @return the call
     */
    @FormUrlEncoded
    @POST("workers/login/")
    Call<LoginResponse> login(@Field("email") String email, @Field("password") String password);

    /**
     * Update worker summary call.
     *
     * @param token   the token
     * @param summary the summary
     * @return the call
     */
    @FormUrlEncoded
    @POST("workers/summary")
    Call<String> updateWorkerSummary(@Header("Authorization") String token,
                                     @Field("summary") String summary);
}


