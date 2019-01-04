package cn.biketomotor.xh.xuanhu.Fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Trace;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.telephony.gsm.GsmCellLocation;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cn.biketomotor.xh.xuanhu.Activity.BaseActivity;
import cn.biketomotor.xh.xuanhu.Activity.EditInfoActivity;
import cn.biketomotor.xh.xuanhu.Activity.MainActivity;
import cn.biketomotor.xh.xuanhu.Activity.OthersHomeActivity;
import cn.biketomotor.xh.xuanhu.Adapter.UserCommentPageAdapter;
import cn.biketomotor.xh.xuanhu.Api.Beans.Comment;
import cn.biketomotor.xh.xuanhu.Api.Result;
import cn.biketomotor.xh.xuanhu.Api.UserApi;
import cn.biketomotor.xh.xuanhu.Class.GlobalDataChannel;
import cn.biketomotor.xh.xuanhu.Class.Util;
import cn.biketomotor.xh.xuanhu.R;
//个人主页Fragment，主要显示用户头像，名字以及评论、赞同与不赞同的评论。
public class MineFragment extends Fragment implements View.OnClickListener, TabLayout.BaseOnTabSelectedListener {
    private static final String TAG = "TagMine";

    private ImageView ivAvatar;
    private TextView tvName;
    private Context context;
    private ViewPager vpComment;
    private View btInfo;
    private TabLayout tabLayout;
    private UserCommentPageAdapter pageAdapter;
    private UserApi.UserInfo userInfo;
    private View view;
    //创建界面
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_mine, container, false);
        ivAvatar = view.findViewById(R.id.iv_avatar);
        tvName = view.findViewById(R.id.tv_name);
        getInfo();
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

    //处理单击事件，单击头像或者“个人资料”按钮时跳转到个人信息活动
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.user_info_ll:
            case R.id.iv_avatar:
                // 点击头像，如果已经登陆，则跳转到编辑个人信息活动，否则跳转到登陆活动
//                LoginActivity.actionActivity(mainActivity);
                EditInfoActivity.actionActivity(context, userInfo.id);
                break;
            case R.id.bt_logout:
                break;
            default:
                break;
        }
    }

    //同步ViewPager和TabLayout的选项
    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        vpComment.setCurrentItem(tab.getPosition());
    }

    //暂无作用
    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    //暂无作用
    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }

    //获取用户的个人信息，包括评论等
    private void getInfo(){
        Activity activity = getActivity();
        final int userId = activity.getIntent().getIntExtra("userId", -1);
        if(userId == -1)return;
        new Thread(new Runnable() {
            @Override
            public void run() {
                final Result<UserApi.UserInfo>infoResult = UserApi.INSTANCE.getUserInfo(userId);
                if(infoResult.isOk()){
                    userInfo = infoResult.get();
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            tvName.setText(userInfo.name);
                            Util.loadImageFromUrl(userInfo.avatar_url, ivAvatar, getActivity());
                        }
                    });
                }
            }
        }).start();
    }

}
