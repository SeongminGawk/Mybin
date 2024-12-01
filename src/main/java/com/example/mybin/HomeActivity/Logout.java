package com.example.mybin.HomeActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mybin.Membership.LoginScreen;

public class Logout extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        logout();
    }

    private void logout() {
        showToast("로그아웃되었습니다.");
        clearUserData(); // 모든 사용자 데이터 삭제
        Intent intent = new Intent(Logout.this, LoginScreen.class);
        startActivity(intent);
        finish();
    }

    // 메시지 표시
    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    // SharedPreferences에서 모든 사용자 데이터 삭제
    private void clearUserData() {
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear(); // 모든 데이터 삭제
        editor.apply();
    }
}
