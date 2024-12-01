package com.example.mybin;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import com.example.mybin.environmentaltips.environmentFragment;

public class ViewPagerAdapter extends FragmentPagerAdapter {

    public ViewPagerAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        // 각 탭에 맞는 Fragment 반환
        switch (position) {
            case 0:
                // 분리수거 방법 Fragment
                return new environmentFragment(); 
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        // 탭 개수
        return 1;
    }

    @NonNull
    @Override
    public CharSequence getPageTitle(int position) {
        // 탭의 제목 반환
        switch (position) {
            case 0:
                return "분리수거 방법";
            default:
                return null;
        }
    }
    public void addFragment(environmentFragment environmentFragment, String 분리수거_방법) {
    }
}
