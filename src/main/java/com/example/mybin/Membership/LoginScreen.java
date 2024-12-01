package com.example.mybin.Membership;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mybin.HomeActivity.MainHomeActivity;
import com.example.mybin.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginScreen extends AppCompatActivity {

    private static final String TAG = "LoginScreen";

    TextView membership_join;
    Button loginButton, loginButton2;
    EditText usernameEditText, passwordEditText;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);

        membership_join = findViewById(R.id.membership_btn);
        loginButton = findViewById(R.id.loginButton);
        usernameEditText = findViewById(R.id.usernameEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        loginButton2 = findViewById(R.id.loginButton2);

        membership_join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Membership_join.class);
                startActivity(intent);
            }
        });

//        //임시 로그인 버튼
        loginButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainHomeActivity.class);
                startActivity(intent);
            }
        });

        // 로그인 버튼 클릭 이벤트
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userID = usernameEditText.getText().toString();
                String password = passwordEditText.getText().toString();

                if (userID.isEmpty() || password.isEmpty()) {
                    showToast("아이디와 비밀번호를 입력해주세요.");
                } else {
                    login(userID, password);
                }
            }
        });
    }
    private void login(String userID, String password) {
        SignInDTO signInDTO = new SignInDTO(userID, password);

        RetrofitService retrofitService = new RetrofitService();
        RetrofitAPI retrofitAPI = retrofitService.getRetrofit().create(RetrofitAPI.class);

        retrofitAPI.signIn(signInDTO).enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                if (response.isSuccessful() && response.body() != null) {
                    if (response.body()) {
                        showToast("로그인 성공");
                        saveUser(userID);
                        Intent intent = new Intent(LoginScreen.this, MainHomeActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        showToast("아이디 혹은 비밀번호가 일치하지 않습니다. 다시 시도해주세요.");
                    }
                } else {
                    showToast("로그인 실패");
                    Log.e(TAG, "로그인 실패 " + response.code());
                }
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                showToast("로그인 요청 실패");
                Log.e(TAG, "로그인 요청 실패", t);
            }
        });
    }

    private void saveUser(String userID) {
        SharedPreferences sharedPreferences = getSharedPreferences("MyAppPreferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("userID", userID);
        editor.apply();
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
