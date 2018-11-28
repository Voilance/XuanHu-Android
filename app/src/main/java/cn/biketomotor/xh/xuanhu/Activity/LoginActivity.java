package cn.biketomotor.xh.xuanhu.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.biketomotor.xh.xuanhu.R;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";
    private static final int REQUEST_SIGNUP = 0;
    private static final int REQUEST_GOHOME = 1;
    public DrawerLayout mDrawerLayout;
    public ActionBarDrawerToggle mDrawerToggle;
    @BindView(R.id.input_email)
    EditText _emailText;
    @BindView(R.id.input_password)
    EditText _passwordText;
    @BindView(R.id.btn_login)
    Button _loginButton;
    @BindView(R.id.link_signup)
    TextView _signupLink;
    @BindView(R.id.link_gohome)
    TextView _gohomeLink;
    String result = "";


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);



        _loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                login();
            }
        });
        //返回首页



        _signupLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start the Signup activity
                Intent intent1 = new Intent(getApplicationContext(), SignupActivity.class);
                startActivityForResult(intent1, REQUEST_SIGNUP);
            }
        });

        _gohomeLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start the Homepage activity
                Intent intent2 = new Intent(LoginActivity.this,HomepageActivity.class);
                startActivity(intent2);


            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case REQUEST_SIGNUP:
                //判断注册是否成功  如果注册成功
                if(resultCode==RESULT_OK){
                    //则获取data中的账号和密码  动态设置到EditText中
                    String email=data.getStringExtra("email");
                    String password=data.getStringExtra("password");
                    _emailText.setText(email);
                    _passwordText.setText(password);
                    SharedPreferences settings3 = getSharedPreferences("settings3", 0);
                    SharedPreferences.Editor editor = settings3.edit();
                    editor.putString("email", email);
                    editor.putString("password", password);
                    editor.commit();
                }
                break;
        }
    }




    @Override
    public void onBackPressed() {
        // Disable going back to the MainActivity
          //   moveTaskToBack(true);
            super.onBackPressed();


    }

    public void onLoginSuccess() {

        Toast.makeText(getBaseContext(), "登录成功", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(LoginActivity.this, HomepageActivity.class);
        startActivityForResult(intent, REQUEST_GOHOME);
        finish();
    }

    public void onLoginFailed() {
        Toast.makeText(getBaseContext(), "登录失败", Toast.LENGTH_LONG).show();
        _loginButton.setEnabled(true);
    }

    //---------------------Edit---------------------//
    public void login() {
        Log.d(TAG, "Login");

        if (!validate()) {
            onLoginFailed();
            return;
        }

        _loginButton.setEnabled(false);
        // TODO: Implement your own authentication logic here.

        String email_input = _emailText.getText().toString().trim();
        String password_input = _passwordText.getText().toString().trim();
        SharedPreferences preferences=getSharedPreferences("settings3", 0);
        String email=preferences.getString("email", "");
        String password=preferences.getString("password", "");

                                   // Start the Homepage activity
        if(email_input.equals(email) && password_input.equals(password)) {

                           
                            _loginButton.setEnabled(true);
                            //进入首页
                            _loginButton.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

	                                    onLoginSuccess();

                                }
                            });

                }
                else{
                        Toast.makeText(getBaseContext(), "登录失败", Toast.LENGTH_LONG).show();
                        _loginButton.setEnabled(true);
      			}

       }

    public boolean validate() {

        boolean valid = true;

        String email = _emailText.getText().toString();
        String password = _passwordText.getText().toString();
        Drawable warning = getResources().getDrawable(R.drawable.warning);
        warning.setBounds(0, 0, warning.getIntrinsicWidth(), warning.getIntrinsicHeight());

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {

            _emailText.setError(Html.fromHtml("<font color='blue',body{background-color:white}>请输入有效的邮箱地址!</font>"), warning);
            valid = false;
        } else {
            _emailText.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            _passwordText.setError(Html.fromHtml("<font color='blue'>输入4-10位密码!</font>"), warning);

            valid = false;
        } else {
            _passwordText.setError(null);
        }

        return valid;

    }



}