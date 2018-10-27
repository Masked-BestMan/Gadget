package com.example.administrator.myapplication;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.zbm.dragframelayout.DragFrameLayout;

public class DragActivity extends AppCompatActivity {
    DragFrameLayout layout;
    ViewPager viewPager;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drag);
        layout=findViewById(R.id.drag);
        layout.setListener(new DragFrameLayout.OnDragFinishedListener() {
            @Override
            public void onFinished() {
                finish();
            }
        });
        viewPager=findViewById(R.id.view_pager);
        viewPager.setAdapter(new DragPagerAdapter(this));
    }
}
