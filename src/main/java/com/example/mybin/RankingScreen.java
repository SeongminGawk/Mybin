package com.example.mybin;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mybin.Membership.RetrofitAPI;
import com.example.mybin.Membership.RetrofitService;
import com.example.mybin.Membership.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RankingScreen extends AppCompatActivity {

    private static final String TAG = "RankingScreen";
    private TextView national_ranking;
    private TextView my_ranking;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking_screen);

        national_ranking = findViewById(R.id.national_ranking);
        my_ranking = findViewById(R.id.my_ranking);

        loadRankingData();
    }

    private void loadRankingData() {
        SharedPreferences sharedPreferences = getSharedPreferences("MyAppPreferences", MODE_PRIVATE);
        String userID = sharedPreferences.getString("userID", "");

        RetrofitService retrofitService = new RetrofitService();
        RetrofitAPI retrofitAPI = retrofitService.getRetrofit().create(RetrofitAPI.class);

        // 전체 랭킹 데이터
        retrofitAPI.getAllUserRank().enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<User> userList = response.body();
                    int userRank = getUserRankFromList(userID, userList); // 사용자 순위
                    displayRanking(userList);
                    displayUserRank(userRank, userList);
                } else {
                    Log.e(TAG, "Failed to load user data.");
                }
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                Log.e(TAG, "Failed to load user data.", t);
            }
        });
    }

    private int getUserRankFromList(String userID, List<User> userList) {
        for (int i = 0; i < userList.size(); i++) {
            if (userList.get(i).getUserId().equals(userID)) {
                return i + 1; // 1부터 순위
            }
        }
        return -1; // 사용자를 찾지 못한 경우
    }

    private void displayRanking(List<User> userList) {
        StringBuilder rankingBuilder = new StringBuilder();

        int rank = 1;

        for (User user : userList) {
            rankingBuilder.append(rank).append(". ").append(user.getUserId()).append(" - ").append(user.getRankPoint()).append("점\n");
            rank++;
        }

        national_ranking.setText(rankingBuilder.toString());
    }

    private void displayUserRank(int userRank, List<User> userList) {
        if (userRank != -1) {
            User user = userList.get(userRank - 1); // 1등부터 표시
            my_ranking.setText("본인 랭킹: " + userRank + "위 (" + user.getRankPoint() + "점)");
        } else {
            my_ranking.setText("랭킹 정보를 가져오지 못했습니다.");
        }
    }
}
