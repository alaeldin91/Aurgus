package com.aurages.ArestaurantWeb.apiService.retrofit;

import com.aurages.ArestaurantWeb.Model.BranchesModel;
import com.aurages.ArestaurantWeb.Model.Category;
import com.aurages.ArestaurantWeb.Model.InsertOrder;
import com.aurages.ArestaurantWeb.Model.InsertOrderDetails;
import com.aurages.ArestaurantWeb.Model.LoginRequest;
import com.aurages.ArestaurantWeb.Model.LoginResponse;
import com.aurages.ArestaurantWeb.Model.Menu;
import com.aurages.ArestaurantWeb.Model.ModelBill;
import com.aurages.ArestaurantWeb.Model.ModelCatogry;
import com.aurages.ArestaurantWeb.Model.ModelIngradient;
import com.aurages.ArestaurantWeb.Model.ModelMenu;
import com.aurages.ArestaurantWeb.Model.ModelModifiors;
import com.aurages.ArestaurantWeb.Model.ModelOrder;
import com.aurages.ArestaurantWeb.Model.ModelPPrinter;
import com.aurages.ArestaurantWeb.Model.ModelProduct;
import com.aurages.ArestaurantWeb.Model.ModelTables;
import com.aurages.ArestaurantWeb.Model.Model_Floor;
import com.aurages.ArestaurantWeb.Model.Model_PaymentType;
import com.aurages.ArestaurantWeb.Model.inser_order_response;
import com.aurages.ArestaurantWeb.Model.orderDetails_response;

import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface ApiInterface {
    @Headers({"Content-Type: application/json","Accept:application/json"})
    @POST("login")
    Call<LoginResponse> doLogin(@Body LoginRequest loginRequest);
    @Headers({"Content-Type: application/json","Accept:application/json"})
    @GET("getModifier")
    Call<List<ModelModifiors>> getAllModifiers(@Header("Authorization") String token);

    @Headers({"Content-Type: application/json","Accept:application/json"})
    @GET("getTable")
    Call<List<ModelTables>> getAllTaables(@Header("Authorization") String token);
    @Headers({"Content-Type: application/json","Accept:application/json"})
    @GET("getIngrediant")
    Call<List<ModelIngradient>> getGradient(@Header("Authorization") String token);
    @Headers({"Content-Type: application/json","Accept:application/json"})
    @GET("getCategory")
    Call<List<ModelCatogry>> getCategory(@Header("Authorization") String token);
    //@GET("getCategory")
    //Call<ModelCatogry> getCategory(@Header("Authorization") String token);
    @Headers({"Content-Type: application/json","Accept:application/json"})
    @GET("getAllBranch")
    Call<BranchesModel>getbranches(@Header("Authorization") String token);
    @Headers({"Content-Type: application/json","Accept:application/json"})
    @GET("getMenu")
    Call<ModelMenu> getMenus(@Header("Authorization") String token);
    @Headers({"Content-Type: application/json","Accept:application/json"})
    @GET("getFloor")
    Call<Model_Floor>getFloor(@Header("Authorization") String token);
    @Headers({"Content-Type: application/json","Accept:application/json"})
    @GET("getProduct")
    Call<List<ModelProduct>> getProduct(@Header("Authorization") String token);

    @Headers({"Content-Type: application/json","Accept:application/json"})
    @POST("createOrderDetails")
    Call<orderDetails_response> addOrderDetails(@Body InsertOrderDetails orderDetails,@Header("Authorization") String token
);
    @Headers({"Content-Type: application/json","Accept:application/json"})
    @POST("createOrder")
    Call <inser_order_response>addOrder(@Body InsertOrder insertOrder,@Header("Authorization") String token
    );
    @Headers({"Content-Type: application/json","Accept:application/json"})
    @GET("getAllPrinter")
    Call<List<ModelPPrinter>>getprinter(@Header("Authorization") String token);
   void orderget(@Body List<ModelOrder> friendModel, Callback<List<orderDetails_response>> response);
    @GET("getAllPayment")
    Call<List<Model_PaymentType>> getAllPayment(@Header("Authorization") String token);
    @GET("getAllBill")
    Call<List<ModelBill>> getAllBill(@Header("Authorization") String token);

}
