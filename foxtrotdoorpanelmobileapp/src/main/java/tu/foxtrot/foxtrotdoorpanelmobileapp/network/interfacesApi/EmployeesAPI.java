package tu.foxtrot.foxtrotdoorpanelmobileapp.network.interfacesApi;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import tu.foxtrot.foxtrotdoorpanelmobileapp.network.models.Employee;

public interface EmployeesAPI {

    @POST("employees")
    Call<Employee> createEmployee(@Body Employee e1);

    @GET("employees/{id}")
    Call<Employee> getEmployeeById(@Path("id") int employeeId);

    @FormUrlEncoded
    @POST("employees/{id}")
    Call<Employee> updateEmployeeStatus(@Path("id") int employeeId, @Field("status") String status);
}


