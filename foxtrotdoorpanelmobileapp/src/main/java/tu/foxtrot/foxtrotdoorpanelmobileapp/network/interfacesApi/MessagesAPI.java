package tu.foxtrot.foxtrotdoorpanelmobileapp.network.interfacesApi;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import tu.foxtrot.foxtrotdoorpanelmobileapp.objects.BookingNotification;
import tu.foxtrot.foxtrotdoorpanelmobileapp.objects.MessageNotification;

public interface MessagesAPI {
    @GET("/messages/")
    Call<List<MessageNotification>> getMessages(@Header("Authorization") String token);

    @GET("/bookings/")
    Call<List<BookingNotification>> getBookings(@Header("Authorization") String token);

    @FormUrlEncoded
    @GET("/messages/send-to-visitor")
    Call<String> sendMessageAnswer(@Header("Authorization") String token,
                                   @Field("message") String message,@Field("date") String date,
                                   @Field("time") String time, @Field("email") String email,
                                   @Field("name") String name,
                                   @Field("previousMessageId") int previousMessageId);
}
