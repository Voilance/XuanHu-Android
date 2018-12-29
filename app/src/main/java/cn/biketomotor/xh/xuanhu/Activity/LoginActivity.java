package cn.biketomotor.xh.xuanhu.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import cn.biketomotor.xh.xuanhu.Api.Result;
import cn.biketomotor.xh.xuanhu.Api.SessionApi;
import cn.biketomotor.xh.xuanhu.Api.UserApi;
import cn.biketomotor.xh.xuanhu.Class.LocalUser;
import cn.biketomotor.xh.xuanhu.Class.Sys;
import cn.biketomotor.xh.xuanhu.R;

public class LoginActivity extends BaseActivity implements View.OnClickListener {
    private static final String TAG = "TagLogin";

    private EditText etEmail;
    private EditText etPassword;
    private Button btLogin;
    private TextView tvRegister;
    private String email;
    private String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }

    private void initView() {
        setContentView(R.layout.activity_login);

        etEmail = findViewById(R.id.et_email);
        etPassword = findViewById(R.id.et_password);
        btLogin = findViewById(R.id.bt_login);
        tvRegister = findViewById(R.id.tv_register);
        btLogin.setOnClickListener(this);
        tvRegister.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_login:
                onLogin();
                break;
            case R.id.tv_register:
                finish();
                RegisterActivity.actionActivity(this);
                break;
            default:
                break;
        }
    }

    // 登陆
    private void onLogin() {
        email = etEmail.getText().toString().trim();
        password = etPassword.getText().toString().trim();
        // 如果登陆成功，初始化User，用finish()销毁登陆活动返回MainActivity
        new Thread(new Runnable() {
            @Override
            public void run() {
                final Result<SessionApi.LoginResult> result = SessionApi.INSTANCE.login(email, password);
                if (result.isOk()) {
                    if (result.get().success) {
                        Log.e(TAG, "run: succcess");
                        LocalUser.initLocalUser(
                                result.get().id,
                                result.get().email,
                                result.get().name,
                                result.get().created_at,
                                result.get().updated_at,
                                result.get().avatar_url,
                                result.get().teacher_id,
                                result.get().description
                        );
                        LocalUser.online = true;
                        Log.e(TAG, LocalUser.getName() + String.valueOf(LocalUser.online));
//                        Sys.setEmail(LocalUser.user.email);
//                        Sys.setName(LocalUser.user.name);
//                        Sys.setPassword(password);
//                        Sys.setLogin(true);
//                        Sys.writeSP(getSharedPreferences(Sys.SPName, Context.MODE_PRIVATE));
//                        Log.e(TAG, "hello" + Sys.getEmail() + Sys.getPassword());
                        finish();
                    }
                } else {
                    Log.e(TAG, result.getErrorMessage());
                }
            }
        }).start();
    }

    public static void actionActivity(Context context) {
        Intent intent = new Intent(context, LoginActivity.class);
        context.startActivity(intent);
    }
}
