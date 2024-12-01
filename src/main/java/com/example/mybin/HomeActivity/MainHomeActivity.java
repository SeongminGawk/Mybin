package com.example.mybin.HomeActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.example.mybin.MapsActivity;
import com.example.mybin.Membership.RetrofitAPI;
import com.example.mybin.Membership.RetrofitService;
import com.example.mybin.R;
import com.example.mybin.RankingScreen;
import com.example.mybin.environmentaltips.TipsScreen;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainHomeActivity extends AppCompatActivity {
    private static final String STATE_IS_HOME = "isHome";
    private boolean isHome = true;
    Button home_button;
    Button menuButton;
    Button mybin_button;
    ImageView mapImageView;
    ImageView rankingImageView;
    ImageView tipsImageView;
    ImageView qrScanButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_home);

        // QR 스캔 처리
        qrScanButton = findViewById(R.id.qr_scan_button);
        qrScanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new IntentIntegrator(MainHomeActivity.this).initiateScan();
            }
        });
        
        // 홈 화면 버튼
        home_button = findViewById(R.id.home_button);
        home_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 이미 홈 화면일 때는 동작 없음
                if (isHome) {
                    return;
                }
                // 새로운 홈 액티비티를 시작하고 현재 홈 액티비티의 상태를 유지
                Intent intent = new Intent(getApplicationContext(), MainHomeActivity.class);
                startActivity(intent);
            }
        });

        // 지도 이동 버튼
        mapImageView = findViewById(R.id.mapImageView);
        mapImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MapsActivity.class);
                startActivity(intent);
            }
        });

        // 과제 이동 버튼
        mybin_button = findViewById(R.id.mybin_button);
        mybin_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), emoticon.class);
                startActivity(intent);
            }
        });

        // 랭킹 이동 버튼
        rankingImageView = findViewById(R.id.ranking);
        rankingImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), RankingScreen.class);
                startActivity(intent);
            }
        });

        // 환경 팁 이동 버튼
        tipsImageView = findViewById(R.id.tips);
        tipsImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), TipsScreen.class);
                startActivity(intent);
            }
        });

        // 메뉴 버튼 클릭 이벤트 처리
        menuButton = findViewById(R.id.menu_button);
        menuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopupMenu(v);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() == null) {
                Toast.makeText(this, "스캔이 취소되었습니다.", Toast.LENGTH_SHORT).show();
            } else {
                String scannedContent = result.getContents();
                if (scannedContent.equals("$#aff2224s&*$sd2123sdff*&4#afd1431fadf(*@")) {
                    String userID = getUserID();
                    if (userID != null) {
                        userIDPoint(userID);
                    } else {
                        Toast.makeText(this, "사용자 ID를 찾을 수 없습니다.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(this, "스캔 결과가 일치하지 않습니다.", Toast.LENGTH_SHORT).show();
                }
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private String getUserID() {
        SharedPreferences sharedPreferences = getSharedPreferences("MyAppPreferences", MODE_PRIVATE);
        return sharedPreferences.getString("userID", null);
    }

    private void userIDPoint(String userID) {
        RetrofitService retrofit = new RetrofitService();
        RetrofitAPI pointup = retrofit.getRetrofit().create(RetrofitAPI.class);
        Call<ResponseBody> call = pointup.pointUp(userID);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    String msg = "포인트가 적립되었습니다! 감사합니다, " + userID + "님!\n" +
                            "탄소 중립을 위한 당신의 기여가 지구를 더 깨끗하게 만드는데 도움을 주고 있습니다.";
                    Toast.makeText(MainHomeActivity.this, msg, Toast.LENGTH_LONG).show();
                } else {
                    String errorMessage = "";
                    try {
                        errorMessage = response.errorBody().string();
                    } catch (Exception e) {
                        errorMessage = "응답 변환 실패";
                    }
                    Toast.makeText(MainHomeActivity.this, "포인트 증가 실패. 코드: " + response.code() + ", 메시지: " + errorMessage, Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(MainHomeActivity.this, "네트워크 오류: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    // 화면 전환
    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(STATE_IS_HOME, isHome);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        isHome = savedInstanceState.getBoolean(STATE_IS_HOME, true);
    }

    private void showPopupMenu(View view) {
        PopupMenu popupMenu = new PopupMenu(this, view);
        MenuInflater inflater = popupMenu.getMenuInflater();
        inflater.inflate(R.menu.popup_menu, popupMenu.getMenu());

        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                return handleMenuItemClick(item);
            }
        });
        popupMenu.show();
    }

    private boolean handleMenuItemClick(MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.logout) {
            Toast.makeText(this, "로그아웃", Toast.LENGTH_SHORT).show();
            Intent intent2 = new Intent(this, Logout.class);
            startActivity(intent2);
            return true;
        } else if (itemId == R.id.menu_option3) {
            Intent tip = new Intent(this, TipsScreen.class);
            startActivity(tip);
            return true;
        }
        return false;
    }
}
