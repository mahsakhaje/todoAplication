package com.example.hw9;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;

import java.util.LinkedList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    ViewPager mViewPager;
    TabLayout mTabLayout;
    myViewPager adaptor;

    List<Fragment> allFragments;

    private final int numberOfTabs = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        mViewPager = findViewById(R.id.viewPagerContainor);
        mTabLayout = findViewById(R.id.tab_layout);
        allFragments = new LinkedList<Fragment>();
        allFragments.add(TodoFragment.newInstance());
        allFragments.add(DoingFragment.newInstance());
        allFragments.add(DoneFragment.newInstance());


        adaptor = new myViewPager(getSupportFragmentManager());
        mViewPager.setAdapter(adaptor);

        mTabLayout.setupWithViewPager(mViewPager);




        TabLayout.Tab tab1 = mTabLayout.getTabAt(0);

        tab1.setText("TODO");

        TabLayout.Tab tab2 = mTabLayout.getTabAt(1);
        tab2.setText("DOING");

        TabLayout.Tab tab3 = mTabLayout.getTabAt(2);
        tab3.setText("DONE");

    }

    class myViewPager extends FragmentStatePagerAdapter {


        public myViewPager(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {

            Fragment fragment = allFragments.get(position);

            return fragment;


        }

        @Override
        public int getCount() {
            return numberOfTabs;
        }

        @Override
        public int getItemPosition(@NonNull Object object) {
            return POSITION_NONE;
        }
    }
}


