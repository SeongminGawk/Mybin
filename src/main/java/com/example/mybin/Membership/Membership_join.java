package com.example.mybin.Membership;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.mybin.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Membership_join extends AppCompatActivity {

    private EditText usernameEditText, passwordEditText, confirmPasswordEditText, phoneNumberEditText, addressEditText;
    private CheckBox environmentMemberCheckBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_membership_join);

        usernameEditText = findViewById(R.id.usernameEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        confirmPasswordEditText = findViewById(R.id.confirmPasswordEditText);
        phoneNumberEditText = findViewById(R.id.phoneNumberEditText);
        addressEditText = findViewById(R.id.addressEditText);
        environmentMemberCheckBox = findViewById(R.id.environmentMemberCheckBox);

        Button signupButton = findViewById(R.id.signupButton);
        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });
    }

    private void registerUser() {
        String userID = usernameEditText.getText().toString();
        String password = passwordEditText.getText().toString();
        String confirmPassword = confirmPasswordEditText.getText().toString();
        String phoneNumber = phoneNumberEditText.getText().toString();
        String address = addressEditText.getText().toString();
        boolean Cleaner = environmentMemberCheckBox.isChecked();

        if (userID.isEmpty() || password.isEmpty() || confirmPassword.isEmpty() || phoneNumber.isEmpty() || address.isEmpty()) {
            showToast("모든 필수 입력란을 작성해주세요.");
        } else if (!password.equals(confirmPassword)) {
            showToast("비밀번호가 일치하지 않습니다. 다시 입력해주세요.");
        } else {
            User user = new User(userID, password, phoneNumber, address, Cleaner);
            UserRegistration(user);
        }
    }

    private void UserRegistration(User user) {
        RetrofitService retrofitService = new RetrofitService();
        RetrofitAPI retrofitAPI = retrofitService.getRetrofit().create(RetrofitAPI.class);

        retrofitAPI.signUp(user).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    showToast("회원가입이 완료되었습니다.");
                    Intent intent = new Intent(Membership_join.this, LoginScreen.class);
                    startActivity(intent);
                } else {
                    showToast("이미 존재하는 아이디 입니다.");
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                showToast("회원가입 요청 실패");
            }
        });
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
