package cn.biketomotor.xh.xuanhu.Activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.Button;

import cn.biketomotor.xh.xuanhu.Class.Sys;
import cn.biketomotor.xh.xuanhu.Fragment.HomeFragment;
import cn.biketomotor.xh.xuanhu.Fragment.MineFragment;
import cn.biketomotor.xh.xuanhu.R;

public class MainActivity extends BaseActivity implements View.OnClickListener {
    private static final String TAG = "TagMain";

    private Button btHome;
    private Button btMine;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
//        onAutoLogin();
    }

    private void initView() {
        setContentView(R.layout.test_activity_main);
        replaceFragment(new HomeFragment());

        btHome = findViewById(R.id.bt_home);
        btMine = findViewById(R.id.bt_mine);
        btHome.setOnClickListener(this);
        btMine.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_home:
                replaceFragment(new HomeFragment());
                break;
            case R.id.bt_mine:
                replaceFragment(new MineFragment());
                break;
            default:
                break;
        }
    }

    private void onAutoLogin() {
        // 读取存在本地的用户信息
        Sys.readSP(getSharedPreferences(Sys.SPName, Context.MODE_PRIVATE));
        // 如果存在本地的用户信息不为空且上次退出应用时处于登陆状态，则自动登陆
        if (!Sys.getEmail().equals("") && Sys.isLogin()) {
            // 登陆成功：初始化个人信息，右上角显示头像昵称等基本信息。
            // 登陆失败：右上角显示“登陆”按钮
        }
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fl_fragment, fragment);
        fragmentTransaction.commit();
    }
}
