package com.example.mybin.Membership;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface RetrofitAPI {
    @POST("user/login") // 로그인
    Call<Boolean> signIn(@Body SignInDTO signInDTO);

    @POST("user/save") // 회원가입
    Call<Void> signUp(@Body User user);

    @GET("/user/rank/user") // 사용자 랭킹 데이터
    Call<ResponseBody> getUserRank(@Query("userID")String userID);

    @POST("/user/rank/add-point")   //랭킹 포인트 추가
    Call<ResponseBody> pointUp(@Query("userID")String userID);

    @POST("user/rank/sub-point") //랭킹 포인트를 사용해서 이모티콘 전송
    Call<ResponseBody> pointDown(@Query("userID") String userID);

    @GET("/user/rank/all") // 상위 랭킹 5개의 데이터
    Call<List<User>> getAllUserRank();
    
}
