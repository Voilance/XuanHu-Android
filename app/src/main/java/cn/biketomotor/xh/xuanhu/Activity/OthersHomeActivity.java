package cn.biketomotor.xh.xuanhu.Activity;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.gsm.GsmCellLocation;
import android.util.Log;

import java.util.List;

import cn.biketomotor.xh.xuanhu.Api.Beans.Comment;
import cn.biketomotor.xh.xuanhu.Api.Beans.User;
import cn.biketomotor.xh.xuanhu.Api.Result;
import cn.biketomotor.xh.xuanhu.Api.UserApi;
import cn.biketomotor.xh.xuanhu.Class.GlobalDataChannel;
import cn.biketomotor.xh.xuanhu.Fragment.MineFragment;
import cn.biketomotor.xh.xuanhu.R;

//从课程评论查看个人信息时跳转到的活动，用来显示个人信息
//如果没有这个活动的话，就要跳转到MainActivity，
//同时将MainActivity中的Fragment切换到个人信息Fragment
//但这还会带来一个问题：因为MainActivity中重写了onBackPressed方法
//所以会导致无法返回到课程评论处......
public class OthersHomeActivity extends BaseActivity {
    private static final String TAG = "OthersHome";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_others_home);

//        Intent intent = getIntent();
//        getUser(intent.getIntExtra("userId", 0));

        MineFragment othersMineFragment = new MineFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fl_fragment, othersMineFragment);
        fragmentTransaction.commit();

    }

    //启动OthersHomeActivity，同时传递用户ID
    public static void actionActivity(Context context, int userId) {
        Intent intent = new Intent(context, OthersHomeActivity.class);
        intent.putExtra("userId", userId);
        context.startActivity(intent);
    }

}
