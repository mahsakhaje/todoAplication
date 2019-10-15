package com.example.hw9;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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


        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                adaptor.notifyDataSetChanged();
                if (position == 0) {
                    TodoFragment frag = (TodoFragment) adaptor.getItem(0);
                    frag.checkBackGround();


                } else if (position == 1) {
                    DoingFragment frag = (DoingFragment) adaptor.getItem(1);
                    frag.checkBackGround();


                } else if (position == 2) {
                    DoneFragment frag = (DoneFragment) adaptor.getItem(2);

                    frag.notifyAdapter();
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

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

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "TODO";

                case 1:
                    return "DOING";

                case 2:
                    return "DONE";

                default:
                    return "";
            }

        }
    }

}


