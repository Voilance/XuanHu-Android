package cn.biketomotor.xh.xuanhu.Api;

import java.net.CookieManager;
import java.net.CookiePolicy;

import okhttp3.JavaNetCookieJar;
import okhttp3.OkHttpClient;

class HttpClientManager {
    private static OkHttpClient client;

    static synchronized OkHttpClient getClient() {
        if (client == null) {
            CookieManager cookieManager = new CookieManager();
            cookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ALL);
            client = new OkHttpClient.Builder().cookieJar(new JavaNetCookieJar(cookieManager)).build();
        }
        return client;
    }
}
