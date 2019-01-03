package cn.biketomotor.xh.xuanhu.Api;

import java.net.CookieManager;
import java.net.CookiePolicy;

import okhttp3.JavaNetCookieJar;
import okhttp3.OkHttpClient;

/**
 * 提供 HTTP 客户端的单例类
 */
class HttpClientManager {
    private static OkHttpClient client;

    /**
     * 获取 HTTP 客户端的单例对象
     * @return HTTP 客户端对象
     */
    static synchronized OkHttpClient getClient() {
        if (client == null) {
            CookieManager cookieManager = new CookieManager();
            cookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ALL);
            client = new OkHttpClient.Builder().cookieJar(new JavaNetCookieJar(cookieManager)).build();
        }
        return client;
    }
}
