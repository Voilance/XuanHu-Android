package cn.biketomotor.xh.xuanhu.Activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import cn.biketomotor.xh.xuanhu.R;

public class EditInfoActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }

    private void initView() {
        setContentView(R.layout.test_activity_edit_info);
    }

    public static void actionActivity(Context context) {
        Intent intent = new Intent(context, EditInfoActivity.class);
        context.startActivity(intent);
    }
}
