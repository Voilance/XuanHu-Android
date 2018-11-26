package cn.biketomotor.xh.xuanhu.Activity;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import cn.biketomotor.xh.xuanhu.Adapter.CourseDetailPageAdapter;
import cn.biketomotor.xh.xuanhu.R;

public class CourseDetail extends BaseActivity implements TabLayout.BaseOnTabSelectedListener {
    ViewPager vpCourseDetail;
    TabLayout tlCourseDetail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }

    private void initView() {
        setContentView(R.layout.activity_course_detail);
        vpCourseDetail = findViewById(R.id.vp_course_detail);
        tlCourseDetail = findViewById(R.id.tl_course_detail);
        CourseDetailPageAdapter pageAdapter = new CourseDetailPageAdapter(getSupportFragmentManager(), tlCourseDetail.getTabCount());
        vpCourseDetail.setAdapter(pageAdapter);
        vpCourseDetail.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tlCourseDetail));
        tlCourseDetail.addOnTabSelectedListener(this);
    }

    public static void actionActivity(Context context) {
        Intent intent = new Intent(context, CourseDetail.class);
        context.startActivity(intent);
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        vpCourseDetail.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }
}
