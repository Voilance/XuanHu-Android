package cn.biketomotor.xh.xuanhu.Adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import cn.biketomotor.xh.xuanhu.Fragment.CourseCommentFragment;
import cn.biketomotor.xh.xuanhu.Fragment.CourseDetailFragment;
import cn.biketomotor.xh.xuanhu.Fragment.UserCommentFragment;

public class CourseDetailPageAdapter extends FragmentPagerAdapter {

    private int numOfTabs;
    private Fragment currentFragment;

    public CourseDetailPageAdapter(FragmentManager fm, int numOfTabs) {
        super(fm);
        this.numOfTabs = numOfTabs;
    }
    @Override
    public Fragment getItem(int position) {
        if(position >= numOfTabs)return null;
        switch (position){
            case 0:
                return new CourseDetailFragment();
            case 1:
                return new CourseCommentFragment();
            default:
                return  null;
        }
    }

    @Override
    public int getCount() {
        return numOfTabs;
    }


    public Fragment getCurrentFragment() {
        return currentFragment;
    }

    @Override
    public void setPrimaryItem(ViewGroup container, int position, Object object) {
        if (getCurrentFragment() != object) {
            currentFragment = ((Fragment) object);
        }
        super.setPrimaryItem(container, position, object);
    }
}
