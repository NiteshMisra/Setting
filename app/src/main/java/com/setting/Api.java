package com.setting;

import java.util.Map;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface Api {

    @FormUrlEncoded
    @POST("post/changename")
    retrofit2.Call<UpdateNameResponse> updateName(
            @HeaderMap Map<String, String> headers,
            @Field("userid") String userId,
            @Field("name") String itemId
    );

    @FormUrlEncoded
    @POST("post/changesecurity")
    retrofit2.Call<UpdateNameResponse> changesecurity(
            @HeaderMap Map<String, String> headers,
            @Field("userid") String userId,
            @Field("newpassword") String newPassword,
            @Field("currentpassword") String currentPassword,
            @Field("confirmpassword") String confirmPassword
    );

    @GET("get/get_user_dt")
    retrofit2.Call<GeneralResponse> getUserDetails(
            @HeaderMap Map<String, String> headers,
            @Query("userid") Integer userId
    );

}
