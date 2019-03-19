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

/**
 * The interface Worker api.
 */
public interface WorkerAPI {
    /**
     * Gets all workers.
     *
     * @return the all workers
     */
    @GET("workers")
    Call<List<Worker>> getAllWorkers();

    /**
     * Gets all workers.
     *
     * @param room the room
     * @return the all workers
     */
    @GET("workers/room/{room}")
    Call<List<Worker>> getAllWorkers(@Path("room") String room);

    /**
     * Send worker message call.
     *
     * @param workerId the worker id
     * @param message  the message
     * @param date     the date
     * @param time     the time
     * @param email    the email
     * @param name     the name
     * @return the call
     */
    @FormUrlEncoded
    @POST("workers/{id}/message")
    Call<String> sendWorkerMessage(@Path("id") int workerId, @Field("message") String message,
                                   @Field("date") String date, @Field("time") String time,
                                   @Field("email") String email, @Field("name") String name);

    /**
     * Book worker timeslot call.
     *
     * @param workerId the worker id
     * @param timeslot the timeslot
     * @param name     the name
     * @param email    the email
     * @param number   the number
     * @param message  the message
     * @param date     the date
     * @param time     the time
     * @return the call
     */
    @FormUrlEncoded
    @POST("workers/{id}/book")
    Call<String> bookWorkerTimeslot(@Path("id") int workerId, @Field("eventId") int timeslot,
                                    @Field("name") String name, @Field("email") String email,
                                    @Field("phone") String number, @Field("message") String message,
                                    @Field("date") String date, @Field("time") String time);
}


