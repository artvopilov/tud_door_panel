package tu.foxtrot.foxtrotdoorpanelmobileapp.network;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * The type Retrofit client.
 */
public class RetrofitClient {
    private static Retrofit retrofit;
    private static final String BASE_URL = "http://foxtrot-doorpanel-foxtrot-doorpanel-server.7e14.starter-us-west-2.openshiftapps.com/"; // 192.168.2.118

    /**
     * Gets retrofit instance.
     *
     * @return the retrofit instance
     */
    public static Retrofit getRetrofitInstance() {

        if (retrofit == null) {
            Gson gson = new GsonBuilder()
                    .setLenient()
                    .create();

            retrofit = new retrofit2.Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }
        return retrofit;
    }
}
