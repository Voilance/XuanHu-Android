package cn.biketomotor.xh.xuanhu.Adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import cn.biketomotor.xh.xuanhu.Fragment.UserCommentFragment;

public class UserCommentPageAdapter extends FragmentPagerAdapter {

    private int numOfTabs;
    public UserCommentPageAdapter(FragmentManager fm, int numOfTabs) {
        super(fm);
        this.numOfTabs = numOfTabs;
    }
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

    @Override
    public int getCount() {
        return numOfTabs;
    }

}
