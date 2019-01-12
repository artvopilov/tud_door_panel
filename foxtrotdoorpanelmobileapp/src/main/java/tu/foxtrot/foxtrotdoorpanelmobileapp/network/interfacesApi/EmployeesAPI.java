package tu.foxtrot.foxtrotdoorpanelmobileapp.network.interfacesApi;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import tu.foxtrot.foxtrotdoorpanelmobileapp.network.responseObjects.Employee;
import tu.foxtrot.foxtrotdoorpanelmobileapp.network.responseObjects.LoginResponse;

public interface EmployeesAPI {

    @POST("employees")
    Call<Employee> createEmployee(@Body Employee e1);

    @GET("employees/{id}")
    Call<Employee> getEmployeeById(@Path("id") int employeeId);

    @FormUrlEncoded
    @POST("employees/status")
    Call<String> updateEmployeeStatus(@Header("Authorization") String token,
                                      @Field("status") String status);

    @FormUrlEncoded
    @POST("/employees/login/")
    Call<LoginResponse> login(@Field("email") String email, @Field("password") String password);
}


