package cn.biketomotor.xh.xuanhu.Class;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.widget.ImageView;

import java.io.InputStream;
import java.net.URL;

public class Util {

    public static void loadImageFromUrl(final String url, final ImageView iv, final Activity activity){
        //test img:
        //https://xuanhu-avatar.oss-cn-shenzhen.aliyuncs.com/MzU5NzQ5MzQwQHFxLmNvbQ
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    InputStream is = (InputStream) new URL(url).getContent();
                    final Drawable d = Drawable.createFromStream(is, "src name");
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            iv.setImageDrawable(d);
                        }
                    });
                } catch (Exception e) {
                    //todo
                }
            }
        }).start();

    }
}
