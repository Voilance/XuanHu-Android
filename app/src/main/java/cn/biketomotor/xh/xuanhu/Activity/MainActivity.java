//package cn.biketomotor.xh.xuanhu.Activity;
//
//import android.content.Intent;
//import android.graphics.Color;
//import android.os.Build;
//import android.os.Bundle;
//import android.support.v7.app.AppCompatActivity;
//import android.view.View;
//import android.view.Window;
//import android.view.WindowManager;
//
//import cn.biketomotor.xh.xuanhu.R;
//
//
//public class MainActivity extends AppCompatActivity {
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//
//        super.onCreate(savedInstanceState);
//        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
//        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            Window window = getWindow();
//            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
//            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
//                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
//            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//            window.setStatusBarColor(Color.TRANSPARENT);
//            window.setNavigationBarColor(Color.TRANSPARENT);
//        }
//
//        setContentView(R.layout.activity_main);
//
//        Intent intent = new Intent(this, WelcomeActivity.class);
//        startActivity(intent);
//        finish();
//        overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
//
//    }
//
//}

package cn.biketomotor.xh.xuanhu.Activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.ViewDragHelper;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.lang.reflect.Field;
import java.util.List;

import cn.biketomotor.xh.xuanhu.Api.Beans.Comment;
import cn.biketomotor.xh.xuanhu.Api.CommentApi;
import cn.biketomotor.xh.xuanhu.Api.Result;
import cn.biketomotor.xh.xuanhu.Api.SessionApi;
import cn.biketomotor.xh.xuanhu.Class.LocalUser;
import cn.biketomotor.xh.xuanhu.Class.Sys;
import cn.biketomotor.xh.xuanhu.Fragment.HomeFragment;
import cn.biketomotor.xh.xuanhu.Fragment.MineFragment;
import cn.biketomotor.xh.xuanhu.R;

public class MainActivity extends BaseActivity implements View.OnClickListener {
    private static final String TAG = "TagMain";
    private TextView btSearch;
    private View btMyComments;
    private View btMyVoteUp;
    private View btMyVoteDown;
    private View btLogin;
    private View btSignUp;
    private View btSignOut;
    private View btHome;
    private TextView menuIcon;
    private DrawerLayout drawerLayout;
    private ImageView userAvatar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
//        onAutoLogin();
    }

    private void initView() {
        setContentView(R.layout.activity_main);
        replaceFragment(new HomeFragment());

        btSearch = findViewById(R.id.bt_search);
        btMyComments = findViewById(R.id.bt_my_comments);
        btMyVoteUp = findViewById(R.id.bt_my_vote_up);
        btMyVoteDown = findViewById(R.id.bt_my_vote_down);
        btLogin = findViewById(R.id.bt_login);
        btSignUp = findViewById(R.id.bt_sign_up);
        btSignOut = findViewById(R.id.bt_sign_out);
        btHome = findViewById(R.id.bt_home);
        drawerLayout = findViewById(R.id.drawer_layout);
        userAvatar = findViewById(R.id.iv_avatar);
        menuIcon = findViewById(R.id.menu_icon);

        menuIcon.setOnClickListener(this);
        userAvatar.setOnClickListener(this);
        btLogin.setOnClickListener(this);
        btSignUp.setOnClickListener(this);
        btSignOut.setOnClickListener(this);
        btSearch.setOnClickListener(this);
        btHome.setOnClickListener(this);
        btMyComments.setOnClickListener(this);
        btMyVoteUp.setOnClickListener(this);
        btMyVoteDown.setOnClickListener(this);
        makeDrawerMoreSensitive();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_home:
                replaceFragment(new HomeFragment());
                break;
            case R.id.menu_icon:
                //show drawer menu
                drawerLayout.openDrawer(GravityCompat.START);
                break;
            case R.id.iv_avatar:
                toMine(0);
                break;
            case R.id.bt_login:
                LoginActivity.actionActivity(this);
                break;
            case R.id.bt_sign_up:
                RegisterActivity.actionActivity(this);
                break;
            case R.id.bt_sign_out:
                break;
            case R.id.bt_search:
                SearchActivity.actionActivity(this);
                break;
            case R.id.bt_my_comments:
                toMine(0);
                break;
            case R.id.bt_my_vote_up:
                toMine(1);
                break;
            case R.id.bt_my_vote_down:
                toMine(2);
                break;
            default:
                break;
        }
    }

    private void onAutoLogin() {
        Log.e(TAG, "onAutoLogin: ");
        // 读取存在本地的用户信息
        Sys.readSP(getSharedPreferences(Sys.SPName, Context.MODE_PRIVATE));
        Log.e(TAG, "hi" + Sys.getEmail() + Sys.getPassword());
        // 如果存在本地的用户信息不为空且上次退出应用时处于登陆状态，则自动登陆
        if (!Sys.getEmail().equals("") && Sys.isLogin()) {
            // 登陆成功：初始化个人信息，右上角显示头像昵称等基本信息。
            // 登陆失败：右上角显示“登陆”按钮
            new Thread(new Runnable() {
                @Override
                public void run() {
                    final Result<SessionApi.LoginResult> result = SessionApi.INSTANCE.login(Sys.getEmail(), Sys.getPassword());
                    if (result.isOk()) {
                        if (result.get().success) {
                            Log.e(TAG, "run:success");
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    LocalUser.online = true;
                                }
                            });
                        } else {
                            Sys.setLogin(false);
                            Sys.writeSP(getSharedPreferences(Sys.SPName, Context.MODE_PRIVATE));
                        }
                    } else {
                        Log.e(TAG, result.getErrorMessage());
                        Sys.setLogin(false);
                        Sys.writeSP(getSharedPreferences(Sys.SPName, Context.MODE_PRIVATE));
                    }
                }
            }).start();
        }
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fl_fragment, fragment, fragment.getClass().getName());
        fragmentTransaction.commit();
        if(drawerLayout != null && drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        }
    }

    private void makeDrawerMoreSensitive(){
        try {
            Field draggerFiled = DrawerLayout.class.getDeclaredField("mLeftDragger");
            draggerFiled.setAccessible(true);
            ViewDragHelper vdh = (ViewDragHelper)draggerFiled.get(drawerLayout);

            Field edgeSizeField = ViewDragHelper.class.getDeclaredField("mEdgeSize");
            edgeSizeField.setAccessible(true);

            int origEdgeSize = (int)edgeSizeField.get(vdh);
            int newEdgeSize = origEdgeSize * 5;
            edgeSizeField.setInt(vdh, newEdgeSize);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //按下返回键的时候
    @Override
    public void onBackPressed() {
        //如果此时是在个人主页
        Fragment mineFragment = getSupportFragmentManager().findFragmentByTag(MineFragment.class.getName());
        if(mineFragment != null){
            //那么返回首页
            replaceFragment(new HomeFragment());
        }
        else {
            //否则退出应用
            finish();
        }
    }

    private void toMine(int pos){
        MineFragment mineFragment = new MineFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("pos", pos);
        mineFragment.setArguments(bundle);
        replaceFragment(mineFragment);
    }
}
