package cn.biketomotor.xh.xuanhu.Activity;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import cn.biketomotor.xh.xuanhu.Fragment.MineFragment;
import cn.biketomotor.xh.xuanhu.R;

public class OthersHomeActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_others_home);
        MineFragment othersMineFragment = new MineFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fl_fragment, othersMineFragment);
        fragmentTransaction.commit();
    }

    public static void actionActivity(Context context) {
        Intent intent = new Intent(context, OthersHomeActivity.class);
        context.startActivity(intent);
    }
}
