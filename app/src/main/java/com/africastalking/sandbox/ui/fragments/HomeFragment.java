package com.africastalking.sandbox.ui.fragments;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.africastalking.sandbox.ui.fragments.home.USSDFragment;
import com.africastalking.sandbox.ui.fragments.home.SMSFragment;
import com.africastalking.sandbox.ui.fragments.home.AirtimeFragment;
import com.africastalking.swipe.R;
import com.africastalking.sandbox.ui.fragments.home.VoiceFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lawrence on 10/29/15.
 */
public class HomeFragment extends Fragment {


    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private int[] tabIcons = {
            R.drawable.ussdmenu,
            R.drawable.smsmenu,
            R.drawable.airtimemenu,
            R.drawable.voicemenu
    };

    private String[] tabTitle = {
            "",
            "",
            "",
            ""
    };
    View view1, view2, view3, view4;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Bundle extras = getArguments();
        int fragment_selected = (int) extras.get("fragment");

        View rootView = inflater.inflate(R.layout.fragment_home,
                container, false);

        view1 = inflater.inflate(R.layout.customtab, null);
        view1.findViewById(R.id.icon).setBackgroundResource(R.drawable.ussdmenu);

        view2 = inflater.inflate(R.layout.customtab, null);
        view2.findViewById(R.id.icon).setBackgroundResource(R.drawable.smsmenu);

        view3 = inflater.inflate(R.layout.customtab, null);
        view3.findViewById(R.id.icon).setBackgroundResource(R.drawable.airtimemenu);

        view4 = inflater.inflate(R.layout.customtab, null);
        view4.findViewById(R.id.icon).setBackgroundResource(R.drawable.voicemenu);


        ((AppCompatActivity) getActivity()).getSupportActionBar().setSubtitle(tabTitle[0]);


        viewPager = (ViewPager) rootView.findViewById(R.id.viewpager);

        setupViewPager(viewPager);

        tabLayout = (TabLayout) rootView.findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        setupTabIcons();
        viewPager.setCurrentItem(fragment_selected);
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                ((AppCompatActivity) getActivity()).getSupportActionBar().setSubtitle(tabTitle[position]);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }


    private void setupTabIcons() {


        tabLayout.getTabAt(0).setCustomView(view1);
        tabLayout.getTabAt(1).setCustomView(view2);
        tabLayout.getTabAt(2).setCustomView(view3);
        tabLayout.getTabAt(3).setCustomView(view4);

//        tabLayout.getTabAt(0).setText("USSD");
//
//        tabLayout.getTabAt(1).setText("SMS");
//        tabLayout.getTabAt(2).setText("AIRTIME");
//        tabLayout.getTabAt(3).setText("VOICE");
    }


    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());
        adapter.addFrag(new USSDFragment(), "ONE");
        adapter.addFrag(new SMSFragment(), "TWO");
        adapter.addFrag(new AirtimeFragment(), "THREE");
        adapter.addFrag(new VoiceFragment(), "FOUR");
        viewPager.setAdapter(adapter);


    }


    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFrag(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
//            return mFragmentTitleList.get(position);
            return null;
        }
    }


    public interface updateSubtitle {
        void pageChanged(int position);
    }
}
