package com.setting;


import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.Objects;

public class SettingFragment extends Fragment {

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_setting, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TabLayout tabLayout = view.findViewById(R.id.setting_tabs);
        ViewPager viewPager = view.findViewById(R.id.setting_tabs_viewPager);
        ViewPagerAdapter mAdapter = new ViewPagerAdapter(getChildFragmentManager());
        mAdapter.addFragment(new GeneralFragment());
        mAdapter.addFragment(new SecurityFragment());
        mAdapter.addFragment(new PreferenceFragment());
        viewPager.setAdapter(mAdapter);
        int limit = (mAdapter.getCount() > 1 ? mAdapter.getCount() - 1 : 1);
        viewPager.setOffscreenPageLimit(limit);

        tabLayout.setupWithViewPager(viewPager);

        View headerView = ((LayoutInflater) Objects.requireNonNull(Objects.requireNonNull(getActivity()).getSystemService(Context.LAYOUT_INFLATER_SERVICE))).inflate(R.layout.setting_tab,null,false);
        LinearLayout lin1 = headerView.findViewById(R.id.setting_tab_layout_1);
        LinearLayout lin2 = headerView.findViewById(R.id.setting_tab_layout_2);
        LinearLayout lin3 = headerView.findViewById(R.id.setting_tab_layout_3);
        final TextView tab1 = headerView.findViewById(R.id.setting_tab_1);
        final TextView tab2 = headerView.findViewById(R.id.setting_tab_2);
        final TextView tab3 = headerView.findViewById(R.id.setting_tab_3);
        final ImageView dot1 = headerView.findViewById(R.id.setting_tab_1_dot);
        dot1.setVisibility(View.VISIBLE);
        final ImageView  dot2 = headerView.findViewById(R.id.setting_tab_2_dot);
        dot2.setVisibility(View.GONE);
        final ImageView  dot3 = headerView.findViewById(R.id.setting_tab_3_dot);
        dot3.setVisibility(View.GONE);
        tab1.setTextColor(Color.parseColor("#131b37"));
        tab2.setTextColor(Color.parseColor("#818183"));
        tab3.setTextColor(Color.parseColor("#818183"));

        Objects.requireNonNull(tabLayout.getTabAt(0)).setCustomView(lin1);
        Objects.requireNonNull(tabLayout.getTabAt(1)).setCustomView(lin2);
        Objects.requireNonNull(tabLayout.getTabAt(2)).setCustomView(lin3);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                switch (tab.getPosition()){
                    case 0 : {
                        dot1.setVisibility(View.VISIBLE);
                        dot2.setVisibility(View.GONE);
                        dot3.setVisibility(View.GONE);
                        tab1.setTextColor(Color.parseColor("#131b37"));
                        tab2.setTextColor(Color.parseColor("#818183"));
                        tab3.setTextColor(Color.parseColor("#818183"));
                        break;
                    }
                    case 1 : {
                        dot1.setVisibility(View.GONE);
                        dot2.setVisibility(View.VISIBLE);
                        dot3.setVisibility(View.GONE);
                        tab1.setTextColor(Color.parseColor("#818183"));
                        tab2.setTextColor(Color.parseColor("#131b37"));
                        tab3.setTextColor(Color.parseColor("#818183"));
                        break;
                    }

                    case 2 : {
                        dot1.setVisibility(View.GONE);
                        dot2.setVisibility(View.GONE);
                        dot3.setVisibility(View.VISIBLE);
                        tab1.setTextColor(Color.parseColor("#818183"));
                        tab2.setTextColor(Color.parseColor("#818183"));
                        tab3.setTextColor(Color.parseColor("#131b37"));
                        break;
                    }

                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    @SuppressWarnings("deprecation")
    public class ViewPagerAdapter extends FragmentPagerAdapter {

        private ArrayList<Fragment> mFragMentList = new ArrayList<>();

        ViewPagerAdapter(@NonNull FragmentManager fm) {
            super(fm);
        }

        void addFragment(Fragment fragment){
            mFragMentList.add(fragment);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            return mFragMentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragMentList.size();
        }
    }
}
