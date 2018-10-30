package com.example.khoaj.projectlab1.Activity;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.khoaj.projectlab1.Fragment.MainPagerAdapter;
import com.example.khoaj.projectlab1.R;

public class CalculationActivity extends AppCompatActivity {
    private ViewPager viewPager;
    private MainPagerAdapter adapter;
    private TabLayout tabLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculation);
        viewPager = findViewById(R.id.viewPager);
        adapter = new MainPagerAdapter(getSupportFragmentManager());
        setTitle("Top 10");
        viewPager.setAdapter(adapter);

        //ket hop
        tabLayout = findViewById(R.id.tabLayout);
        //ket noi ViewPager voi TabLayout
        tabLayout.setupWithViewPager(viewPager);
    }
}
