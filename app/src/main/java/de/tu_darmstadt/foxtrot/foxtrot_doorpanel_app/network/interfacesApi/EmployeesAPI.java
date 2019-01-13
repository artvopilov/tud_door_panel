package de.tu_darmstadt.foxtrot.foxtrot_doorpanel_app.network.interfacesApi;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import de.tu_darmstadt.foxtrot.foxtrot_doorpanel_app.network.models.Employee;

public interface EmployeesAPI {

    @FormUrlEncoded
    @POST("employees/{id}/message")
    Call<String> sendEmployeeMessage(@Path("id") int employeeId, @Field("message") String message);
}


