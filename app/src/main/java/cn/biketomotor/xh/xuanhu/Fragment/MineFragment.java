package cn.biketomotor.xh.xuanhu.Fragment;

import android.os.Bundle;
import android.support.design.widget.TabItem;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import cn.biketomotor.xh.xuanhu.Activity.EditInfoActivity;
import cn.biketomotor.xh.xuanhu.Activity.LoginActivity;
import cn.biketomotor.xh.xuanhu.Activity.MainActivity;
import cn.biketomotor.xh.xuanhu.Adapter.HistoryCommentItemAdapter;
import cn.biketomotor.xh.xuanhu.Item.CommentItem;
import cn.biketomotor.xh.xuanhu.R;

public class MineFragment extends Fragment implements View.OnClickListener, TabLayout.BaseOnTabSelectedListener {
    private static final String TAG = "TagMine";

    private ImageView ivAvatar;
    private TextView tvName;
    private MainActivity mainActivity;
    private ViewPager commentPager;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        ivAvatar = view.findViewById(R.id.iv_avatar);
        tvName = view.findViewById(R.id.tv_name);
        ivAvatar.setOnClickListener(this);
        mainActivity = (MainActivity)getActivity();
        TabLayout tabLayout = view.findViewById(R.id.user_comment_tab_layout);
        commentPager = view.findViewById(R.id.user_comment_pager);
        CommentPageAdapter pageAdapter = new CommentPageAdapter(getChildFragmentManager(), tabLayout.getTabCount());
        commentPager.setAdapter(pageAdapter);
        commentPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_avatar:
                // 点击头像，如果已经登陆，则跳转到编辑个人信息活动，否则跳转到登陆活动
//                LoginActivity.actionActivity(mainActivity);
                EditInfoActivity.actionActivity(mainActivity);
                break;
            case R.id.bt_logout:
                break;
            default:
                break;
        }
    }

    public class CommentPageAdapter extends FragmentPagerAdapter{

        private int numOfTabs;
        public CommentPageAdapter(FragmentManager fm, int numOfTabs) {
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

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        commentPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }
}
