package com.example.mybin.HomeActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.example.mybin.Membership.RetrofitAPI;
import com.example.mybin.Membership.RetrofitService;
import com.example.mybin.R;
import java.io.IOException;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class emoticon extends AppCompatActivity {
    private Button emoticonButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emoticon);

        emoticonButton = findViewById(R.id.emoticon_button);
        emoticonButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userID = getUserID();
                if (userID != null) {
                    deductUserID(userID);
                } else {
                    Toast.makeText(emoticon.this, "사용자 ID를 찾을 수 없습니다.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private String getUserID() {
        SharedPreferences sharedPreferences = getSharedPreferences("MyAppPreferences", MODE_PRIVATE);
        return sharedPreferences.getString("userID", null);
    }

    private void deductUserID(String userID) {
        RetrofitService retrofit = new RetrofitService();
        RetrofitAPI pointdown = retrofit.getRetrofit().create(RetrofitAPI.class);

        Call<ResponseBody> call = pointdown.pointDown(userID);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(emoticon.this, "10포인트를 사용합니다.", Toast.LENGTH_SHORT).show();
                    RaspberryPi();
                } else {
                    String errorMessage = "";
                    try {
                        errorMessage = response.errorBody().string();
                    } catch (Exception e) {
                        errorMessage = "응답 변환 실패";
                    }
                    Toast.makeText(emoticon.this, "포인트 사용 실패. " + errorMessage, Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(emoticon.this, "네트워크 오류: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void RaspberryPi() {
        String url = "http://192.168.0.133:3000/heart";
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();

        client.newCall(request).enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(okhttp3.Call call, IOException e) {
                e.printStackTrace();
                runOnUiThread(() -> Toast.makeText(emoticon.this, "이모티콘 전달 실패: " + e.getMessage(), Toast.LENGTH_LONG).show());
            }

            @Override
            public void onResponse(okhttp3.Call call, okhttp3.Response response) throws IOException {
                if (response.isSuccessful()) {
                    runOnUiThread(() -> Toast.makeText(emoticon.this, "My bin에 이모티콘을 전송합니다.", Toast.LENGTH_SHORT).show());
                } else {
                    runOnUiThread(() -> Toast.makeText(emoticon.this, "이모티콘 전달 실패. 코드: " + response.code(), Toast.LENGTH_LONG).show());
                }
            }
        });
    }
}
