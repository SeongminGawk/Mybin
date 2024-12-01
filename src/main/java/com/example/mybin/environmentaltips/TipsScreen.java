package com.example.mybin.environmentaltips;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;
import com.example.mybin.R;
import com.example.mybin.ViewPagerAdapter;
import com.google.android.material.tabs.TabLayout;

public class TipsScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tips_screen);

        ViewPager viewPager = findViewById(R.id.previous_view_pager);
        TabLayout tabLayout = findViewById(R.id.previous_tab_layout);

        // ViewPager에 어댑터 설정
        setViewPager(viewPager);
        // TabLayout과 ViewPager 연결
        tabLayout.setupWithViewPager(viewPager);
    }

    private void setViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new environmentFragment(), "분리수거 방법");
        viewPager.setAdapter(adapter);
    }
}
