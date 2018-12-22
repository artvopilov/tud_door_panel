package de.tu_darmstadt.foxtrot.foxtrot_doorpanel_app;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface retrieveEmployee {



    @GET("employees")
    Call<List<employee_single>> getIndividualEmployee();
}


