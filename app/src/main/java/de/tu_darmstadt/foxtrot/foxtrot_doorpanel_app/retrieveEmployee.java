package de.tu_darmstadt.foxtrot.foxtrot_doorpanel_app;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface retrieveEmployee {



    @GET("get-employees")
    Call<employee> getIndividualEmployee();
}


