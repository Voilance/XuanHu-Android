package cn.biketomotor.xh.xuanhu.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

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
        setContentView(R.layout.test_activity_login);

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
    }

    public static void actionActivity(Context context) {
        Intent intent = new Intent(context, LoginActivity.class);
        context.startActivity(intent);
    }
}
