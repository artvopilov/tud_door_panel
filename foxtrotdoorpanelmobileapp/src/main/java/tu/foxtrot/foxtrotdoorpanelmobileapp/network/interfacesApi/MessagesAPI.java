package tu.foxtrot.foxtrotdoorpanelmobileapp.network.interfacesApi;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import tu.foxtrot.foxtrotdoorpanelmobileapp.objects.BookingNotification;
import tu.foxtrot.foxtrotdoorpanelmobileapp.objects.MessageNotification;

/**
 * The interface Messages api.
 */
public interface MessagesAPI {
    /**
     * Gets messages.
     *
     * @param token the token
     * @return the messages
     */
    @GET("/messages/")
    Call<List<MessageNotification>> getMessages(@Header("Authorization") String token);

    /**
     * Gets bookings.
     *
     * @param token the token
     * @return the bookings
     */
    @GET("/bookings/")
    Call<List<BookingNotification>> getBookings(@Header("Authorization") String token);

    /**
     * Send message answer call.
     *
     * @param token             the token
     * @param message           the message
     * @param date              the date
     * @param time              the time
     * @param email             the email
     * @param name              the name
     * @param previousMessageId the previous message id
     * @return the call
     */
    @FormUrlEncoded
    @POST("/messages/send-to-visitor")
    Call<String> sendMessageAnswer(@Header("Authorization") String token,
                                   @Field("message") String message,@Field("date") String date,
                                   @Field("time") String time, @Field("email") String email,
                                   @Field("name") String name,
                                   @Field("previousMessageId") int previousMessageId);
}
