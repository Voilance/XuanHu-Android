package cn.biketomotor.xh.xuanhu.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import cn.biketomotor.xh.xuanhu.Api.Result;
import cn.biketomotor.xh.xuanhu.Api.UserApi;
import cn.biketomotor.xh.xuanhu.Class.LocalUser;
import cn.biketomotor.xh.xuanhu.Class.Util;
import cn.biketomotor.xh.xuanhu.R;

//查看/修改个人信息的活动
public class EditInfoActivity extends BaseActivity implements View.OnClickListener {
    private static final String TAG = "TagEditInfo";

    private LinearLayout btAvatar;
    private ImageView ivAvatar;
    private EditText etName;
    private TextView tvName;
    private EditText etPassword;
    private EditText etNewPassword;
    private EditText etInfo;
    private TextView tvInfo;
    private TextView tvEmail;
    private View btSubmit;
    private View vNewPassword;
    private View vOldPassword;

    private String avatar;
    private String name;
    private String password;
    private String newPassword;
    private String info;
    private int userId = -1;

    //创建活动时初始化界面
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }

    //初始化界面：获取头像、用户名等控件，从后台获取用户信息，绑定事件
    private void initView() {
        setContentView(R.layout.activity_edit_info);

        btAvatar = findViewById(R.id.bt_avatar);
        ivAvatar = findViewById(R.id.iv_avatar);
        etName = findViewById(R.id.et_name);
        tvName = findViewById(R.id.tv_name);
        etPassword = findViewById(R.id.et_password);
        etNewPassword = findViewById(R.id.et_new_password);
        etInfo = findViewById(R.id.et_info);
        tvInfo = findViewById(R.id.tv_info);
        btSubmit = findViewById(R.id.bt_submit);
        vNewPassword = findViewById(R.id.v_new_psw);
        vOldPassword = findViewById(R.id.v_old_psw);
        tvEmail = findViewById(R.id.tv_email);
        btAvatar.setOnClickListener(this);
        btSubmit.setOnClickListener(this);

        userId = getIntent().getIntExtra("userId", -1);
        if(userId != LocalUser.getId())makeReadOnly();

        getInfo();
    }

    //处理上传头像和修改用户信息事件
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_avatar: // 上传头像
                break;
            case R.id.bt_submit: // 提交修改
                if (isInfoValid()) {
                    onEditInfo();
                }
                break;
            default:
                break;
        }
    }

    // 判断填入的个人信息是否符合要求
    private boolean isInfoValid() {
        avatar = ivAvatar.toString().trim();
        name = etName.getText().toString().trim();
        password = etPassword.getText().toString().trim();
        newPassword = etNewPassword.getText().toString().trim();
        info = etInfo.getText().toString().trim();
        return true;
    }

    // 向后端发送修改个人信息的请求
    private void onEditInfo() {

    }

    //启动EditInfoActivity，并传递用户ID
    public static void actionActivity(Context context, int userId) {
        Intent intent = new Intent(context, EditInfoActivity.class);
        intent.putExtra("userId", userId);
        context.startActivity(intent);
    }

    //当访问的是他人的主页时，去掉修改信息的控件，显示只读的控件
    private void makeReadOnly(){
        //虽然可以通过setEnable(false)来使控件只读
        //但是显示的文字会变成灰色，而且仍然保留了下划线，效果不是很好
        btSubmit.setVisibility(View.GONE);
        vNewPassword.setVisibility(View.GONE);
        vOldPassword.setVisibility(View.GONE);
        tvName.setVisibility(View.VISIBLE);
        etName.setVisibility(View.GONE);
        etInfo.setVisibility(View.GONE);
        tvInfo.setVisibility(View.VISIBLE);
    }

    //从后台获取用户的个人信息，主要包括：头像url，用户名，邮箱，个人简介
    private void getInfo(){
        if(userId == -1)return;
        new Thread(new Runnable() {
            @Override
            public void run() {
                Result<UserApi.UserInfo>infoResult = UserApi.INSTANCE.getUserInfo(userId);
                if(!infoResult.isOk())return;
                final UserApi.UserInfo info = infoResult.get();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Util.loadImageFromUrl(info.avatar_url, ivAvatar, EditInfoActivity.this);
                        etName.setText(info.name);
                        tvName.setText(info.name);
                        tvEmail.setText(info.email);
                        etInfo.setText(info.description);
                        tvInfo.setText(info.description);
                    }
                });
            }
        }).start();
    }
}
