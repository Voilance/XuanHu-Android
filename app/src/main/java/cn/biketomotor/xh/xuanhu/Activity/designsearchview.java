package cn.biketomotor.xh.xuanhu.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import cn.biketomotor.xh.xuanhu.R;

public class designsearchview extends AppCompatActivity {



    private Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.designsearchview);

        button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(designsearchview.this, SearchDemo.class));

            }

        });
    }

}
