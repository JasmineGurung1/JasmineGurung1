package com.example.drugstoreskincare.api;

import com.example.drugstoreskincare.api.response.AllProductResponse;
import com.example.drugstoreskincare.api.response.CategoryResponse;
import com.example.drugstoreskincare.api.response.LoginResponse;
import com.example.drugstoreskincare.api.response.RegisterResponse;
import com.example.drugstoreskincare.api.response.SliderResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiService {

    @FormUrlEncoded
    @POST("/ecommerce/api/v1/login")
    Call<LoginResponse> login(@Field("email") String email, @Field("password") String password);

    @FormUrlEncoded
    @POST("/ecommerce/api/v1/register")
    Call<RegisterResponse> register(@Field("name") String names, @Field("email") String email, @Field("password") String password);


    @FormUrlEncoded
    @POST("/api/v1/cart")
    Call<RegisterResponse> addToCart(@Header("Apikey") String apikey, @Field("p_id") int p, @Field("quantity") int q);

    @GET("/ecommerce/api/v1/get-all-products")
    Call<AllProductResponse> getAllProducts();

    @GET("/ecommerce/api/v1/get-categories")
    Call<CategoryResponse> getCategories();

    @GET("/ecommerce/api/v1/slider")
    Call<SliderResponse> getSliders();

    @GET("/ecommerce/api/v1/get-products-by-category")
    Call<AllProductResponse> getProductsByCategory(@Query("c_id") int catid);




}
