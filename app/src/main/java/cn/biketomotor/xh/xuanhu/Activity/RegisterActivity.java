package cn.biketomotor.xh.xuanhu.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import cn.biketomotor.xh.xuanhu.R;

public class RegisterActivity extends BaseActivity implements View.OnClickListener {
    private static final String TAG = "TagRegister";

    private EditText etName;
    private EditText etEmail;
    private EditText etPassword;
    private EditText etConfirmPassword;
    private Button btRegister;
    private TextView tvLogin;
    private String name;
    private String email;
    private String password;
    private String confirmPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }

    private void initView() {
        setContentView(R.layout.activity_register);

        etName = findViewById(R.id.et_name);
        etEmail = findViewById(R.id.et_email);
        etPassword = findViewById(R.id.et_password);
        etConfirmPassword = findViewById(R.id.et_confirm_password);
        btRegister = findViewById(R.id.bt_register);
        tvLogin = findViewById(R.id.tv_login);
        btRegister.setOnClickListener(this);
        tvLogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_register:
                if (isInfoValid()) {
                    onRegister();
                }
                break;
            case R.id.tv_login:
                // 销毁活动返回登陆活动
                finish();
                LoginActivity.actionActivity(this);
                break;
            default:
                break;
        }
    }

    // 判断用户填写的注册信息是否有用
    private boolean isInfoValid() {
        name = etName.getText().toString().trim();
        if (name.length() == 0) {
            toast("请输入用户名");
            return false;
        }
        email = etEmail.getText().toString().trim();
        if (email.length() == 0 || !email.contains("@")) {
            toast("请输入合法的邮箱地址");
            return false;
        }
        password = etPassword.getText().toString().trim();
        if (password.length() < 8) {
            toast("请输入至少8位的密码");
            return false;
        }
        confirmPassword = etConfirmPassword.getText().toString().trim();
        if (!confirmPassword.equals(password)) {
            toast("两次输入的密码不同");
            return false;
        }
        return true;
    }

    // 向后端发送注册请求
    private void onRegister() {
    }

    public static void actionActivity(Context context) {
        Intent intent = new Intent(context, RegisterActivity.class);
        context.startActivity(intent);
    }
}
