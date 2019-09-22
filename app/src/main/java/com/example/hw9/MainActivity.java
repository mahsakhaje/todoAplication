package com.example.hw9;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.Menu;

import com.google.android.material.tabs.TabLayout;

import java.util.zip.Inflater;

public class MainActivity extends AppCompatActivity {
ViewPager mViewPager;
TabLayout mTabLayout;
myViewPager adaptor;
private final int numberOfTabs=3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        mViewPager=findViewById(R.id.viewPagerContainor);
        mTabLayout=findViewById(R.id.tab_layout);
        adaptor=new myViewPager(getSupportFragmentManager());
        mViewPager.setAdapter(adaptor);

        mTabLayout.setupWithViewPager(mViewPager);


    }
class myViewPager extends FragmentPagerAdapter {


    public myViewPager(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment=null;
        switch (position){
            case 0:
                fragment=TodoFragment.newInstance();
                break;
            case 1:
                fragment=DoingFragment.newInstance();
                break;
            case 2:
               fragment=DoneFragment.newInstance();
               break;
               default:
                   fragment=null;

        }
        return fragment;
    }
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0: return "TODO";
            case 1:return "DOING";
            case 2:return "DONE";
        }
        return null;
    }
    @Override
    public int getCount() {
        return numberOfTabs;
    }
}


}
