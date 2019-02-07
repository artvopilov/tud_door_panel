package tu.foxtrot.foxtrotdoorpanelmobileapp.network.interfacesApi;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import tu.foxtrot.foxtrotdoorpanelmobileapp.objects.MessageNotification;
import tu.foxtrot.foxtrotdoorpanelmobileapp.objects.common.Notification;

public interface MessagesAPI {
    @GET("/messages/")
    Call<List<MessageNotification>> getMessages(@Header("Authorization") String token);
}
