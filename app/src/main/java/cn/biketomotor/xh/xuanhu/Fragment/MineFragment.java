package cn.biketomotor.xh.xuanhu.Fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import cn.biketomotor.xh.xuanhu.Activity.BaseActivity;
import cn.biketomotor.xh.xuanhu.Activity.EditInfoActivity;
import cn.biketomotor.xh.xuanhu.Activity.MainActivity;
import cn.biketomotor.xh.xuanhu.Adapter.UserCommentPageAdapter;
import cn.biketomotor.xh.xuanhu.R;

public class MineFragment extends Fragment implements View.OnClickListener, TabLayout.BaseOnTabSelectedListener {
    private static final String TAG = "TagMine";

    private ImageView ivAvatar;
    private TextView tvName;
    private Context context;
    private ViewPager vpComment;
    private View btInfo;
    private TabLayout tabLayout;
    private UserCommentPageAdapter pageAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mine, container, false);
        ivAvatar = view.findViewById(R.id.iv_avatar);
        tvName = view.findViewById(R.id.tv_name);
        btInfo = view.findViewById(R.id.user_info_ll);
        ivAvatar.setOnClickListener(this);
        btInfo.setOnClickListener(this);
        context = getContext();
        tabLayout = view.findViewById(R.id.user_comment_tab_layout);
        vpComment = view.findViewById(R.id.user_comment_pager);
        pageAdapter = new UserCommentPageAdapter(getChildFragmentManager(), tabLayout.getTabCount());
        vpComment.setAdapter(pageAdapter);
        vpComment.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(this);

        Bundle bundle = getArguments();
        if(bundle != null){
            if(bundle.containsKey("pos")){
                int pos = bundle.getInt("pos");
                vpComment.setCurrentItem(pos);
            }
        }
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.user_info_ll:
            case R.id.iv_avatar:
                // 点击头像，如果已经登陆，则跳转到编辑个人信息活动，否则跳转到登陆活动
//                LoginActivity.actionActivity(mainActivity);
                EditInfoActivity.actionActivity(context);
                break;
            case R.id.bt_logout:
                break;
            default:
                break;
        }
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        vpComment.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }

}
