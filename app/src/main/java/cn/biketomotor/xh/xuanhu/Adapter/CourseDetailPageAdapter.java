package cn.biketomotor.xh.xuanhu.Adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import cn.biketomotor.xh.xuanhu.Fragment.CourseCommentFragment;
import cn.biketomotor.xh.xuanhu.Fragment.CourseDetailFragment;
import cn.biketomotor.xh.xuanhu.Fragment.UserCommentFragment;

//课程详情活动中ViewPager的适配器
public class CourseDetailPageAdapter extends FragmentPagerAdapter {

    private int numOfTabs;
    private Fragment currentFragment;

    //根据FragmentManager和页数创建适配器
    public CourseDetailPageAdapter(FragmentManager fm, int numOfTabs) {
        super(fm);
        this.numOfTabs = numOfTabs;
    }

    //返回特定页的Fragment
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

    //获取页数
    @Override
    public int getCount() {
        return numOfTabs;
    }

    //获取当前的页的Fragment
    public Fragment getCurrentFragment() {
        return currentFragment;
    }

    //在切换页时更新当前页的Fragment
    @Override
    public void setPrimaryItem(ViewGroup container, int position, Object object) { //暂无作用
        if (getCurrentFragment() != object) {
            currentFragment = ((Fragment) object);
        }
        super.setPrimaryItem(container, position, object);
    }
}
