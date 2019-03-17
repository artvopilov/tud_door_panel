package de.tu_darmstadt.foxtrot.foxtrot_doorpanel_app.network.interfacesApi;

import java.util.List;

import de.tu_darmstadt.foxtrot.foxtrot_doorpanel_app.model.Message;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface MessagesAPI {
    @GET("messages/{room}/get")
    Call<List<Message>> getRecentMessages(@Path("room") String roomNumber);
}
