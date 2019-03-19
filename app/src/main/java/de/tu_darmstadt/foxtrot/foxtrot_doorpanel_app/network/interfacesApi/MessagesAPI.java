package de.tu_darmstadt.foxtrot.foxtrot_doorpanel_app.network.interfacesApi;

import java.util.List;

import de.tu_darmstadt.foxtrot.foxtrot_doorpanel_app.model.Message;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * The interface Messages api.
 */
public interface MessagesAPI {
    /**
     * Gets recent messages.
     *
     * @param roomNumber the room number
     * @return the recent messages
     */
    @GET("messages/{room}/get")
    Call<List<Message>> getRecentMessages(@Path("room") String roomNumber);
}
