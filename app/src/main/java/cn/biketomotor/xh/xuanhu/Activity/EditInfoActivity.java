package cn.biketomotor.xh.xuanhu.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import cn.biketomotor.xh.xuanhu.R;

public class EditInfoActivity extends BaseActivity implements View.OnClickListener {
    private static final String TAG = "TagEditInfo";

    private LinearLayout btAvatar;
    private ImageView ivAvatar;
    private EditText etName;
    private EditText etPassword;
    private EditText etNewPassword;
    private EditText etInfo;
    private View btSubmit;

    private String avatar;
    private String name;
    private String password;
    private String newPassword;
    private String info;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }

    private void initView() {
        setContentView(R.layout.activity_edit_info);

        btAvatar = findViewById(R.id.bt_avatar);
        ivAvatar = findViewById(R.id.iv_avatar);
        etName = findViewById(R.id.et_name);
        etPassword = findViewById(R.id.et_password);
        etNewPassword = findViewById(R.id.et_new_password);
        etInfo = findViewById(R.id.et_info);
        btSubmit = findViewById(R.id.bt_submit);
        btAvatar.setOnClickListener(this);
        btSubmit.setOnClickListener(this);
    }

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

    public static void actionActivity(Context context) {
        Intent intent = new Intent(context, EditInfoActivity.class);
        context.startActivity(intent);
    }
}
