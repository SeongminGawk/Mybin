package com.example.mybin;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.example.mybin.Membership.LoginScreen;
import com.example.mybin.Membership.Membership_join;
import com.example.mybin.environmentaltips.TipsScreen;


public class HomeScreen extends AppCompatActivity {

    FragmentManager fragmentManager;
    Button login_btn;
    TextView membership_join;
    ImageView mapImageView;
    ImageView tipsImageView;


    @SuppressLint({"WrongViewCast", "MissingInflatedId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        fragmentManager = getSupportFragmentManager();

        login_btn = findViewById(R.id.login_btn);
        //로그인 버튼
        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), LoginScreen.class);
                startActivity(intent);
            }
        });

        membership_join = findViewById(R.id.membership_btn);
        //회원가입 버튼
        membership_join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Membership_join.class);
                startActivity(intent);
            }
        });
        //구글 맵 이동 버튼
        mapImageView = findViewById(R.id.mapImageView);
        mapImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MapsActivity.class);
                startActivity(intent);
            }
        });
        //환경팁 이동 버튼
        tipsImageView = findViewById(R.id.tips);
        tipsImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), TipsScreen.class);
                startActivity(intent);
            }
        });
    }
}