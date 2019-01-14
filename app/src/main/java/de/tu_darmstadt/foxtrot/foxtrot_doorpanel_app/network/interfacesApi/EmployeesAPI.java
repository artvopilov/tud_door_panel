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

    @POST("employees")
    Call<Employee> createEmployee(@Body Employee e1);

    @GET("employees/{id}")
    Call<Employee> getEmployeeById(@Path("id") int employeeId);

    @FormUrlEncoded
    @POST("employees/{id}/status")
    Call<String> updateEmployeeStatus(@Path("id") int employeeId, @Field("status") String status);

    @FormUrlEncoded
    @POST("employees/{id}/message")
    Call<String> sendEmployeeMessage(@Path("id") int employeeId, @Field("message") String message);

    @FormUrlEncoded
    @POST("employees/{id}/book")
    Call<String> bookEmployeeTimeslot(@Path("id") int employeeId, @Field("timeslot") int timeslot,
                                      @Field("name") String name, @Field("number") String number,
                                      @Field("email") String email, @Field("message") String message);
}


