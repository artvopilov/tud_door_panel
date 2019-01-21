package de.tu_darmstadt.foxtrot.foxtrot_doorpanel_app.network.interfacesApi;

import java.util.List;

import de.tu_darmstadt.foxtrot.foxtrot_doorpanel_app.Worker;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface WorkerAPI {
    @GET("employees")
    Call<List<Worker>> getAllWorkers();

    @GET("employees/room/{room}")
    Call<List<Worker>> getAllWorkers(@Path("room") String room);

    @FormUrlEncoded
    @POST("employees/{id}/message")
    Call<String> sendWorkerMessage(@Path("id") int employeeId, @Field("message") String message);

    @FormUrlEncoded
    @POST("employees/{id}/book")
    Call<String> bookWorkerTimeslot(@Path("id") int employeeId, @Field("timeslot") int timeslot,
                                      @Field("name") String name, @Field("phone") String number,
                                      @Field("email") String email, @Field("message") String message);
}


