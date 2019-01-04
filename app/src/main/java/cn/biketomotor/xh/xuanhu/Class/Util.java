package cn.biketomotor.xh.xuanhu.Class;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.widget.ImageView;

import java.io.InputStream;
import java.net.URL;

public class Util {

    //根据url加载图像
    public static void loadImageFromUrl(final String url, final ImageView iv, final Activity activity){
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
