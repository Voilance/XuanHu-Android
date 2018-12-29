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

    public static void actionActivity(Context context, int userId) {
        Intent intent = new Intent(context, OthersHomeActivity.class);
        intent.putExtra("userId", userId);
        context.startActivity(intent);
    }

}
