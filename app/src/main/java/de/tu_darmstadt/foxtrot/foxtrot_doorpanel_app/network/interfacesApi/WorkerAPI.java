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
    @GET("workers")
    Call<List<Worker>> getAllWorkers();

    @GET("workers/room/{room}")
    Call<List<Worker>> getAllWorkers(@Path("room") String room);

    @FormUrlEncoded
    @POST("workers/{id}/message")
    Call<String> sendWorkerMessage(@Path("id") int workerId, @Field("text") String message,
                                   @Field("date") String date, @Field("time") String time,
                                   @Field("email") String email, @Field("name") String name);

    @FormUrlEncoded
    @POST("workers/{id}/book")
    Call<String> bookWorkerTimeslot(@Path("id") int workerId, @Field("eventId") int timeslot,
                                    @Field("name") String name, @Field("email") String email,
                                    @Field("phone") String number, @Field("text") String message,
                                    @Field("date") String date, @Field("time") String time);
}


