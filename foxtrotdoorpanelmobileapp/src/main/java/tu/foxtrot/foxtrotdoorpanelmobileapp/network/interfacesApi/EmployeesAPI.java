package tu.foxtrot.foxtrotdoorpanelmobileapp.network.interfacesApi;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import tu.foxtrot.foxtrotdoorpanelmobileapp.network.models.Employee;
import tu.foxtrot.foxtrotdoorpanelmobileapp.network.models.Event;

public interface EmployeesAPI {

    @POST("employees")
    Call<Employee> createEmployee(@Body Employee e1);

    @GET("employees/{id}")
    Call<Employee> getEmployeeById(@Path("id") int employeeId);

    @FormUrlEncoded
    @POST("employees/{id}/status")
    Call<String> updateEmployeeStatus(@Path("id") int employeeId, @Field("status") String status);

    @POST("employees/{id}/timeslot")
    Call<String> addEmployeeTimeslot(@Path("id") int employeeId, @Body Event timeslot);
}


