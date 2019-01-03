package cn.biketomotor.xh.xuanhu.Adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import cn.biketomotor.xh.xuanhu.Fragment.UserCommentFragment;

//用户个人主页处的评论的ViewPager的适配器
public class UserCommentPageAdapter extends FragmentPagerAdapter {

    private int numOfTabs;
    //根据FragmentManager和页数创建适配器
    public UserCommentPageAdapter(FragmentManager fm, int numOfTabs) {
        super(fm);
        this.numOfTabs = numOfTabs;
    }

    //获取特定页的Fragment
    @Override
    public Fragment getItem(int position) {
        if(position >= numOfTabs)return null;
        UserCommentFragment ret = new UserCommentFragment();
        Bundle bundle = new Bundle();
        switch (position){
            case 0:
                bundle.putInt("type", UserCommentFragment.COMMENT);
                break;
            case 1:
                bundle.putInt("type", UserCommentFragment.LIKE);
                break;
            case 2:
                bundle.putInt("type", UserCommentFragment.UNLIKE);
                break;
            default:
                break;
        }
        ret.setArguments(bundle);
        return ret;
    }

    //获取页数
    @Override
    public int getCount() {
        return numOfTabs;
    }

}
